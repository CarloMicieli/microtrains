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
package io.github.carlomicieli.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class PeriodTest {
  static final String YEARS_LABEL = "interval.years.more.label";
  static final String YEAR_LABEL = "interval.years.one.label";
  static final String MONTHS_LABEL = "interval.months.more.label";
  static final String MONTH_LABEL = "interval.months.one.label";
  static final String WEEKS_LABEL = "interval.weeks.more.label";
  static final String WEEK_LABEL = "interval.weeks.one.label";
  static final String DAYS_LABEL = "interval.days.more.label";
  static final String DAY_LABEL = "interval.days.one.label";
  static final String HOURS_LABEL = "interval.hours.more.label";
  static final String HOUR_LABEL = "interval.hours.one.label";
  static final String MINUTES_LABEL = "interval.minutes.more.label";
  static final String MINUTE_LABEL = "interval.minutes.one.label";

  @Test
  @Disabled
  void shouldCalculatePeriodForYears() {
    int numberOfYears = 3;
    Instant start = Instant.parse("2010-11-25T10:15:30.00Z");
    Instant end = Instant.parse("2010-11-25T12:15:30.00Z");

    var period = Period.between(start, end);

    assertThat(period).isNotNull();
    assertThat(period.getLabel()).isEqualTo(YEARS_LABEL);
    assertThat(period.getValue()).isEqualTo(numberOfYears);
  }
}
