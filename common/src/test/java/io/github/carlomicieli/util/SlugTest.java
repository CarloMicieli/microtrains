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
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A Slug")
class SlugTest {
  @Test
  public void shouldEncodeWhitespacesAsSlugs() {
    Slug slug = Slug.of("Time is an illusion");
    assertThat(slug.getValue()).isEqualTo("time-is-an-illusion");
  }

  @Test
  public void shouldEncodePunctuationSignsAsSlugs() {
    Slug slug = Slug.of("Time; is an: illusion.");
    assertThat(slug.getValue()).isEqualTo("time-is-an-illusion");
  }

  @Test
  public void shouldEncodeNonLatinCharactersAsSlugs() {
    Slug slug = Slug.of("Timè is àn illusiòn.");
    assertThat(slug.getValue()).isEqualTo("time-is-an-illusion");
  }

  @Test
  public void shouldThrowExceptionIfProvidedStringIsNull() {
    var ex = catchThrowableOfType(() -> Slug.of(null), InvalidSlugException.class);
    assertThat(ex.getMessage()).isEqualTo("Slug: input cannot be null or empty");
  }

  @Test
  public void shouldEncodeMultipleValuesAsSlugs() {
    Slug slug = Slug.ofValues("first", 2, null, "end");
    assertThat(slug.getValue()).isEqualTo("first-2-end");
  }

  @Test
  public void shouldThrowExceptionIfProvidedValuesAreNull() {
    var ex = catchThrowableOfType(() -> Slug.ofValues((Object[]) null), InvalidSlugException.class);
    assertThat(ex.getMessage()).isEqualTo("Slug: input cannot be null or empty");
  }

  @Test
  public void shouldCheckWhetherSlugIsNotEmptyAndIfSoUseTheProvidedSupplier() {
    Supplier<String> slugSupplier = () -> "default-slug";
    assertThat(Slug.orElseGet("my-slug", slugSupplier)).isEqualTo("my-slug");
    assertThat(Slug.orElseGet("", slugSupplier)).isEqualTo("default-slug");
    assertThat(Slug.orElseGet(null, slugSupplier)).isEqualTo("default-slug");
    assertThat(Slug.orElseGet(null, () -> null)).isNull();
  }
}
