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
import io.github.carlomicieli.domain.AggregateRoot;
import io.github.carlomicieli.mail.MailAddress;
import io.github.carlomicieli.phonenumbers.PhoneNumber;
import io.github.carlomicieli.util.Slug;
import java.net.URL;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@Data
@AllArgsConstructor
@Builder
@With
public class Shop implements AggregateRoot<ShopId> {
  ShopId id;
  Slug slug;
  String name;
  Address address;
  PhoneNumber phoneNumber;
  MailAddress mailAddress;
  URL websiteUrl;
  Instant createdDate;
  Instant modifiedDate;
  int version;
}
