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

import io.github.carlomicieli.catalog.brands.BrandFactory;
import io.github.carlomicieli.catalog.brands.BrandKind;
import io.github.carlomicieli.catalog.brands.BrandsRepository;
import io.github.carlomicieli.mail.MailAddress;
import io.github.carlomicieli.usecases.AbstractUseCase;
import io.github.carlomicieli.util.EnumUtils;
import io.github.carlomicieli.util.Slug;
import io.github.carlomicieli.util.UriUtils;
import io.micronaut.core.annotation.NonNull;
import java.util.Objects;
import javax.inject.Singleton;
import javax.validation.Validator;

@Singleton
public class CreateBrandUseCase
        extends AbstractUseCase<CreateBrandInput, CreateBrandOutput, CreateBrandOutputPort> {
    private final BrandFactory brandFactory;
    private final BrandsRepository brandsRepository;

    public CreateBrandUseCase(
            Validator validator,
            CreateBrandOutputPort outputPort,
            BrandFactory brandFactory,
            BrandsRepository brandsRepository) {
        super(validator, outputPort);

        this.brandFactory = Objects.requireNonNull(brandFactory);
        this.brandsRepository = Objects.requireNonNull(brandsRepository);
    }

    @Override
    protected void handle(@NonNull CreateBrandInput input) {
        var slug = Slug.of(input.getName());

        if (brandsRepository.exists(slug)) {
            outputPort.brandAlreadyExists(slug);
            return;
        }

        var brandKind = EnumUtils.parseEnum(BrandKind.class, input.getKind());
        var websiteUrl = UriUtils.tryConvert(input.getWebsiteUrl()).orElse(null);
        var mailAddress = MailAddress.tryParse(input.getEmailAddress()).orElse(null);

        var newBrand =
                brandFactory.createNewBrand(
                        input.getName(),
                        input.getCompanyName(),
                        websiteUrl,
                        input.getGroupName(),
                        input.getDescription(),
                        input.getAddress(),
                        brandKind,
                        mailAddress);

        try {
            brandsRepository.add(newBrand);
            outputPort.standard(CreateBrandOutput.of(slug));
        } catch (Exception ex) {
            outputPort.error(ex);
        }
    }
}
