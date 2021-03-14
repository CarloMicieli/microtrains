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

import io.github.carlomicieli.domain.AggregateRoot;
import io.github.carlomicieli.util.Slug;
import java.time.Instant;
import lombok.*;

/**
 * A model trains <em>Scale</em> is the relationship between its size and the size of an actual
 * train, usually measured as a ratio or as a millimetre to inch conversion. OO scale is said to be
 * 4mm:ft or 1:76.
 *
 * <p>A model trains <em>Gauge</em> is the distance between the inner edges of the two rails that it
 * runs on.
 */
@Value
@Builder
@With
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Scale implements AggregateRoot<ScaleId> {
  ScaleId id;
  Slug slug;
  String name;
  Ratio ratio;
  ScaleGauge gauge;
  String description;
  Integer weight;
  int version;
  Instant createdDate;
  Instant modifiedDate;

  public static Slug buildSlug(String name) {
    return Slug.of(name);
  }
}
