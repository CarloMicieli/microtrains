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
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

public enum MeasureUnitsConverters {
  INSTANCE();

  private final Map<Conversion, MeasureUnitConverter> converterMap;
  private final MeasureUnitConverter sameUnitConvert = new SameUnitConverter();

  private MeasureUnitsConverters() {
    converterMap =
        Map.ofEntries(
            converterEntry(
                MeasureUnit.INCHES, MeasureUnit.MILLIMETERS, ConversionRate.INCHES_TO_MILLIMETERS),
            converterEntry(
                MeasureUnit.MILLIMETERS, MeasureUnit.INCHES, ConversionRate.MILLIMETERS_TO_INCHES),
            converterEntry(
                MeasureUnit.KILOMETERS, MeasureUnit.MILES, ConversionRate.KILOMETERS_TO_MILES),
            converterEntry(
                MeasureUnit.MILES, MeasureUnit.KILOMETERS, ConversionRate.MILES_TO_KILOMETERS));
  }

  private static Map.Entry<Conversion, MeasureUnitConverter> converterEntry(
      MeasureUnit from, MeasureUnit to, BigDecimal conversionRate) {
    return Map.entry(
        new Conversion(from, to), new MeasureUnitConverterByRate(from, to, conversionRate));
  }

  public MeasureUnitConverter getConverter(MeasureUnit from, MeasureUnit to) {
    if (from == to) {
      return sameUnitConvert;
    }

    return converterMap.getOrDefault(new Conversion(from, to), new InvalidConverter(from, to));
  }

  @Data
  @AllArgsConstructor
  static class Conversion {
    private final MeasureUnit from;
    private final MeasureUnit to;
  }
}
