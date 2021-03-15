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
package io.github.carlomicieli.collecting.collections;

import io.github.carlomicieli.collecting.valueobject.Owner;
import io.github.carlomicieli.collecting.valueobject.Price;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@AllArgsConstructor
@Builder
@With
public class CollectionStats {
    CollectionId id;
    Owner owner;
    Instant modifiedDate;
    Price totalValue;
    List<CollectionStatsItem> items;

    /** Generates the statistics from the {@code Collection}. */
    public static CollectionStats fromCollection(Collection collection) {
        throw new UnsupportedOperationException("TODO");
    }
}
