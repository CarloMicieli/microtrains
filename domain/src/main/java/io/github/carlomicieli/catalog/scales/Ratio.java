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

import java.math.BigDecimal;
import lombok.Value;

/** It represents the {@code Ratio} between a model railway size and the size of an actual train. */
@Value
public class Ratio implements Comparable<Ratio> {
    BigDecimal value;

    private Ratio(BigDecimal value) {
        if (value.signum() <= 0) {
            throw new IllegalArgumentException("Ratio: value must be positive");
        }
        this.value = value;
    }

    public static Ratio of(long value) {
        return new Ratio(BigDecimal.valueOf(value));
    }

    public static Ratio of(double value) {
        return new Ratio(BigDecimal.valueOf(value));
    }

    public static Ratio of(BigDecimal value) {
        return new Ratio(value);
    }

    @Override
    public String toString() {
        return String.format("1:%s", value.toString());
    }

    @Override
    public int compareTo(Ratio that) {
        return that.getValue().compareTo(this.getValue());
    }
}
