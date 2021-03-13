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

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/** An utility methods to calculate periods between a date and the current timestamp. */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Period {
  String label;
  int value;

  public static Period untilNow(Instant start) {
    return between(start, Instant.now());
  }

  public static Period untilNow(Instant start, Clock clock) {
    return between(start, Instant.now());
  }

  public static Period between(Instant start, Instant end) {
    var dur = Duration.between(start, end);
    var seconds = dur.getSeconds();
    throw new UnsupportedOperationException("TODO");
  }

  private static Period periodValue(String name, int value) {
    if (value > 1) {
      return new Period("interval." + name + ".more.label", value);
    } else if (value > 0) {
      return new Period("interval." + name + ".one.label", 1);
    }

    return null;
  }
}
