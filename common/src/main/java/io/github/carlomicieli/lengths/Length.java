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

import java.math.BigDecimal;
import java.util.Objects;
import lombok.Data;
import lombok.With;

/** It represents a non negative unit of length. */
@Data
@With
public final class Length implements Comparable<Length> {
    private final BigDecimal value;
    private final MeasureUnit measureUnit;

    private Length(BigDecimal value, MeasureUnit measureUnit) {
        if (value.signum() < 0) {
            throw new IllegalArgumentException("A length value cannot be negative");
        }

        this.value = value;
        this.measureUnit = measureUnit;
    }

    @Override
    public int compareTo(Length that) {
        if (this.measureUnit == that.measureUnit) {
            return this.value.compareTo(that.value);
        } else {
            var converted = this.measureUnit.convertTo(that.measureUnit).convert(this.value);
            return converted.compareTo(that.value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Length that = (Length) o;

        if (measureUnit == that.measureUnit) {
            return value.compareTo(that.value) == 0;
        } else {
            var converted = this.measureUnit.convertTo(that.measureUnit).convert(this.value);
            return converted.compareTo(that.value) == 0;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, measureUnit);
    }

    @Override
    public String toString() {
        return measureUnit.buildString(value);
    }

    public static Length of(BigDecimal value, MeasureUnit measureUnit) {
        return new Length(value, measureUnit);
    }

    public static Length ofKilometers(BigDecimal value) {
        return new Length(value, MeasureUnit.KILOMETERS);
    }

    public static Length ofMiles(BigDecimal value) {
        return new Length(value, MeasureUnit.MILES);
    }

    public static Length ofMillimeters(BigDecimal value) {
        return new Length(value, MeasureUnit.MILLIMETERS);
    }

    public static Length ofInches(BigDecimal value) {
        return new Length(value, MeasureUnit.INCHES);
    }

    public static Length valueOf(double value, MeasureUnit measureUnit) {
        return new Length(BigDecimal.valueOf(value), measureUnit);
    }

    public static Length valueOf(long value, MeasureUnit measureUnit) {
        return new Length(BigDecimal.valueOf(value), measureUnit);
    }

    /** A constant length of zero millimeters */
    public static final Length ZERO_MILLIMETERS =
            new Length(BigDecimal.ZERO, MeasureUnit.MILLIMETERS);

    /** A constant length of zero inches */
    public static final Length ZERO_INCHES = new Length(BigDecimal.ZERO, MeasureUnit.INCHES);

    /** A constant length of zero kilometers */
    public static final Length ZERO_KILOMETERS =
            new Length(BigDecimal.ZERO, MeasureUnit.KILOMETERS);

    /** A constant length of zero miles */
    public static final Length ZERO_MILES = new Length(BigDecimal.ZERO, MeasureUnit.MILES);
}
