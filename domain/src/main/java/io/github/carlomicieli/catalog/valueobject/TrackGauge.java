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
package io.github.carlomicieli.catalog.valueobject;

// In rail transport, track gauge or track gage is the spacing of the rails on a
// railway track and is measured between the inner faces of the load-bearing rails.
public enum TrackGauge {
  // In common usage the term "standard gauge" refers to 1,435 mm (4 ft 8 1⁄2 in)
  STANDARD,

  // In modern usage, broad gauge generally refers to track spaced significantly
  // wider than 1,435 mm (4 ft 8 1⁄2 in).
  BROAD,

  MEDIUM,

  // As the gauge of a railway is reduced the costs of construction can be reduced since narrow
  // gauges allow smaller-radius curves, allowing obstacles to be avoided rather than having to be
  // built over or through (valleys and hills)
  NARROW
}
