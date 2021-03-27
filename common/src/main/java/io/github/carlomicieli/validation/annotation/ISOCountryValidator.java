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

import io.github.carlomicieli.countries.ISOValidationUtils;
import io.github.carlomicieli.validation.annotation.constraints.ISOCountry;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.validation.validator.constraints.ConstraintValidator;
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext;

/** It represents a validator for countries ISO codes. */
public final class ISOCountryValidator implements ConstraintValidator<ISOCountry, String> {
    @Override
    public boolean isValid(
            @Nullable String value,
            @NonNull AnnotationValue<ISOCountry> annotationMetadata,
            @NonNull ConstraintValidatorContext context) {
        return ISOValidationUtils.countryIsValid(value);
    }
}
