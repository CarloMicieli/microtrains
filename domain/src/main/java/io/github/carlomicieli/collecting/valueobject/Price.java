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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Currency;
import lombok.Value;

/** A value object for prices, it contains two information an amount and a currency */
@Value
public class Price {
  BigDecimal amount;
  Currency currency;

  public Price(BigDecimal amount, Currency currency) {
    if (amount.signum() <= 0) {
      throw new IllegalArgumentException("Price: amount cannot be negative");
    }

    if (currency == null) {
      throw new IllegalArgumentException("Price: a valid currency is required");
    }

    this.amount = amount;
    this.currency = currency;
  }

  /**
   * Add {@code this} price with the {@code other}. In case the two values have different currency
   * then the operation will fail throwing a {@code DifferentCurrencyException}
   *
   * @throws DifferentCurrencyException the two prices have different currency
   */
  public Price add(Price other) {
    ensureSameCurrency(getCurrency(), other.getCurrency());
    return new Price(this.getAmount().add(other.getAmount()), this.getCurrency());
  }

  /**
   * Subtract {@code other} price from {@code this} price. In case the two values have different
   * currency then the operation will fail throwing a {@code DifferentCurrencyException}.
   *
   * @throws DifferentCurrencyException the two prices have different currency
   * @throws UnsupportedOperationException when the operation yields a negative {@code Price}
   */
  public Price subtract(Price other) {
    ensureSameCurrency(getCurrency(), other.getCurrency());

    var result = this.getAmount().subtract(other.getAmount());
    if (result.signum() < 0) {
      throw new UnsupportedOperationException("Result will produce a negative price");
    }

    return new Price(result, this.getCurrency());
  }

  @Override
  public String toString() {
    DecimalFormat df = new DecimalFormat("#,###.00");
    return df.format(amount) + " " + currency.getSymbol();
  }

  public static boolean isValidCurrency(String code) {
    return Currency.getAvailableCurrencies().stream()
        .anyMatch(c -> c.getCurrencyCode().equals(code));
  }

  public static Price dollars(BigDecimal value) {
    return new Price(value, Currency.getInstance("USD"));
  }

  public static Price euros(BigDecimal value) {
    return new Price(value, Currency.getInstance("EUR"));
  }

  public static Price pounds(BigDecimal value) {
    return new Price(value, Currency.getInstance("GBP"));
  }

  private static void ensureSameCurrency(Currency lhs, Currency rhs) {
    if (!lhs.equals(rhs)) {
      throw new DifferentCurrencyException(lhs, rhs);
    }
  }
}
