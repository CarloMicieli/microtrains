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

import com.google.common.base.Strings;
import io.github.carlomicieli.validation.annotation.constraints.IsEnumName;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext;
import java.util.Arrays;

public final class IsEnumNameValidator implements ConstraintValidator<IsEnumName, String> {
    @Override
    public boolean isValid(
            @Nullable String value,
            @NonNull AnnotationValue<IsEnumName> annotation,
            @NonNull ConstraintValidatorContext context) {

        if (Strings.isNullOrEmpty(value)) {
            return true;
        }

        final var parameters = annotation.getConvertibleValues().asMap();
        @SuppressWarnings("unchecked")
        Class<? extends Enum<?>> enumType = (Class<? extends Enum<?>>) parameters.get("enumType");
        return Arrays.stream(enumType.getEnumConstants())
                .map(Enum::name)
                .map(name -> name.toUpperCase().replace('-', '_'))
                .anyMatch(name -> name.equals(value));
    }
}
