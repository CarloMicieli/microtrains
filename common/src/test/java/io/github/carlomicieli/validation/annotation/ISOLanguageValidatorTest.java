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
package io.github.carlomicieli.validation.annotation;

import static org.assertj.core.api.Assertions.*;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ISOLanguageValidator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ISOLanguageValidatorTest {
    private final ISOLanguageValidator validator = new ISOLanguageValidator();

    @Mock private ConstraintValidatorContext context;

    @Test
    void should_validate_empty_values() {
        assertThat(validator.isValid(null, context)).isTrue();
        assertThat(validator.isValid("", context)).isTrue();
    }

    @Test
    void should_validate_correct_ISO_language_codes() {
        assertThat(validator.isValid("de", context)).isTrue();
        assertThat(validator.isValid("en", context)).isTrue();
    }

    @Test
    void should_fail_to_validate_incorrect_ISO_language_codes() {
        assertThat(validator.isValid("rr", context)).isFalse();
        assertThat(validator.isValid("ub", context)).isFalse();
    }
}
