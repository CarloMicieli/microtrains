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
package io.github.carlomicieli.catalog.catalogitems;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("An Item Number")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ItemNumberTest {

  @Test
  void is_created_from_non_empty_strings() {
    var itemNumber = ItemNumber.of("123456");

    assertThat(itemNumber).isNotNull();
    assertThat(itemNumber.getValue()).isEqualTo("123456");
  }

  @Test
  void should_fail_when_the_item_number_is_invalid() {
    var ex = catchThrowableOfType(() -> ItemNumber.of(""), IllegalArgumentException.class);

    assertThat(ex).isNotNull();
    assertThat(ex.getMessage()).isEqualTo("Item number value cannot be blank or null");
  }

  @Test
  void should_try_to_create_an_item_number_or_return_an_empty_value() {
    var val1 = ItemNumber.tryCreate("123456");
    var val2 = ItemNumber.tryCreate("");

    assertThat(val1).isNotNull();
    assertThat(val1).isPresent();
    assertThat(val2).isNotNull();
    assertThat(val2).isEmpty();
  }

  @Test
  void should_compare_two_values() {
    var itemNumber1 = ItemNumber.of("123456");
    var itemNumber2 = ItemNumber.of("654321");

    assertThat(itemNumber1.compareTo(itemNumber2)).isNegative();
    assertThat(itemNumber2.compareTo(itemNumber1)).isPositive();
  }
}
