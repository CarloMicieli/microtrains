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

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.catalog.brands.BrandId;
import io.github.carlomicieli.catalog.catalogitems.deliverydates.DeliveryDate;
import io.github.carlomicieli.catalog.scales.ScaleId;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("CategoryItem factory")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CatalogItemFactoryTest {

  private static final Instant FIXED_INSTANT = Instant.parse("1988-11-25T10:15:30.00Z");
  private static final BrandId FIXED_BRAND_ID =
      BrandId.of(UUID.fromString("e45f901f-0751-489c-910e-ce112d06ccfe"));
  private static final ScaleId FIXED_SCALE_ID =
      ScaleId.of(UUID.fromString("e9f5ed5a-edb6-46fa-a55a-bc8632e89d3a"));
  private static final CatalogItemId FIXED_CATALOG_ITEM_ID =
      CatalogItemId.of(UUID.fromString("0218a288-10da-47c4-8e19-b4dad6906579"));
  private static final Clock FIXED_CLOCK = Clock.fixed(FIXED_INSTANT, ZoneId.systemDefault());

  private final CatalogItemFactory factory;

  public CatalogItemFactoryTest() {
    this.factory = new CatalogItemFactory(FIXED_CLOCK, () -> FIXED_CATALOG_ITEM_ID);
  }

  @Test
  void should_create_new_catalog_items() {
    var itemNumber = ItemNumber.of("123456");
    var deliveryDate = DeliveryDate.of(2020, 1);
    var catalogItem =
        factory.createNewCatalogItem(
            FIXED_BRAND_ID,
            "ACME",
            itemNumber,
            CatalogItemCategory.LOCOMOTIVES,
            FIXED_SCALE_ID,
            PowerMethod.DC,
            "My first loco",
            "My prototype description",
            "My model description",
            deliveryDate,
            true,
            Collections.emptyList());
    assertThat(catalogItem).isNotNull();
    assertThat(catalogItem.getId()).isEqualTo(FIXED_CATALOG_ITEM_ID);
    assertThat(catalogItem.getPowerMethod()).isEqualTo(PowerMethod.DC);
    assertThat(catalogItem.getItemNumber()).isEqualTo(itemNumber);
    assertThat(catalogItem.getScale()).isEqualTo(FIXED_SCALE_ID);
    assertThat(catalogItem.getCategory()).isEqualTo(CatalogItemCategory.LOCOMOTIVES);
    assertThat(catalogItem.getDescription()).isEqualTo("My first loco");
    assertThat(catalogItem.getModelDescription()).isEqualTo("My model description");
    assertThat(catalogItem.getPrototypeDescription()).isEqualTo("My prototype description");
    assertThat(catalogItem.getDeliveryDate()).isEqualTo(deliveryDate);
    assertThat(catalogItem.getSlug()).isNotNull();
    assertThat(catalogItem.getVersion()).isEqualTo(1);
    assertThat(catalogItem.getCreatedDate()).isNotNull();
    assertThat(catalogItem.getModifiedDate()).isNull();
  }
}
