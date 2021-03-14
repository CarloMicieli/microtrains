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
package io.github.carlomicieli.catalog.scales;

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.catalog.valueobject.Gauge;
import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A Scale factory")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ScaleFactoryTest {
  private static final Instant FIXED_INSTANT = Instant.parse("1988-11-25T10:15:30.00Z");
  private static final ScaleId FIXED_SCALE_ID =
      ScaleId.of(UUID.fromString("e9f5ed5a-edb6-46fa-a55a-bc8632e89d3a"));
  private static final Clock FIXED_CLOCK = Clock.fixed(FIXED_INSTANT, ZoneId.systemDefault());

  private final ScaleFactory factory;

  public ScaleFactoryTest() {
    this.factory = new ScaleFactory(FIXED_CLOCK, () -> FIXED_SCALE_ID);
  }

  @Test
  void should_create_new_scales() {
    var ratio = Ratio.of(87);
    var gauge =
        ScaleGauge.builder()
            .trackGauge(TrackGauge.STANDARD)
            .millimetres(Gauge.ofMillimeters(16.5))
            .inches(Gauge.ofInches(0.65))
            .build();

    var scale = factory.createNewScale("H0", ratio, gauge, "Scale Half-Zero", 100);

    assertThat(scale).isNotNull();
    assertThat(scale.getName()).isEqualTo("H0");
    assertThat(scale.getRatio()).isEqualTo(ratio);
    assertThat(scale.getGauge()).isEqualTo(gauge);
    assertThat(scale.getDescription()).isEqualTo("Scale Half-Zero");
    assertThat(scale.getWeight()).isEqualTo(100);
    assertThat(scale.getCreatedDate()).isNotNull();
    assertThat(scale.getModifiedDate()).isNull();
    assertThat(scale.getVersion()).isEqualTo(1);
  }
}
