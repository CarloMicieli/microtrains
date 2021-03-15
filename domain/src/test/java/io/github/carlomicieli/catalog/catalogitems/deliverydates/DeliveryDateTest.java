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

import static io.github.carlomicieli.catalog.catalogitems.deliverydates.DeliveryDateTest.CustomAssertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DeliveryDateTest {

    @Test
    void shouldCreateNewDeliveryDatesWithYearAndQuarter() {
        DeliveryDate firstQtrOf2014 = DeliveryDate.of(2014, 1);
        assertThat(firstQtrOf2014).isNotNull().hasYear(2014).hasQuarter(1);
    }

    @Test
    public void shouldCreateNewDeliveryDatesWithYearOnly() {
        DeliveryDate deliveryDate = DeliveryDate.of(2014);
        assertThat(deliveryDate).isNotNull().hasYear(2014).hasNoQuarter();
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenTheQuarterIsNotValid() {
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class, () -> DeliveryDate.of(2014, -1));
    }

    @Test
    public void shouldCheckWhetherTwoDeliveryDatesAreDifferent() {
        DeliveryDate x = DeliveryDate.of(2012, 1);
        DeliveryDate y = DeliveryDate.of(2012, 2);
        assertThat(x).isNotEqualTo(y);
    }

    @Test
    public void shouldCheckWhetherTwoDeliveryDatesAreEquals() {
        DeliveryDate x = DeliveryDate.of(2012, 1);
        DeliveryDate y = DeliveryDate.of(2012, 1);
        assertThat(x).isEqualTo(y);
    }

    @Test
    public void shouldProduceStringRepresentationForDeliveryDates() {
        DeliveryDate dd1 = DeliveryDate.of(2012, 1);
        DeliveryDate dd2 = DeliveryDate.of(2012);

        assertThat(dd1.toString()).isEqualTo("2012/Q1");
        assertThat(dd2.toString()).isEqualTo("2012");
    }

    @Test
    public void shouldParseStringAsDeliveryDateWithYearOnly() {
        DeliveryDate dd = DeliveryDate.parseDeliveryDate("2014");
        assertThat(dd).isNotNull().hasYear(2014).hasNoQuarter();
    }

    @Test
    public void shouldParseStringAsDeliveryDateWithYeaAndQuarter() {
        DeliveryDate dd = DeliveryDate.parseDeliveryDate("2014/Q1");
        assertThat(dd).isNotNull().hasYear(2014).hasQuarter(1);
    }

    @Test
    public void shouldThrowExceptionParsingStringsWithInvalidYearValues() {
        var ex =
                org.junit.jupiter.api.Assertions.assertThrows(
                        IllegalArgumentException.class,
                        () -> DeliveryDate.parseDeliveryDate("20e4/Q1"));
        assertThat(ex.getMessage()).isEqualTo("Invalid format for a delivery date");
    }

    @Test
    public void shouldProduceHasCodesForDeliveryDates() {
        DeliveryDate x = DeliveryDate.of(2012, 1);
        DeliveryDate y = DeliveryDate.of(2012, 1);
        assertThat(x.hashCode()).isEqualTo(y.hashCode());
    }

    @Test
    public void shouldCreateRangesOfDeliveryDates() {
        final List<DeliveryDate> expected =
                Arrays.asList(
                        DeliveryDate.of(2012, 4),
                        DeliveryDate.of(2012, 3),
                        DeliveryDate.of(2012, 2),
                        DeliveryDate.of(2012, 1),
                        DeliveryDate.of(2011, 4),
                        DeliveryDate.of(2011, 3),
                        DeliveryDate.of(2011, 2),
                        DeliveryDate.of(2011, 1),
                        DeliveryDate.of(2010),
                        DeliveryDate.of(2009));

        Stream<DeliveryDate> stream = DeliveryDate.range(2009, 2010, 2011, 2012);

        assertThat(stream.collect(Collectors.toList())).isEqualTo(expected);
    }

    static class CustomAssertions extends Assertions {
        public static DeliveryDateAssert assertThat(DeliveryDate actual) {
            return new DeliveryDateAssert(actual);
        }
    }

    static class DeliveryDateAssert extends AbstractAssert<DeliveryDateAssert, DeliveryDate> {
        public DeliveryDateAssert(DeliveryDate actual) {
            super(actual, DeliveryDateAssert.class);
        }

        public static DeliveryDateAssert assertThat(DeliveryDate actual) {
            return new DeliveryDateAssert(actual);
        }

        public DeliveryDateAssert hasYear(int year) {
            isNotNull();

            if (actual.getYear() != year) {
                failWithMessage("Expected year to be <%d> but was <%d>", year, actual.getYear());
            }

            return this;
        }

        public DeliveryDateAssert hasQuarter(int quarter) {
            isNotNull();

            if (actual.getQuarter() != quarter) {
                failWithMessage(
                        "Expected quarter to be <%d> but was <%d>", quarter, actual.getQuarter());
            }

            return this;
        }

        public DeliveryDateAssert hasNoQuarter() {
            isNotNull();

            if (actual.hasQuarter()) {
                failWithMessage("Expected to be without quarter but was <%d>", actual.getQuarter());
            }

            return this;
        }
    }
}
