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
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Value;

/** Minimum curve radius for rolling stocks, in millimeters. */
@AllArgsConstructor
@Value
public class MinRadius {
  Length value;

  public static MinRadius ofMillimeters(BigDecimal value) {
    return new MinRadius(Length.ofMillimeters(value));
  }

  public static MinRadius ofMillimeters(int value) {
    return new MinRadius(Length.ofMillimeters(BigDecimal.valueOf(value)));
  }

  private Length getValue() {
    return value;
  }

  /** Returns this minimum radius in millimeters */
  public BigDecimal getMillimeters() {
    return value.getValue();
  }
}
