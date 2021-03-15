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
package io.github.carlomicieli.catalog.brands.createbrand;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.github.carlomicieli.addresses.Address;
import io.github.carlomicieli.catalog.brands.Brand;
import io.github.carlomicieli.catalog.brands.BrandFactory;
import io.github.carlomicieli.catalog.brands.BrandId;
import io.github.carlomicieli.catalog.brands.BrandsRepository;
import io.github.carlomicieli.util.Slug;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Create brand use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CreateBrandUseCaseTest {
    private final CreateBrandUseCase useCase;
    private final CreateBrandOutputPort outputPort;
    private final BrandsRepository brandsRepository;

    public CreateBrandUseCaseTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        var brandsFactory =
                new BrandFactory(
                        Clock.fixed(Instant.now(), ZoneId.systemDefault()), BrandId::randomId);

        this.brandsRepository = mock(BrandsRepository.class);
        this.outputPort = mock(CreateBrandOutputPort.class);
        this.useCase =
                new CreateBrandUseCase(
                        factory.getValidator(), outputPort, brandsFactory, brandsRepository);
    }

    @Test
    void it_should_create_a_new_brand() {
        when(brandsRepository.add(isA(Brand.class))).thenReturn(BrandId.randomId());
        var input =
                CreateBrandInput.builder()
                        .name("ACME")
                        .companyName("Associazione Costruzioni Modellistiche Esatte")
                        .websiteUrl("http://www.acmetreni.com")
                        .emailAddress("mail@acmetreni.com")
                        .description("Model railway maker")
                        .kind("Industrial")
                        .address(
                                Address.builder()
                                        .line1("Address line1")
                                        .line2("Address line2")
                                        .region("Region")
                                        .postalCode("123456")
                                        .city("City")
                                        .country("IT")
                                        .build())
                        .build();

        useCase.execute(input);

        verify(brandsRepository).add(isA(Brand.class));
        ArgumentCaptor<CreateBrandOutput> argumentCaptor =
                ArgumentCaptor.forClass(CreateBrandOutput.class);
        verify(outputPort).standard(argumentCaptor.capture());
        var output = argumentCaptor.getValue();
        assertThat(output.getSlug()).isEqualTo(Slug.of("ACME"));
    }

    @Test
    void it_should_return_an_error_if_the_brand_already_exists() {
        when(brandsRepository.exists(Slug.of("ACME"))).thenReturn(true);
        var input = CreateBrandInput.builder().name("ACME").build();

        useCase.execute(input);

        ArgumentCaptor<Slug> argumentCaptor = ArgumentCaptor.forClass(Slug.class);
        verify(outputPort).brandAlreadyExists(argumentCaptor.capture());
        var slug = argumentCaptor.getValue();
        assertThat(slug).isNotNull().isEqualTo(Slug.of("ACME"));
    }

    @Test
    void it_should_return_an_error_if_repository_throws_an_exception() {
        when(brandsRepository.add(isA(Brand.class))).thenThrow(RuntimeException.class);
        var input = CreateBrandInput.builder().name("ACME").build();

        useCase.execute(input);

        ArgumentCaptor<Exception> argumentCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(outputPort).error(argumentCaptor.capture());
    }
}
