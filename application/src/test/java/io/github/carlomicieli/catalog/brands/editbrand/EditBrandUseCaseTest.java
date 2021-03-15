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
package io.github.carlomicieli.catalog.brands.editbrand;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import io.github.carlomicieli.addresses.Address;
import io.github.carlomicieli.catalog.brands.Brand;
import io.github.carlomicieli.catalog.brands.BrandKind;
import io.github.carlomicieli.catalog.brands.BrandsRepository;
import io.github.carlomicieli.mail.MailAddress;
import io.github.carlomicieli.util.Slug;
import java.net.URI;
import java.util.Optional;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Edit brand use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class EditBrandUseCaseTest {
    private final EditBrandUseCase useCase;
    private final EditBrandOutputPort outputPort;
    private final BrandsRepository brandsRepository;

    public EditBrandUseCaseTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        this.brandsRepository = mock(BrandsRepository.class);
        this.outputPort = mock(EditBrandOutputPort.class);
        this.useCase = new EditBrandUseCase(factory.getValidator(), outputPort, brandsRepository);
    }

    @Test
    void it_should_update_a_brand() throws Exception {
        var slugOfName = Slug.of("name");
        var originalValue =
                Brand.builder()
                        .name("name")
                        .companyName("company name")
                        .groupName("group name")
                        .websiteUrl(new URI("http://website"))
                        .mailAddress(MailAddress.of("mail@mail.com"))
                        .description("description")
                        .brandKind(BrandKind.INDUSTRIAL)
                        .address(
                                Address.builder()
                                        .line1("line1")
                                        .line2("line2")
                                        .region("region")
                                        .postalCode("postalCode")
                                        .city("city")
                                        .country("IT")
                                        .build())
                        .build();

        when(brandsRepository.getBySlug(slugOfName)).thenReturn(Optional.of(originalValue));

        var newAddress =
                Address.builder()
                        .line1("new line1")
                        .line2("new line2")
                        .region("new region")
                        .postalCode("new postalCode")
                        .city("new city")
                        .country("DE")
                        .build();
        var modifiedValues =
                EditBrandInput.ModifiedValues.builder()
                        .name("new name")
                        .companyName("new company name")
                        .groupName("new group name")
                        .websiteUrl("http://new.website")
                        .emailAddress("new.mail@mail.com")
                        .description("new description")
                        .kind("brass-models")
                        .address(newAddress)
                        .build();
        var input =
                EditBrandInput.builder()
                        .brandSlug(slugOfName)
                        .modifiedValues(modifiedValues)
                        .build();

        useCase.execute(input);

        ArgumentCaptor<Brand> brandArgumentCaptor = ArgumentCaptor.forClass(Brand.class);
        verify(brandsRepository).update(brandArgumentCaptor.capture());

        var inBrand = brandArgumentCaptor.getValue();
        assertThat(inBrand).isNotNull();
        assertThat(inBrand.getName()).isEqualTo("new name");
        assertThat(inBrand.getBrandKind()).isEqualTo(BrandKind.BRASS_MODELS);
        assertThat(inBrand.getCompanyName()).isEqualTo("new company name");
        assertThat(inBrand.getDescription()).isEqualTo("new description");
        assertThat(inBrand.getAddress()).isEqualTo(newAddress);
        assertThat(inBrand.getMailAddress()).isEqualTo(MailAddress.of("new.mail@mail.com"));
        assertThat(inBrand.getGroupName()).isEqualTo("new group name");
        assertThat(inBrand.getWebsiteUrl()).isEqualTo(new URI("http://new.website"));

        ArgumentCaptor<EditBrandOutput> argumentCaptor =
                ArgumentCaptor.forClass(EditBrandOutput.class);
        verify(outputPort).standard(argumentCaptor.capture());
        var output = argumentCaptor.getValue();
        assertThat(output.getSlug()).isEqualTo(slugOfName);
    }

    @Test
    void it_should_return_an_error_if_the_brand_does_not_exist() {
        when(brandsRepository.getBySlug(Slug.of("ACME"))).thenReturn(Optional.empty());
        var input = EditBrandInput.builder().brandSlug(Slug.of("ACME")).build();

        useCase.execute(input);

        ArgumentCaptor<Slug> argumentCaptor = ArgumentCaptor.forClass(Slug.class);
        verify(outputPort).brandDoesNotExist(argumentCaptor.capture());
        var slug = argumentCaptor.getValue();
        assertThat(slug).isNotNull().isEqualTo(Slug.of("ACME"));
    }

    @Test
    void it_should_return_an_error_if_repository_throws_an_exception() {
        when(brandsRepository.getBySlug(Slug.of("ACME")))
                .thenReturn(Optional.of(Brand.builder().build()));
        doThrow(RuntimeException.class).when(brandsRepository).update(isA(Brand.class));
        var input = EditBrandInput.builder().brandSlug(Slug.of("ACME")).build();

        useCase.execute(input);

        ArgumentCaptor<Exception> argumentCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(outputPort).error(argumentCaptor.capture());
    }
}
