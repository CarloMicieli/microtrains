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
package io.github.carlomicieli.catalog.catalogitems.rollingstocks;

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.lengths.Length;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Length over buffer")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LengthOverBufferTest {

  private static final BigDecimal TWO_HUNDRED_TEN = BigDecimal.valueOf(210);
  private static final BigDecimal THREE_HUNDRED = BigDecimal.valueOf(300);

  @Test
  void is_created_from_millimeters_value() {
    var length = LengthOverBuffer.ofMillimeters(TWO_HUNDRED_TEN);

    assertThat(length).isNotNull();
    assertThat(length.getMillimeters()).isEqualTo(Length.ofMillimeters(TWO_HUNDRED_TEN));
    assertThat(length.getInches()).isNotNull();
  }

  @Test
  void is_created_from_inches_value() {
    var length = LengthOverBuffer.ofInches(TWO_HUNDRED_TEN);

    assertThat(length).isNotNull();
    assertThat(length.getInches()).isEqualTo(Length.ofInches(TWO_HUNDRED_TEN));
    assertThat(length.getMillimeters()).isNotNull();
  }

  @Test
  void should_compare_two_values() {
    var length1 = LengthOverBuffer.ofInches(TWO_HUNDRED_TEN);
    var length2 = LengthOverBuffer.ofInches(THREE_HUNDRED);

    assertThat(length1.compareTo(length2)).isNegative();
    assertThat(length2.compareTo(length1)).isPositive();
  }
}
