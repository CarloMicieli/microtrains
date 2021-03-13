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
package io.github.carlomicieli.webapi.catalog.catalogitems.createnew;

import io.micronaut.core.annotation.Introspected;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Introspected
public class CreateCatalogItemRequest {
  @NotBlank
  @Size(max = 25)
  String brand;

  @NotBlank
  @Size(max = 10)
  String itemNumber;

  @Size(max = 1000)
  String description;

  String scale;
  String railway;

  BigDecimal length;
}
