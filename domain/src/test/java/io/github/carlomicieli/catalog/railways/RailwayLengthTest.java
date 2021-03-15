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
package io.github.carlomicieli.catalog.railways;

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.lengths.Length;
import io.github.carlomicieli.lengths.MeasureUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A railway length")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayLengthTest {

    private static final Length ONE_HUNDRED_KM = Length.valueOf(100, MeasureUnit.KILOMETERS);
    private static final Length SIXTY_TWO_MILES = Length.valueOf(62, MeasureUnit.MILES);

    @Test
    void is_created_via_a_builder() {
        var railwayLength =
                RailwayLength.builder().kilometers(ONE_HUNDRED_KM).miles(SIXTY_TWO_MILES).build();

        assertThat(railwayLength.getKilometers()).isEqualTo(ONE_HUNDRED_KM);
        assertThat(railwayLength.getMiles()).isEqualTo(SIXTY_TWO_MILES);
    }

    @Test
    void is_created_from_kilometers_value() {
        var railwayLength = RailwayLength.ofKilometers(100.0);
        assertThat(railwayLength).isNotNull();
        assertThat(railwayLength.getKilometers()).isEqualTo(ONE_HUNDRED_KM);
        assertThat(railwayLength.getMiles()).isEqualTo(Length.valueOf(62.14, MeasureUnit.MILES));
    }

    @Test
    void is_created_from_miles_value() {
        var railwayLength = RailwayLength.ofMiles(62.0);
        assertThat(railwayLength).isNotNull();
        assertThat(railwayLength.getKilometers())
                .isEqualTo(Length.valueOf(99.78, MeasureUnit.KILOMETERS));
        assertThat(railwayLength.getMiles()).isEqualTo(SIXTY_TWO_MILES);
    }
}
