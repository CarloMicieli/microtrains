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

import io.github.carlomicieli.lengths.Length;
import io.github.carlomicieli.lengths.MeasureUnit;
import io.github.carlomicieli.lengths.conversion.MeasureUnitsConverters;
import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Value
public class LengthOverBuffer implements Comparable<LengthOverBuffer> {
    Length inches;
    Length millimeters;

    public static LengthOverBuffer of(BigDecimal inches, BigDecimal millimeters) {
        return new LengthOverBuffer(Length.ofInches(inches), Length.ofMillimeters(millimeters));
    }

    public static LengthOverBuffer ofMillimeters(BigDecimal millimeters) {
        var converter =
                MeasureUnitsConverters.INSTANCE.getConverter(
                        MeasureUnit.MILLIMETERS, MeasureUnit.INCHES);
        return LengthOverBuffer.of(converter.convert(millimeters), millimeters);
    }

    public static LengthOverBuffer ofInches(BigDecimal inches) {
        var converter =
                MeasureUnitsConverters.INSTANCE.getConverter(
                        MeasureUnit.INCHES, MeasureUnit.MILLIMETERS);
        return LengthOverBuffer.of(inches, converter.convert(inches));
    }

    @Override
    public int compareTo(LengthOverBuffer that) {
        return this.millimeters.compareTo(that.millimeters);
    }
}
