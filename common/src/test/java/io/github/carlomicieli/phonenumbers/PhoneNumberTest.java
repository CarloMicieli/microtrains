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
package io.github.carlomicieli.phonenumbers;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A phone number")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PhoneNumberTest {

  @Test
  void is_created_from_values_without_validation() {
    var phoneNumber = PhoneNumber.valueOf("044 668 18 00");
    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getValue()).isEqualTo("044 668 18 00");
  }

  @Test
  void is_created_from_only_valid_values() {
    var phoneNumber = PhoneNumber.valueOf("044 668 18 00", "CH");
    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getValue()).isEqualTo("+41 44 668 18 00");
  }

  @Test
  void is_should_fail_to_create_values_from_blank_inputs() {
    var ex = catchThrowableOfType(() -> PhoneNumber.valueOf(""), IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage()).isEqualTo("Phone number value cannot be blank or null");
  }

  @Test
  void is_should_fail_to_create_values_from_invalid_inputs() {
    var ex =
        catchThrowableOfType(
            () -> PhoneNumber.valueOf("aaaa bbbb", "CH"), IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage()).isEqualTo("Invalid value for a phone number");
  }
}
