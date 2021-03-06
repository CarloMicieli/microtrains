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
package io.github.carlomicieli.validation.annotation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import io.github.carlomicieli.validation.annotation.ISOCountryValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Java annotation for ISO country codes validation.
 *
 * @author Carlo Micieli
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {FIELD, METHOD, PARAMETER})
@Constraint(validatedBy = ISOCountryValidator.class)
@Documented
public @interface ISOCountry {
  String message() default "{microtrains.country.code.invalid}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
