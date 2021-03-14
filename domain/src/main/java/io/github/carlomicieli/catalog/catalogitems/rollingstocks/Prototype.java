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

import lombok.*;

@AllArgsConstructor(staticName = "of")
@Value
@Builder
@With
public class Prototype {
  String className;
  String roadNumber;
  String series;

  public static Prototype ofLocomotive(String className, String roadNumber) {
    return new Prototype(className, roadNumber, null);
  }

  public static Prototype ofLocomotive(String className, String roadNumber, String series) {
    return new Prototype(className, roadNumber, series);
  }
}
