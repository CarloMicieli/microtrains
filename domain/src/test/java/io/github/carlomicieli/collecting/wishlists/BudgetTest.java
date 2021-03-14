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
package io.github.carlomicieli.collecting.wishlists;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Currency;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Budget")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BudgetTest {
  @Test
  void is_defined_for_a_given_amount() {
    var b1 = Budget.ofDollars(100);
    var b2 = Budget.ofEuro(100);
    var b3 = Budget.ofPounds(100);

    Assertions.assertThat(b1).isNotNull();
    Assertions.assertThat(b2).isNotNull();
    Assertions.assertThat(b3).isNotNull();
  }

  @Test
  void must_have_a_non_negative_amount_otherwise_an_exception_is_thrown() {
    var ex =
        catchThrowableOfType(
            () -> new Budget(BigDecimal.valueOf(-1), Currency.getInstance("USD")),
            IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex).hasMessage("A budget must have a non negative amount");
  }
}
