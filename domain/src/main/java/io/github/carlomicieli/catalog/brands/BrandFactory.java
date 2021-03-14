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
package io.github.carlomicieli.catalog.brands;

import io.github.carlomicieli.addresses.Address;
import io.github.carlomicieli.domain.AggregateRootFactory;
import io.github.carlomicieli.mail.MailAddress;
import io.github.carlomicieli.util.Slug;
import java.net.URL;
import java.time.Clock;
import java.util.function.Supplier;

public final class BrandFactory extends AggregateRootFactory<Brand, BrandId> {
  public BrandFactory(Clock clock, Supplier<BrandId> identifierSource) {
    super(clock, identifierSource);
  }

  /**
   * Creates a new {@code Brand}, this method is not making any validation. The caller needs to
   * ensure only a valid object is created.
   */
  public Brand createNewBrand(
      String name,
      String companyName,
      URL websiteUrl,
      String groupName,
      String description,
      Address address,
      BrandKind brandKind,
      MailAddress mailAddress) {
    var newId = generateNewId();
    var createdDate = getCurrentInstant();

    Slug brandSlug = Brand.buildSlug(name);

    return Brand.builder()
        .id(newId)
        .name(name)
        .slug(brandSlug)
        .companyName(companyName)
        .groupName(groupName)
        .address(address)
        .mailAddress(mailAddress)
        .brandKind(brandKind)
        .version(1)
        .createdDate(createdDate)
        .build();
  }
}
