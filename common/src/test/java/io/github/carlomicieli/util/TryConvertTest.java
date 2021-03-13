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
package io.github.carlomicieli.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

import org.junit.jupiter.api.Test;

class TryConvertTest {
  @Test
  void shouldConvertStringToIntegers() {
    Try<Integer> num = TryConvert.toInteger("123");
    assertThat(num.isSuccess()).isTrue();
    assertThat(num.get()).isEqualTo(123);
  }

  @Test
  void shouldReturnEmptyFromConversionStringToIntegerWhenInputIsBlank() {
    Try<Integer> num = TryConvert.toInteger(" ");
    assertThat(num.isFailure()).isTrue();
    assertThat(num.exception().get()).isInstanceOf(NumberFormatException.class);
  }

  @Test
  void shouldReturnEmptyFromConversionStringToIntegerWhenInputIsNotANumber() {
    Try<Integer> num = TryConvert.toInteger("abc123");
    assertThat(num.isFailure()).isTrue();
    assertThat(num.exception().get()).isInstanceOf(NumberFormatException.class);
  }

  @Test
  void shouldConvertStringToDoubles() {
    Try<Double> num = TryConvert.toDouble("123.56");
    assertThat(num.isSuccess()).isTrue();
    assertThat(num.get()).isCloseTo(123.56, within(0.01));
  }

  @Test
  void shouldReturnEmptyFromConversionStringToDoubleWhenInputIsBlank() {
    Try<Double> num = TryConvert.toDouble(" ");
    assertThat(num.isFailure()).isTrue();
    assertThat(num.exception().get()).isInstanceOf(NumberFormatException.class);
  }

  @Test
  void shouldReturnEmptyFromConversionStringToDoubleWhenInputIsNotANumber() {
    Try<Double> num = TryConvert.toDouble("xdr35");
    assertThat(num.isFailure()).isTrue();
    assertThat(num.exception().get()).isInstanceOf(NumberFormatException.class);
  }
}
