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
package io.github.carlomicieli.catalog.valueobject;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.carlomicieli.lengths.MeasureUnit;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Gauge")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GaugeTest {

  @Test
  void is_created_from_value_in_millimeters() {
    var gauge = Gauge.ofMillimeters(16.5);
    assertThat(gauge).isNotNull();
    assertThat(gauge.getValue()).isEqualTo(BigDecimal.valueOf(16.5));
  }

  @Test
  void is_created_from_value_in_inches() {
    var gauge = Gauge.ofMillimeters(16.5);
    assertThat(gauge).isNotNull();
    assertThat(gauge.getValue()).isEqualTo(BigDecimal.valueOf(16.5));
  }

  @Test
  void is_created_from_a_value_and_a_measure_unit() {
    var gauge1 = Gauge.of(16.5f, MeasureUnit.MILLIMETERS);
    var gauge2 = Gauge.of(BigDecimal.valueOf(16.5), MeasureUnit.MILLIMETERS);

    assertThat(gauge1).isNotNull();
    assertThat(gauge1.getValue()).isEqualTo(BigDecimal.valueOf(16.5));
    assertThat(gauge1.getMeasureUnit()).isEqualTo(MeasureUnit.MILLIMETERS);
    assertThat(gauge2).isNotNull();
    assertThat(gauge2.getValue()).isEqualTo(BigDecimal.valueOf(16.5));
    assertThat(gauge2.getMeasureUnit()).isEqualTo(MeasureUnit.MILLIMETERS);
  }
}
