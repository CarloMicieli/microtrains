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
package io.github.carlomicieli.collecting.valueobject;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Currency;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Price")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PriceTest {

    @Test
    void is_defined_by_amount_and_currency() {
        var p = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        assertThat(p).isNotNull();
    }

    @Test
    void has_builder_methods_for_the_most_common_currencies() {
        final var value = BigDecimal.valueOf(100);
        var dollars = Price.dollars(value);
        var euros = Price.euros(value);
        var pounds = Price.pounds(value);

        assertThat(dollars).isNotNull();
        assertThat(dollars.getCurrency()).isEqualTo(Currency.getInstance("USD"));

        assertThat(euros).isNotNull();
        assertThat(euros.getCurrency()).isEqualTo(Currency.getInstance("EUR"));

        assertThat(pounds).isNotNull();
        assertThat(pounds.getCurrency()).isEqualTo(Currency.getInstance("GBP"));
    }

    @Test
    void requires_amount_to_be_non_negative() {
        var ex =
                catchThrowableOfType(
                        () -> new Price(BigDecimal.valueOf(-1), Currency.getInstance("USD")),
                        IllegalArgumentException.class);

        assertThat(ex).isNotNull();
        assertThat(ex).hasMessage("Price: amount cannot be negative");
    }

    @Test
    void requires_currency_to_be_non_null() {
        var ex =
                catchThrowableOfType(
                        () -> new Price(BigDecimal.valueOf(10), null),
                        IllegalArgumentException.class);

        assertThat(ex).isNotNull();
        assertThat(ex).hasMessage("Price: a valid currency is required");
    }

    @Test
    void has_a_string_representation() {
        var p = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        assertThat(p.toString()).isEqualTo("100.00 $");
    }

    @Test
    void is_able_to_check_whether_currency_is_valid() {
        boolean valid;
        valid = Price.isValidCurrency("USD");
        assertThat(valid).isTrue();

        valid = Price.isValidCurrency("YYY");
        assertThat(valid).isFalse();
    }

    @Test
    void should_add_two_prices_with_the_same_currency() {
        var p1 = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var p2 = new Price(BigDecimal.valueOf(50), Currency.getInstance("USD"));

        var result = p1.add(p2);

        assertThat(result).isNotNull();
        assertThat(result.getCurrency()).isEqualTo(p1.getCurrency());
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(150));
    }

    @Test
    void should_fail_to_add_two_prices_when_they_have_different_currencies() {
        var p1 = new Price(BigDecimal.valueOf(100), Currency.getInstance("GBP"));
        var p2 = new Price(BigDecimal.valueOf(50), Currency.getInstance("USD"));

        var ex = catchThrowableOfType(() -> p1.add(p2), DifferentCurrencyException.class);

        assertThat(ex).isNotNull();
        assertThat(ex).hasMessage("Wrong currency, this operation is not allowed (GBP != USD)");
    }

    @Test
    void should_subtract_two_prices_with_the_same_currency() {
        var p1 = new Price(BigDecimal.valueOf(100), Currency.getInstance("USD"));
        var p2 = new Price(BigDecimal.valueOf(50), Currency.getInstance("USD"));

        var result = p1.subtract(p2);

        assertThat(result).isNotNull();
        assertThat(result.getCurrency()).isEqualTo(p1.getCurrency());
        assertThat(result.getAmount()).isEqualTo(BigDecimal.valueOf(50));
    }

    @Test
    void should_fail_to_subtract_two_prices_when_they_have_different_currencies() {
        var p1 = new Price(BigDecimal.valueOf(100), Currency.getInstance("GBP"));
        var p2 = new Price(BigDecimal.valueOf(50), Currency.getInstance("USD"));

        var ex = catchThrowableOfType(() -> p1.subtract(p2), DifferentCurrencyException.class);

        assertThat(ex).isNotNull();
        assertThat(ex).hasMessage("Wrong currency, this operation is not allowed (GBP != USD)");
    }

    @Test
    void should_fail_to_subtract_two_prices_when_the_result_will_produce_a_negative_result() {
        var p1 = new Price(BigDecimal.valueOf(10), Currency.getInstance("GBP"));
        var p2 = new Price(BigDecimal.valueOf(50), Currency.getInstance("GBP"));

        var ex = catchThrowableOfType(() -> p1.subtract(p2), UnsupportedOperationException.class);

        assertThat(ex).isNotNull();
        assertThat(ex).hasMessage("Result will produce a negative price");
    }
}
