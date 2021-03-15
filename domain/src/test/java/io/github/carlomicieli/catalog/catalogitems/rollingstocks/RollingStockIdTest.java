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

import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A rolling stock identifier")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RollingStockIdTest {

    private final UUID expectedUUID;

    public RollingStockIdTest() {
        this.expectedUUID = UUID.fromString("9a19a4b3-a7ff-4004-8b05-fae51dd2fbca");
    }

    @Test
    void is_created_from_UUID_values() {
        var newId = RollingStockId.of(expectedUUID);
        assertThat(newId).isNotNull();
        assertThat(newId.toUUID()).isEqualTo(expectedUUID);
    }

    @Test
    void has_a_string_representation() {
        var newId = RollingStockId.of(expectedUUID);
        assertThat(newId.toString()).isEqualTo(expectedUUID.toString());
    }
}
