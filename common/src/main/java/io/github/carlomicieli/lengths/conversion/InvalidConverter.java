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
package io.github.carlomicieli.lengths.conversion;

import io.github.carlomicieli.lengths.MeasureUnit;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/** It represents a measure conversion that will always fail. */
@AllArgsConstructor
@Getter
@ToString
public final class InvalidConverter implements MeasureUnitConverter {

  private final MeasureUnit fromUnit;
  private final MeasureUnit toUnit;

  @Override
  public BigDecimal convert(BigDecimal value, int decimals) {
    var msg = String.format("Unable to find a suitable converter from %s to %s", fromUnit, toUnit);
    throw new RuntimeException(msg);
  }
}
