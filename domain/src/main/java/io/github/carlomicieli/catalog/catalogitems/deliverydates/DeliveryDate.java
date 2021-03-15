/*
   Copyright 2021 (C) Carlo Micieli

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.carlomicieli.catalog.catalogitems.deliverydates;

import static java.util.Comparator.comparing;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * It represents an immutable class for delivery date for a rolling stock model.
 *
 * <p>The delivery date has two parts: the {@code year} (required) and the {@code quarter}
 * (optional).
 *
 * <p>
 *
 * <pre>
 *  DeliveryDate q1 = DeliveryDate.of(2014, 1);
 *  assertThat(q1.toString()).isEqualTo("2014/Q1");
 *      </pre>
 *
 * <p>Delivery date objects are immutable, the methods that changes any value are actually producing
 * a new object.
 *
 * <pre>
 *  DeliveryDate dd1 = firstQuarter
 *      .withQuarter(2)
 *      .withYear(2014);
 *      </pre>
 */
public final class DeliveryDate {
    private static final Pattern YEAR_AND_QUARTER_PATTERN = Pattern.compile("(\\d{4})/Q(\\d)");
    private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{4})");

    private static final String QUARTER_PREFIX = "Q";
    private final int year;
    private final int quarter;

    private DeliveryDate(int year, int quarter) {
        validateYear(year);

        this.year = year;
        this.quarter = quarter;
    }

    public int getYear() {
        return year;
    }

    public int getQuarter() {
        return quarter;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DeliveryDate)) return false;

        DeliveryDate other = (DeliveryDate) obj;
        return this.quarter == other.quarter && this.year == other.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.quarter, this.year);
    }

    @Override
    public String toString() {
        if (this.hasQuarter()) {
            return String.format("%d/%s%d", year, QUARTER_PREFIX, quarter);
        }
        return String.format("%d", year);
    }

    /**
     * Checks whether the current {@code DeliveryData} contains quarter information.
     *
     * @return {@code true} if contains quarter information, {@code false} otherwise.
     */
    public boolean hasQuarter() {
        return validQuarterPredicate().test(quarter);
    }

    /**
     * Creates a new {@code DeliveryDate} without the quarter.
     *
     * @param year the year
     * @throws IllegalArgumentException if the quarter is invalid.
     */
    public static DeliveryDate of(int year) {
        return new DeliveryDate(year, 0);
    }

    /**
     * Creates a new {@code DeliveryDate} with year and quarter.
     *
     * @param year the year
     * @param quarter quarter (1-4)
     * @throws IllegalArgumentException if the quarter is invalid.
     */
    public static DeliveryDate of(int year, int quarter) {
        validateQuarter(quarter);
        return new DeliveryDate(year, quarter);
    }

    private static void validateQuarter(int quarter) {
        if (!validQuarterPredicate().test(quarter))
            throw new IllegalArgumentException("Delivery quarter must be >=1 and <=4");
    }

    private static void validateYear(int year) {
        if (!validYearPredicate().test(year))
            throw new IllegalArgumentException("Year must be >=1900 and <2999");
    }

    /**
     * Parses the string argument as a {@code DeliveryDate}.
     *
     * <p>A valid instance of {@code DeliveryDate} can have two formats:
     *
     * <ul>
     *   <li>{@code YYYY}, where {@code YYYY} is a valid year (ie {@code year>=1900 && year<2999});
     *   <li>{@code YYYY + '/Q' + N}, where {@code YYYY} is a valid year (ie {@code year>=1900 &&
     *       year<2999}) and {@code N} is the quarter number (ie {@code quarter>=1 && quarter<4}).
     * </ul>
     *
     * @param str the string to be parsed
     * @return a {@code DeliveryDate} represented by the string argument
     * @throws IllegalArgumentException if {@code s} is empty or {@code null}
     * @throws DeliveryDateFormatException if {@code s} doesn't represent a valid {@code
     *     DeliveryDate}
     */
    public static DeliveryDate parseDeliveryDate(String str) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("Empty string is not valid");
        }

        if (!checkString(YEAR_PATTERN, str) && !checkString(YEAR_AND_QUARTER_PATTERN, str)) {
            throw new DeliveryDateFormatException("Invalid format for a delivery date");
        }

        return Optional.ofNullable(parseWithYearAndQuarter(str)).orElse(parseWithYear(str));
    }

    private static DeliveryDate parseWithYear(String str) {
        Matcher matcher = YEAR_PATTERN.matcher(str);
        if (matcher.find()) {
            return DeliveryDate.of(Integer.parseInt(matcher.group(1)));
        }

        return null;
    }

    private static DeliveryDate parseWithYearAndQuarter(String str) {
        Matcher matcher = YEAR_AND_QUARTER_PATTERN.matcher(str);
        if (matcher.find()) {
            int year = Integer.parseInt(matcher.group(1));
            int quarter = Integer.parseInt(matcher.group(2));
            return DeliveryDate.of(year, quarter);
        }

        return null;
    }

    private static boolean checkString(Pattern pattern, String str) {
        return pattern.matcher(str).matches();
    }

    /**
     * Returns the list of {@code DeliveryDate}.
     *
     * <p>This methods provides two different years ranges:
     *
     * <ul>
     *   <li>since {@code endYearWithQuarters} back to {@code startYearWithQuarters} the years are
     *       listed with quarter information;
     *   <li>since {@code endYearWithoutQuarters} back to {@code startYearWithoutQuarters} the years
     *       are listed without quarter information.
     * </ul>
     *
     * @param startYearWithoutQuarters the first year without quarters
     * @param endYearWithoutQuarters the last year without quarters
     * @param startYearWithQuarters the first year with quarters
     * @param endYearWithQuarters the last year with quarters
     * @return the {@code DeliveryDate} list
     */
    public static Stream<DeliveryDate> range(
            int startYearWithoutQuarters,
            int endYearWithoutQuarters,
            int startYearWithQuarters,
            int endYearWithQuarters) {

        if (startYearWithoutQuarters > endYearWithoutQuarters) {
            throw new IllegalArgumentException(
                    "DeliveryDate: startYearWithoutQuarters > endYearWithoutQuarters");
        }

        if (startYearWithQuarters > endYearWithQuarters) {
            throw new IllegalArgumentException(
                    "DeliveryDate: startYearWithQuarters > endYearWithQuarters");
        }

        Function<Integer, Stream<DeliveryDate>> deliveryDatesForYear =
                year -> quarters().mapToObj(qtr -> DeliveryDate.of(year, qtr));

        return Stream.concat(
                        IntStream.rangeClosed(startYearWithQuarters, endYearWithQuarters)
                                .boxed()
                                .flatMap(deliveryDatesForYear),
                        IntStream.rangeClosed(startYearWithoutQuarters, endYearWithoutQuarters)
                                .mapToObj(DeliveryDate::of))
                .sorted(deliveryDateComparator().reversed());
    }

    private static Comparator<DeliveryDate> deliveryDateComparator() {
        return comparing(DeliveryDate::getYear).thenComparing(DeliveryDate::getQuarter);
    }

    private static IntPredicate validQuarterPredicate() {
        return qtr -> quarters().anyMatch(q -> q == qtr);
    }

    private static IntPredicate validYearPredicate() {
        return year -> year >= 1900 && year < 2999;
    }

    private static IntStream quarters() {
        return IntStream.rangeClosed(1, 4);
    }
}
