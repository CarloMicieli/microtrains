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
import io.github.carlomicieli.domain.AggregateRootFactory;
import io.github.carlomicieli.util.Slug;
import java.time.Clock;
import java.util.List;
import java.util.function.Supplier;

public class CatalogItemFactory extends AggregateRootFactory<CatalogItem, CatalogItemId> {
  public CatalogItemFactory(Clock clock, Supplier<CatalogItemId> identifierSource) {
    super(clock, identifierSource);
  }

  /**
   * Creates a new {@code CatalogItem}, this method is not making any validation. The caller needs
   * to ensure only a valid object is created.
   */
  public CatalogItem createNewCatalogItem(
      BrandId brandId,
      String brandName,
      ItemNumber itemNumber,
      CatalogItemCategory category,
      ScaleId scaleId,
      PowerMethod powerMethod,
      String description,
      String prototypeDescription,
      String modelDescription,
      DeliveryDate deliveryDate,
      boolean available,
      List<RollingStock> rollingStocks) {
    var newId = generateNewId();
    var createdDate = getCurrentInstant();

    Slug slug = CatalogItem.buildSlug(brandName, itemNumber);

    return CatalogItem.builder()
        .id(newId)
        .slug(slug)
        .itemNumber(itemNumber)
        .available(available)
        .brand(brandId)
        .category(category)
        .description(description)
        .prototypeDescription(prototypeDescription)
        .modelDescription(modelDescription)
        .scale(scaleId)
        .deliveryDate(deliveryDate)
        .powerMethod(powerMethod)
        .createdDate(createdDate)
        .version(1)
        .build();
  }
}
