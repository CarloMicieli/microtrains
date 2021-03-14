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

import io.github.carlomicieli.catalog.catalogitems.Epoch;
import io.github.carlomicieli.catalog.railways.RailwayId;
import lombok.*;

@Data
@Builder
@With
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class FreightCar implements RollingStock {
  private final RollingStockId id;
  private final RailwayId railway;
  private final Category category;
  private final Epoch epoch;
  private final LengthOverBuffer lengthOverBuffer;
  private final MinRadius minRadius;
  private final Couplers couplers;
  private final String livery;
  private final String typeName;
  private final FreightCarType type;
}
