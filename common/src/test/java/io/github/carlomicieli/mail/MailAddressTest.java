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
package io.github.carlomicieli.mail;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A mail address")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MailAddressTest {

  @Test
  void is_created_from_its_address() {
    final String expected = "mail@mail.com";

    var mail = MailAddress.of("mail@mail.com");
    assertThat(mail).isNotNull();
    assertThat(mail.getAddress()).isEqualTo(expected);
  }

  @Test
  void should_accept_only_valid_addresses() {
    IllegalArgumentException thrown =
        catchThrowableOfType(() -> MailAddress.of("invalid mail"), IllegalArgumentException.class);
    assertThat(thrown.getMessage()).contains("Invalid mail address");
  }

  @Test
  void can_try_to_parse_the_mail_address() {
    var valid = MailAddress.tryParse("mail@mail.com");
    var invalid = MailAddress.tryParse("not really");

    assertThat(valid.isPresent()).isTrue();
    assertThat(invalid.isEmpty()).isTrue();
  }
}
