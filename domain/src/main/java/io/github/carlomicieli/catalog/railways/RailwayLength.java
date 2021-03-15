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

import io.github.carlomicieli.lengths.Length;
import io.github.carlomicieli.lengths.MeasureUnit;
import io.github.carlomicieli.lengths.conversion.MeasureUnitsConverters;
import java.math.BigDecimal;
import lombok.*;

/**
 * It represents the total length for {@code Railway}s.
 *
 * <p>The length is available in both kilometers and miles.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@Builder
public class RailwayLength {
    Length kilometers;
    Length miles;

    /**
     * Creates a new {@code RailwayLength} from a kilometers value, converting this value to miles
     */
    public static RailwayLength ofKilometers(double value) {
        var lengthValue = BigDecimal.valueOf(value);
        var converter =
                MeasureUnitsConverters.INSTANCE.getConverter(
                        MeasureUnit.KILOMETERS, MeasureUnit.MILES);
        return new RailwayLength(
                Length.of(lengthValue, MeasureUnit.KILOMETERS),
                Length.of(converter.convert(lengthValue), MeasureUnit.MILES));
    }

    /**
     * Creates a new {@code RailwayLength} from a miles value, converting this value to kilometers
     */
    public static RailwayLength ofMiles(double value) {
        var lengthValue = BigDecimal.valueOf(value);
        var converter =
                MeasureUnitsConverters.INSTANCE.getConverter(
                        MeasureUnit.MILES, MeasureUnit.KILOMETERS);
        return new RailwayLength(
                Length.of(converter.convert(lengthValue), MeasureUnit.KILOMETERS),
                Length.of(lengthValue, MeasureUnit.MILES));
    }
}
