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
package io.github.carlomicieli.validation;

import static org.assertj.core.api.Assertions.*;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Value;
import org.junit.jupiter.api.*;

@DisplayName("CustomValidator")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomValidatorTest {

  private Validator validator;

  @BeforeAll
  void init() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    validator = factory.getValidator();
  }

  @Test
  void should_produce_no_validation_errors_for_valid_beans() {
    var valid = new MyBean("Label", 42);
    var errors = beanValidator().validate(valid);
    assertThat(errors).isEmpty();
  }

  @Test
  void should_produce_validation_errors_for_invalid_beans() {
    var invalid = new MyBean("", 0);

    var errors = beanValidator().validate(invalid);

    assertThat(errors).isNotEmpty();
    assertThat(errors).hasSize(2);

    assertThat(errors).contains(ValidationError.of("label", "must not be blank", ""));
    assertThat(errors)
        .contains(ValidationError.of("label", "size must be between 2 and 2147483647", ""));
  }

  CustomValidator<MyBean> beanValidator() {
    return () -> validator;
  }

  @Value
  static class MyBean {
    @NotBlank
    @Size(min = 2)
    String label;

    int value;
  }
}
