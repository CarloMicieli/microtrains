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

import io.github.carlomicieli.webapi.ApiVersion;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.http.uri.UriBuilder;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
@AllArgsConstructor
@Controller("/catalog_items")
@Version(ApiVersion.V1)
@Validated
public class CatalogItemsController {
  @Post
  @Operation(summary = "Create a new catalog item")
  @Status(HttpStatus.CREATED)
  @ApiResponse(responseCode = "201", description = "Catalog item created")
  @ApiResponse(responseCode = "400", description = "Invalid request")
  @ApiResponse(responseCode = "422", description = "Empty request")
  public HttpResponse<?> postNewCatalogItem(@Valid @Body CreateCatalogItemRequest request) {
    var resourceUri = UriBuilder.of("/catalog_items/{id}").expand(Map.of("id", UUID.randomUUID()));
    return HttpResponse.created(resourceUri);
  }
}
