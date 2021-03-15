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
package io.github.carlomicieli.webapi.catalog.brands.createnew;

import io.github.carlomicieli.addresses.Address;
import io.github.carlomicieli.catalog.brands.createbrand.CreateBrandInput;
import io.github.carlomicieli.catalog.brands.createbrand.CreateBrandOutput;
import io.github.carlomicieli.catalog.brands.createbrand.CreateBrandUseCase;
import io.github.carlomicieli.web.usecases.UseCaseController;
import io.github.carlomicieli.webapi.ApiVersion;
import io.github.carlomicieli.webapi.common.AddressRequest;
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
@Controller("/api/brands")
@Version(ApiVersion.V1)
@AllArgsConstructor
public class BrandsController {
    private final CreateBrandUseCase useCase;
    private final CreateBrandPresenter presenter;
    private final UseCaseController useCaseController;

    @Post
    @Operation(summary = "Create a new model railways brand")
    @Status(HttpStatus.CREATED)
    @ApiResponse(responseCode = "201", description = "Brand created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "422", description = "Empty request")
    public Single<HttpResponse<?>> postNewBrand(@Body CreateBrandRequest request) {
        var address = convertAddress(request.address);
        var input =
                CreateBrandInput.builder()
                        .name(request.name)
                        .kind(request.kind)
                        .description(request.description)
                        .emailAddress(request.emailAddress)
                        .groupName(request.groupName)
                        .websiteUrl(request.websiteUrl)
                        .address(address)
                        .build();

        return useCaseController
                .<CreateBrandInput, CreateBrandOutput>handle()
                .useCase(useCase)
                .withInput(input)
                .andPresenter(presenter)
                .run();
    }

    public Address convertAddress(AddressRequest addressRequest) {
        return (addressRequest == null)
                ? null
                : Address.builder()
                        .country(addressRequest.getCountry())
                        .city(addressRequest.getCity())
                        .postalCode(addressRequest.getPostalCode())
                        .region(addressRequest.getRegion())
                        .line2(addressRequest.getLine2())
                        .line1(addressRequest.getLine1())
                        .build();
    }
}
