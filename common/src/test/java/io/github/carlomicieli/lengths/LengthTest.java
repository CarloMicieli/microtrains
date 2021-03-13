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
package io.github.carlomicieli.lengths;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A length")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LengthTest {

  @Test
  void is_created_by_measure_unit() {
    var oneHundred = BigDecimal.valueOf(100);

    var km = Length.ofKilometers(oneHundred);
    var mm = Length.ofMillimeters(oneHundred);
    var mi = Length.ofMiles(oneHundred);
    var in = Length.ofInches(oneHundred);

    assertThat(km).isNotNull();
    assertThat(km.getMeasureUnit()).isEqualTo(MeasureUnit.KILOMETERS);
    assertThat(km.getValue()).isEqualTo(oneHundred);

    assertThat(mm).isNotNull();
    assertThat(mm.getMeasureUnit()).isEqualTo(MeasureUnit.MILLIMETERS);
    assertThat(mm.getValue()).isEqualTo(oneHundred);

    assertThat(mi).isNotNull();
    assertThat(mi.getMeasureUnit()).isEqualTo(MeasureUnit.MILES);
    assertThat(mi.getValue()).isEqualTo(oneHundred);

    assertThat(in).isNotNull();
    assertThat(in.getMeasureUnit()).isEqualTo(MeasureUnit.INCHES);
    assertThat(in.getValue()).isEqualTo(oneHundred);
  }

  @Test
  void is_created_with_a_value_and_measure_unit() {
    var len = Length.valueOf(42, MeasureUnit.INCHES);
    assertThat(len.getValue()).isEqualTo(BigDecimal.valueOf(42));
    assertThat(len.getMeasureUnit()).isEqualTo(MeasureUnit.INCHES);
  }

  @Test
  void can_only_assume_non_negative_values() {
    var ex =
        catchThrowableOfType(
            () -> Length.valueOf(-1, MeasureUnit.INCHES), IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage()).isEqualTo("A length value cannot be negative");
  }

  @Test
  void defines_zero_length_constants() {
    assertThat(Length.ZERO_MILLIMETERS.getValue()).isEqualTo(BigDecimal.ZERO);
    assertThat(Length.ZERO_MILLIMETERS.getMeasureUnit()).isEqualTo(MeasureUnit.MILLIMETERS);

    assertThat(Length.ZERO_INCHES.getValue()).isEqualTo(BigDecimal.ZERO);
    assertThat(Length.ZERO_INCHES.getMeasureUnit()).isEqualTo(MeasureUnit.INCHES);

    assertThat(Length.ZERO_KILOMETERS.getValue()).isEqualTo(BigDecimal.ZERO);
    assertThat(Length.ZERO_KILOMETERS.getMeasureUnit()).isEqualTo(MeasureUnit.KILOMETERS);

    assertThat(Length.ZERO_MILES.getValue()).isEqualTo(BigDecimal.ZERO);
    assertThat(Length.ZERO_MILES.getMeasureUnit()).isEqualTo(MeasureUnit.MILES);
  }

  @Test
  void has_a_string_representation() {
    var len = Length.valueOf(42, MeasureUnit.INCHES);
    assertThat(len.toString()).isEqualTo("42.0 in");
  }

  @Test
  void is_checked_for_equality_with_values_with_the_same_measure_unit() {
    var len1 = Length.valueOf(42.0, MeasureUnit.MILLIMETERS);
    var len2 = Length.valueOf(42, MeasureUnit.MILLIMETERS);
    assertThat(len1).isEqualTo(len2);
  }

  @Test
  void is_checked_for_equality_with_values_with_a_different_measure_unit() {
    var len1 = Length.valueOf(25.4, MeasureUnit.MILLIMETERS);
    var len2 = Length.valueOf(1, MeasureUnit.INCHES);
    assertThat(len1).isEqualTo(len2);
  }

  @Test
  void is_comparing_two_values_with_the_same_measure_unit() {
    var len1 = Length.valueOf(42, MeasureUnit.MILLIMETERS);
    var len2 = Length.valueOf(12, MeasureUnit.MILLIMETERS);

    assertThat(len1.compareTo(len2)).isPositive();
    assertThat(len2.compareTo(len1)).isNegative();
    //noinspection EqualsWithItself
    assertThat(len2.compareTo(len2)).isZero();
  }

  @Test
  void is_comparing_two_values_with_different_measure_units() {
    var len1 = Length.valueOf(42, MeasureUnit.MILLIMETERS);
    var len2 = Length.valueOf(2, MeasureUnit.INCHES); // around 50 mm

    assertThat(len1.compareTo(len2)).isNegative();
    assertThat(len2.compareTo(len1)).isPositive();
  }
}
