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

import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class TryTest {
  @Test
  void shouldCreateSuccessValues() {
    Try<Integer> success = Try.success(100);
    assertThat(success.get()).isEqualTo(100);
    assertThat(success.isSuccess()).isTrue();
  }

  @Test
  void shouldCreateFailureValues() {
    Try<Integer> failure = Try.failure(new NumberFormatException());
    assertThat(failure.isFailure()).isTrue();
  }

  @Test
  void shouldCreateTryContainerForSuccess() {
    Try<Integer> success = Try.apply(() -> 1 + 1);
    assertThat(success.get()).isEqualTo(2);
    assertThat(success.isSuccess()).isTrue();
    assertThat(success.isFailure()).isFalse();
  }

  @Test
  @SuppressWarnings({"divzero", "NumericOverflow"})
  void shouldCreateTryContainerForFailure() {
    Try<Integer> failure = Try.apply(() -> 1 / 0);
    assertThat(failure.isSuccess()).isFalse();
    assertThat(failure.isFailure()).isTrue();
  }

  @Test
  @SuppressWarnings({"divzero", "NumericOverflow"})
  void shouldRecoverWithAFunctionAfterFailure() {
    Try<Integer> recovered = Try.apply(() -> 1 / 0).recoverWith(() -> Try.success(999));
    assertThat(recovered.get()).isEqualTo(999);
  }

  @Test
  void shouldNotRecoverWithAFunctionAfterSuccess() {
    Try<Integer> notRecovered = Try.apply(() -> 1 + 1).recoverWith(() -> Try.success(999));
    assertThat(notRecovered.get()).isEqualTo(2);
  }

  @Test
  void shouldReturnTheContainerValueInASafeManner() {
    int result1 = Try.success(100).getOrElse(() -> 999);
    int result2 = Try.<Integer>failure(new NumberFormatException()).getOrElse(() -> 999);

    assertThat(result1).isEqualTo(100);
    assertThat(result2).isEqualTo(999);
  }

  @Test
  void shouldConvertTryInstancesToOptional() {
    Optional<Integer> opt1 = Try.success(100).toOptional();
    Optional<Integer> opt2 = Try.<Integer>failure(new NumberFormatException()).toOptional();

    assertThat(opt1.isPresent()).isTrue();
    assertThat(opt2.isPresent()).isFalse();
  }

  @Test
  void shouldFilterTryInstances() {
    Try<Integer> success = Try.success(100);
    Try<Integer> failure = Try.failure(new NumberFormatException());

    Try<Integer> successIfPredicateSatisfied = success.filter(n -> n == 100);
    Try<Integer> failureIfPredicateNotSatisfied = success.filter(n -> n > 10000);
    Try<Integer> alwaysFailureFilteringFailure = failure.filter(n -> n > 0);

    assertThat(successIfPredicateSatisfied.isSuccess()).isTrue();
    assertThat(failureIfPredicateNotSatisfied.isFailure()).isTrue();
    assertThat(alwaysFailureFilteringFailure.isFailure()).isTrue();
  }

  @Test
  @Disabled
  void shouldThrowNoSuchElementExceptionFilteringASuccessWithPredicateNotSatisfied() {
    Try<Integer> success = Try.success(100);
    Assertions.assertThrows(NoSuchElementException.class, () -> success.filter(n -> n > 10000));
  }

  @Test
  void shouldMapTryInstances() {
    Try<Integer> success = Try.success(100);
    Try<Integer> failure = Try.failure(new NumberFormatException());

    Try<Integer> mappedSuccess = success.map(n -> n * 2);
    Try<Integer> mappedFailure = failure.map(n -> n * 2);

    assertThat(mappedSuccess.isSuccess()).isTrue();
    assertThat(mappedSuccess.get()).isEqualTo(200);
    assertThat(mappedFailure.isFailure()).isTrue();
  }

  @Test
  void shouldFlatMapTryInstances() {
    Try<Integer> success = Try.success(100);
    Try<Integer> failure = Try.failure(new NumberFormatException());

    Try<Integer> mappedSuccess = success.flatMap(n -> Try.success(n * 2));
    Try<Integer> mappedFailure = failure.flatMap(n -> Try.success(n * 2));

    assertThat(mappedSuccess.isSuccess()).isTrue();
    assertThat(mappedSuccess.get()).isEqualTo(200);
    assertThat(mappedFailure.isFailure()).isTrue();
  }

  @Test
  void shouldReturnTheInnerCauseForFailure() {
    final NumberFormatException NUMBER_FORMAT_EX = new NumberFormatException();
    Try<Integer> success = Try.success(100);
    Try<Integer> failure = Try.failure(NUMBER_FORMAT_EX);

    assertThat(success.exception().isPresent()).isFalse();
    assertThat(failure.exception().isPresent()).isTrue();
    assertThat(failure.exception().get()).isEqualTo(NUMBER_FORMAT_EX);
  }
}
