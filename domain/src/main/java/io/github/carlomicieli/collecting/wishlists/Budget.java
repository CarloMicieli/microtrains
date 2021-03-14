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

import java.math.BigDecimal;
import java.util.Currency;
import lombok.Value;

@Value
public class Budget {
  BigDecimal amount;
  Currency currency;

  public Budget(BigDecimal amount, Currency currency) {
    if (amount.signum() < 0) {
      throw new IllegalArgumentException("A budget must have a non negative amount");
    }

    this.amount = amount;
    this.currency = currency;
  }

  public static Budget ofDollars(int amount) {
    return fromAmount(amount, "USD");
  }

  public static Budget ofEuro(int amount) {
    return fromAmount(amount, "EUR");
  }

  public static Budget ofPounds(int amount) {
    return fromAmount(amount, "GBP");
  }

  private static Budget fromAmount(int amount, String currency) {
    return new Budget(BigDecimal.valueOf(amount), Currency.getInstance(currency));
  }
}
