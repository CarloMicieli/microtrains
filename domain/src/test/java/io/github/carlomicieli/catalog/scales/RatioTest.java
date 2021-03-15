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
package io.github.carlomicieli.catalog.scales;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A ratio")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RatioTest {

    private static final BigDecimal EIGHTY_SEVEN = BigDecimal.valueOf(87);

    @Test
    void is_created_from_its_value_as_big_decimal() {
        var ratio = Ratio.of(EIGHTY_SEVEN);
        assertThat(ratio).isNotNull();
        assertThat(ratio.getValue()).isEqualTo(EIGHTY_SEVEN);
    }

    @Test
    void is_created_from_its_value_as_double() {
        var ratio = Ratio.of(EIGHTY_SEVEN.doubleValue());
        assertThat(ratio).isNotNull();
        assertThat(ratio.getValue()).isEqualTo(BigDecimal.valueOf(87.0));
    }

    @Test
    void is_created_from_its_value_as_long() {
        var ratio = Ratio.of(EIGHTY_SEVEN.longValue());
        assertThat(ratio).isNotNull();
        assertThat(ratio.getValue()).isEqualTo(EIGHTY_SEVEN);
    }

    @Test
    void must_have_a_positive_value() {
        var ex =
                catchThrowableOfType(
                        () -> Ratio.of(BigDecimal.valueOf(-1)), IllegalArgumentException.class);
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).isEqualTo("Ratio: value must be positive");
    }

    @Test
    void has_a_string_representation() {
        var ratio1 = Ratio.of(87);
        var ratio2 = Ratio.of(43.5);

        assertThat(ratio1.toString()).isEqualTo("1:87");
        assertThat(ratio2.toString()).isEqualTo("1:43.5");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    void should_compare_two_values() {
        var ratio1 = Ratio.of(87);
        var ratio2 = Ratio.of(43.5);

        assertThat(ratio1.compareTo(ratio1)).isEqualTo(0);
        assertThat(ratio1.compareTo(ratio2)).isNegative();
        assertThat(ratio2.compareTo(ratio1)).isPositive();
    }
}
