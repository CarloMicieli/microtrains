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
package io.github.carlomicieli.catalog.catalogitems.rollingstocks;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Service level")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ServiceLevelTest {

  @Test
  void is_created_parsing_a_string_value() {
    var serviceLevel = ServiceLevel.parse("1cl");

    assertThat(serviceLevel).isNotNull();
    assertThat(serviceLevel).isEqualTo(ServiceLevel.FirstClass);
  }

  @Test
  void is_able_to_parse_multiple_values() {
    var serviceLevel = ServiceLevel.parse("1cl/3cl/2cl");

    assertThat(serviceLevel).isNotNull();
    assertThat(serviceLevel.toString()).isEqualTo("1cl/2cl/3cl");
  }

  @Test
  void is_able_to_parse_multiple_values_also_when_duplicates_are_present() {
    var serviceLevel = ServiceLevel.parse("1cl/2cl/2cl");

    assertThat(serviceLevel).isNotNull();
    assertThat(serviceLevel.toString()).isEqualTo("1cl/2cl");
  }

  @Test
  void is_not_able_to_parse_invalid_service_levels() {
    var serviceLevel = ServiceLevel.parse("aaa/2cl/2cl");

    assertThat(serviceLevel).isNull();
  }

  @Test
  void is_not_able_to_optionally_parse_service_levels() {
    var serviceLevel1 = ServiceLevel.tryParse("aaa/2cl/2cl");
    var serviceLevel2 = ServiceLevel.tryParse("1cl/2cl/2cl");

    assertThat(serviceLevel1).isEmpty();
    assertThat(serviceLevel2).isPresent();
    assertThat(serviceLevel2.get().toString()).isEqualTo("1cl/2cl");
  }
}
