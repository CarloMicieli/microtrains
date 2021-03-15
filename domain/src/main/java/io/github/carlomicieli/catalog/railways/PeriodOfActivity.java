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

import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * It represent a period of activity for {@code Railway}. Railways can be either "active" or
 * "inactive". In the latter case, inactive railways will also have a termination date.
 */
@Data
@NoArgsConstructor
public class PeriodOfActivity {
    RailwayStatus railwayStatus;
    LocalDate operatingSince;
    LocalDate operatingUntil;

    public PeriodOfActivity(
            RailwayStatus railwayStatus, LocalDate operatingSince, LocalDate operatingUntil) {
        validatePeriod(railwayStatus, operatingSince, operatingUntil);

        this.operatingSince = operatingSince;
        this.operatingUntil = operatingUntil;
        this.railwayStatus = railwayStatus;
    }

    public boolean isActive() {
        return railwayStatus == RailwayStatus.ACTIVE;
    }

    public static PeriodOfActivity activeRailway(LocalDate operatingSince) {
        return new PeriodOfActivity(RailwayStatus.ACTIVE, operatingSince, null);
    }

    public static PeriodOfActivity inactiveRailway(
            LocalDate operatingSince, LocalDate operatingUntil) {
        return new PeriodOfActivity(RailwayStatus.INACTIVE, operatingSince, operatingUntil);
    }

    public static PeriodOfActivity defaultPeriodOfActivity() {
        return new PeriodOfActivity(RailwayStatus.ACTIVE, null, null);
    }

    private static void validatePeriod(
            RailwayStatus status, LocalDate operatingSince, LocalDate operatingUntil) {
        if (status == RailwayStatus.INACTIVE) {
            if (operatingSince == null || operatingUntil == null) {
                throw new IllegalArgumentException(
                        "Invalid period of activity: both operating until and since are required for an inactive railway");
            }

            if (operatingSince.compareTo(operatingUntil) > 0) {
                throw new IllegalArgumentException(
                        "Invalid period of activity: operating since > operating until");
            }
        } else {
            if (operatingUntil != null) {
                throw new IllegalArgumentException(
                        "Invalid period of activity: operating until has a value for an active railway");
            }
        }
    }
}
