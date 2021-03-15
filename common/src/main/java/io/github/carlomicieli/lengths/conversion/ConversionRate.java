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

import java.math.BigDecimal;

/** Conversion rates for the most common lenghts measure units. */
public final class ConversionRate {
    public static final BigDecimal INCHES_TO_MILLIMETERS = BigDecimal.valueOf(25.4);

    public static final BigDecimal MILLIMETERS_TO_INCHES = BigDecimal.valueOf(0.0393701);

    public static final BigDecimal MILES_TO_KILOMETERS = BigDecimal.valueOf(1.60934);

    public static final BigDecimal KILOMETERS_TO_MILES = BigDecimal.valueOf(0.621371);
}
