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

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Min radius")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MinRadiusTest {

    @Test
    void is_created_from_its_value_in_millimeters() {
        var minRadius = MinRadius.ofMillimeters(360);

        assertThat(minRadius).isNotNull();
        assertThat(minRadius.getMillimeters()).isEqualTo(BigDecimal.valueOf(360));
    }
}
