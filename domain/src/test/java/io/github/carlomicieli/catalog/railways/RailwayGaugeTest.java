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

import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import io.github.carlomicieli.lengths.Length;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Railway gauge")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayGaugeTest {

  private final Length ONE_METER = Length.ofMillimeters(BigDecimal.valueOf(1000));
  private final Length THIRTY_NINE_INCHES = Length.ofMillimeters(BigDecimal.valueOf(39.3701));

  @Test
  void is_created_using_a_builder() {
    var gauge =
        RailwayGauge.builder()
            .trackGauge(TrackGauge.NARROW)
            .millimeters(ONE_METER)
            .inches(THIRTY_NINE_INCHES)
            .build();

    assertThat(gauge).isNotNull();
    assertThat(gauge.getTrackGauge()).isEqualTo(TrackGauge.NARROW);
    assertThat(gauge.getMillimeters()).isEqualTo(ONE_METER);
    assertThat(gauge.getInches()).isEqualTo(THIRTY_NINE_INCHES);
  }

  @Test
  void is_created_from_millimeters_value() {
    var gauge = RailwayGauge.ofMillimeters(16.5, TrackGauge.STANDARD);

    assertThat(gauge).isNotNull();
    assertThat(gauge.getTrackGauge()).isEqualTo(TrackGauge.STANDARD);
    assertThat(gauge.getMillimeters().getValue()).isEqualTo(BigDecimal.valueOf(16.5));
    assertThat(gauge.getInches().getValue()).isEqualTo(BigDecimal.valueOf(0.65));
  }

  @Test
  void is_created_from_inches_value() {
    var gauge = RailwayGauge.ofInches(0.65, TrackGauge.STANDARD);

    assertThat(gauge).isNotNull();
    assertThat(gauge.getTrackGauge()).isEqualTo(TrackGauge.STANDARD);
    assertThat(gauge.getMillimeters().getValue()).isEqualTo(BigDecimal.valueOf(16.51));
    assertThat(gauge.getInches().getValue()).isEqualTo(BigDecimal.valueOf(0.65));
  }
}
