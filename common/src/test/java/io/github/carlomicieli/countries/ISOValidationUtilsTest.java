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
package io.github.carlomicieli.countries;

import static io.github.carlomicieli.countries.ISOValidationUtils.countryIsValid;
import static io.github.carlomicieli.countries.ISOValidationUtils.languageIsValid;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ISOValidationUtilsTest {
  @Test
  public void shouldCheckWhetherLanguageCodesAreValid() {
    assertThat(languageIsValid("en")).isTrue();
  }

  @Test
  public void shouldCheckWhetherLanguageCodesAreNotValid() {
    assertThat(languageIsValid("rr")).isFalse();
  }

  @Test
  public void shouldCheckWhetherCountryCodesAreValid() {
    assertThat(countryIsValid("gb")).isTrue();
  }

  @Test
  public void shouldCheckWhetherCountryCodesAreNotValid() {
    assertThat(countryIsValid("rr")).isFalse();
  }
}
