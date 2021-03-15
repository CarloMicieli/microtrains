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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A country")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CountryTest {
    @Test
    void is_created_from_a_valid_iso_code() {
        var country = Country.of("DE");
        assertThat(country).isNotNull();
        assertThat(country.getCode()).isEqualTo("de");
        assertThat(country.getEnglishName()).isEqualTo("Germany");
    }

    @Test
    void is_validating_the_country_code() {
        var ex = catchThrowableOfType(() -> Country.of("rr"), IllegalArgumentException.class);
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).isEqualTo("Invalid country code");
    }
}
