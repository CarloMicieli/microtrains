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

import static org.assertj.core.api.Assertions.catchThrowableOfType;

import io.github.carlomicieli.web.assertions.HttpClientResponseExceptionAssert;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpMethod;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import javax.inject.Inject;
import org.junit.jupiter.api.*;

@MicronautTest
@DisplayName("POST /api/brands")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandsControllerTest {
    @Inject EmbeddedServer server;

    @Inject
    @Client("/api/brands")
    HttpClient client;

    @Test
    void should_return_422_Unprocessable_Entity_when_the_request_body_is_empty() {
        var request = HttpRequest.create(HttpMethod.POST, "/");
        var thrown =
                catchThrowableOfType(
                        () -> client.toBlocking().exchange(request),
                        HttpClientResponseException.class);

        HttpClientResponseExceptionAssert.assertThat(thrown)
                .hasStatus(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void should_return_400_Bad_Request_when_the_request_is_invalid() {
        var request = HttpRequest.POST("/", CreateBrandRequest.builder().build());
        var thrown =
                catchThrowableOfType(
                        () -> client.toBlocking().exchange(request),
                        HttpClientResponseException.class);
        HttpClientResponseExceptionAssert.assertThat(thrown).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_return_201_Created_when_the_brand_is_created_correctly() {
        var request =
                CreateBrandRequest.builder()
                        .name("ACME")
                        .description("My description goes here")
                        .kind("Industrial")
                        .companyName("Associazione Costruzioni Modellistiche Esatte")
                        .groupName("Group")
                        .emailAddress("mail@mail.com")
                        .websiteUrl("http://www.acmetreni.com")
                        .build();

        var response = client.toBlocking().exchange(HttpRequest.POST("/", request));

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatus());
        Assertions.assertNotNull(response.header(HttpHeaders.LOCATION));
    }
}
