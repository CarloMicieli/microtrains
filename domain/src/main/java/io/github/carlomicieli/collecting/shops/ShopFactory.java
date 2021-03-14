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
package io.github.carlomicieli.collecting.shops;

import io.github.carlomicieli.addresses.Address;
import io.github.carlomicieli.domain.AggregateRootFactory;
import io.github.carlomicieli.mail.MailAddress;
import io.github.carlomicieli.phonenumbers.PhoneNumber;
import java.net.URL;
import java.time.Clock;
import java.util.function.Supplier;

public class ShopFactory extends AggregateRootFactory<Shop, ShopId> {
  public ShopFactory(Clock clock, Supplier<ShopId> identifierSource) {
    super(clock, identifierSource);
  }

  public Shop createNewShop(
      String name,
      URL websiteUrl,
      MailAddress emailAddress,
      Address address,
      PhoneNumber phoneNumber) {
    throw new UnsupportedOperationException("TODO");
  }
}
