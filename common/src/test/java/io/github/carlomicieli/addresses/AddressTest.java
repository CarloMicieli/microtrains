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
package io.github.carlomicieli.addresses;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("An address")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AddressTest {
    @Test
    void is_created() {
        var address =
                Address.builder()
                        .line1("Stuttgarter Straße 55-57")
                        .line2("A")
                        .city("Göppingen")
                        .country("Germany")
                        .region("Baden-Württemberg")
                        .postalCode("D-73033")
                        .build();
        assertThat(address).isNotNull();
        assertThat(address.getLine1()).isEqualTo("Stuttgarter Straße 55-57");
        assertThat(address.getLine2()).isEqualTo("A");
        assertThat(address.getCity()).isEqualTo("Göppingen");
        assertThat(address.getCountry()).isEqualTo("Germany");
        assertThat(address.getRegion()).isEqualTo("Baden-Württemberg");
        assertThat(address.getPostalCode()).isEqualTo("D-73033");
    }

    @Test
    void is_empty_when_no_value_has_been_provided() {
        var isEmpty = Address.isEmpty(Address.NULL_ADDRESS);
        assertThat(isEmpty).isTrue();
        assertThat(Address.isEmpty(null)).isTrue();
    }
}
