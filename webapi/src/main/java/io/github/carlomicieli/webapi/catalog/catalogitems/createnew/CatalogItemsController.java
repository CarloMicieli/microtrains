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

import io.github.carlomicieli.catalog.catalogitems.createcatalogitem.CreateCatalogItemInput;
import io.github.carlomicieli.catalog.catalogitems.createcatalogitem.CreateCatalogItemOutput;
import io.github.carlomicieli.catalog.catalogitems.createcatalogitem.CreateCatalogItemUseCase;
import io.github.carlomicieli.web.usecases.UseCaseController;
import io.github.carlomicieli.webapi.ApiVersion;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Status;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Secured(SecurityRule.IS_ANONYMOUS)
@Slf4j
@Controller("/api/catalog_items")
@Version(ApiVersion.V1)
@AllArgsConstructor
public class CatalogItemsController {

    private final CreateCatalogItemUseCase useCase;
    private final CreateCatalogItemPresenter presenter;
    private final UseCaseController useCaseController;

    @Post
    @Operation(summary = "Create a new catalog item")
    @Status(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Catalog item created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "422", description = "Empty request")
    public Single<HttpResponse<?>> postNewCatalogItem(@Body CreateCatalogItemRequest request) {
        var input =
                CreateCatalogItemInput.builder()
                        .brand(request.brand)
                        .itemNumber(request.itemNumber)
                        .length(request.length)
                        .railway(request.railway)
                        .scale(request.scale)
                        .description(request.description)
                        .build();

        return useCaseController
                .<CreateCatalogItemInput, CreateCatalogItemOutput>handle()
                .useCase(useCase)
                .withInput(input)
                .andPresenter(presenter)
                .run();
    }
}
