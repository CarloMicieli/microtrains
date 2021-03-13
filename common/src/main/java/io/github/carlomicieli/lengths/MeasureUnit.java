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
package io.github.carlomicieli.lengths;

import io.github.carlomicieli.lengths.conversion.MeasureUnitConverter;
import io.github.carlomicieli.lengths.conversion.MeasureUnitsConverters;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public enum MeasureUnit {
  MILLIMETERS("mm"),
  INCHES("in"),
  MILES("mi"),
  KILOMETERS("km");

  private final String symbol;

  MeasureUnit(String symbol) {
    this.symbol = symbol;
  }

  String getSymbol() {
    return symbol;
  }

  String buildString(BigDecimal value) {
    var df = new DecimalFormat("#,###.0");
    return df.format(value) + " " + this.symbol;
  }

  /**
   * Returns the appropriate converter to convert from this MeasureUnit to the other If such
   * converter does not exist, a converter that always failed is returned instead.
   */
  MeasureUnitConverter convertTo(MeasureUnit other) {
    return MeasureUnitsConverters.INSTANCE.getConverter(this, other);
  }
}
