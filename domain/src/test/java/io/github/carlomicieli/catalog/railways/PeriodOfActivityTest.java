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

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A period of activity")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PeriodOfActivityTest {

  private static final LocalDate SINCE_DATE = LocalDate.of(1905, 7, 15);
  private static final LocalDate UNTIL_DATE = LocalDate.of(1999, 12, 31);

  @Test
  void can_represent_active_railways() {
    var period = PeriodOfActivity.activeRailway(SINCE_DATE);
    assertThat(period).isNotNull();
    assertThat(period.getRailwayStatus()).isEqualTo(RailwayStatus.ACTIVE);
    assertThat(period.getOperatingSince()).isEqualTo(SINCE_DATE);
    assertThat(period.isActive()).isTrue();
  }

  @Test
  void can_represent_inactive_railways() {
    var period = PeriodOfActivity.inactiveRailway(SINCE_DATE, UNTIL_DATE);
    assertThat(period).isNotNull();
    assertThat(period.getRailwayStatus()).isEqualTo(RailwayStatus.INACTIVE);
    assertThat(period.getOperatingSince()).isEqualTo(SINCE_DATE);
    assertThat(period.getOperatingUntil()).isEqualTo(UNTIL_DATE);
    assertThat(period.isActive()).isFalse();
  }

  @Test
  void is_active_without_starting_and_termination_dates_by_default() {
    var period = PeriodOfActivity.defaultPeriodOfActivity();
    assertThat(period).isNotNull();
    assertThat(period.getRailwayStatus()).isEqualTo(RailwayStatus.ACTIVE);
    assertThat(period.getOperatingSince()).isNull();
    assertThat(period.getOperatingUntil()).isNull();
  }

  @Test
  void when_inactive_must_have_both_dates() {
    var ex =
        catchThrowableOfType(
            () -> new PeriodOfActivity(RailwayStatus.INACTIVE, null, null),
            IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage())
        .isEqualTo(
            "Invalid period of activity: both operating until and since are required for an inactive railway");
  }

  @Test
  void when_inactive_operating_since_must_be_before_operating_until_date() {
    var ex =
        catchThrowableOfType(
            () -> new PeriodOfActivity(RailwayStatus.INACTIVE, UNTIL_DATE, SINCE_DATE),
            IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage())
        .isEqualTo("Invalid period of activity: operating since > operating until");
  }

  @Test
  void when_active_operating_until_date_is_not_allowed() {
    var ex =
        catchThrowableOfType(
            () -> new PeriodOfActivity(RailwayStatus.ACTIVE, SINCE_DATE, UNTIL_DATE),
            IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage())
        .isEqualTo("Invalid period of activity: operating until has a value for an active railway");
  }
}
