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
package io.github.carlomicieli.catalog.catalogitems;

import io.github.carlomicieli.catalog.brands.BrandId;
import io.github.carlomicieli.catalog.catalogitems.deliverydates.DeliveryDate;
import io.github.carlomicieli.catalog.catalogitems.rollingstocks.RollingStock;
import io.github.carlomicieli.catalog.scales.ScaleId;
import io.github.carlomicieli.domain.AggregateRoot;
import io.github.carlomicieli.util.Slug;
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;
import lombok.*;

@Data
@Builder
@With
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class CatalogItem implements AggregateRoot<CatalogItemId> {

    private final CatalogItemId id;
    private final BrandId brand;
    private final ScaleId scale;
    private final ItemNumber itemNumber;
    private final Slug slug;
    private final CatalogItemCategory category;
    private final String description;
    private final String prototypeDescription;
    private final String modelDescription;
    private final List<RollingStock> rollingStocks;
    private final PowerMethod powerMethod;
    private final DeliveryDate deliveryDate;
    private final boolean available;
    private final int version;
    private final Instant createdDate;
    private final Instant modifiedDate;

    public static Slug buildSlug(String brandName, ItemNumber itemNumber) {
        return Slug.ofValues(brandName, itemNumber.getValue());
    }

    /** Returns the number of rolling stocks for this catalog item */
    public int getCount() {
        return rollingStocks.size();
    }

    public Stream<RollingStock> getRollingStocks() {
        return rollingStocks.stream();
    }
}
