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

import io.github.carlomicieli.catalog.brands.BrandKind;
import io.github.carlomicieli.catalog.brands.BrandsRepository;
import io.github.carlomicieli.mail.MailAddress;
import io.github.carlomicieli.usecases.AbstractUseCase;
import io.github.carlomicieli.util.EnumUtils;
import io.github.carlomicieli.util.UriUtils;
import java.util.Objects;
import javax.inject.Singleton;
import javax.validation.Validator;

@Singleton
public final class EditBrandUseCase
        extends AbstractUseCase<EditBrandInput, EditBrandOutput, EditBrandOutputPort> {
    private final BrandsRepository brandsRepository;

    public EditBrandUseCase(
            Validator validator,
            EditBrandOutputPort outputPort,
            BrandsRepository brandsRepository) {
        super(validator, outputPort);
        this.brandsRepository = Objects.requireNonNull(brandsRepository);
    }

    @Override
    protected void handle(EditBrandInput input) {
        var optBrand = brandsRepository.getBySlug(input.getBrandSlug());
        if (optBrand.isEmpty()) {
            outputPort.brandDoesNotExist(input.getBrandSlug());
            return;
        }

        var brand = optBrand.get();

        var modified = input.getModifiedValues();
        var kind = EnumUtils.parseEnum(BrandKind.class, modified.getKind());
        var mailAddress =
                MailAddress.tryParse(modified.getEmailAddress()).orElse(brand.getMailAddress());
        var websiteUrl =
                UriUtils.tryConvert(modified.getWebsiteUrl()).orElse(brand.getWebsiteUrl());
        var modifiedBrand =
                brand.withName(modified.getName())
                        .withBrandKind(kind)
                        .withWebsiteUrl(websiteUrl)
                        .withCompanyName(modified.getCompanyName())
                        .withGroupName(modified.getGroupName())
                        .withDescription(modified.getDescription())
                        .withMailAddress(mailAddress)
                        .withAddress(modified.getAddress());

        try {
            brandsRepository.update(modifiedBrand);
            outputPort.standard(EditBrandOutput.of(input.getBrandSlug()));
        } catch (Exception ex) {
            outputPort.error(ex);
        }
    }
}
