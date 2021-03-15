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
package io.github.carlomicieli.catalog.brands;

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.addresses.Address;
import io.github.carlomicieli.mail.MailAddress;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A Brand factory")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BrandFactoryTest {

    private static final Address FIXED_ADDRESS =
            Address.builder().line1("22 Acacia Avenue").city("London").country("GB").build();
    private static final Instant FIXED_INSTANT = Instant.parse("1988-11-25T10:15:30.00Z");
    private static final BrandId FIXED_BRAND_ID =
            BrandId.of(UUID.fromString("e9f5ed5a-edb6-46fa-a55a-bc8632e89d3a"));
    private static final Clock FIXED_CLOCK = Clock.fixed(FIXED_INSTANT, ZoneId.systemDefault());

    private final BrandFactory factory;

    public BrandFactoryTest() {
        this.factory = new BrandFactory(FIXED_CLOCK, () -> FIXED_BRAND_ID);
    }

    @Test
    @SneakyThrows
    void it_should_create_new_brands() {
        var brand = createNewBrandWithName("ACME");

        assertThat(brand).isNotNull();
        assertThat(brand.getName()).isEqualTo("ACME");
        assertThat(brand.getCompanyName())
                .isEqualTo("Associazione Costruzioni Modellistiche Esatte");
        assertThat(brand.getBrandKind()).isEqualTo(BrandKind.INDUSTRIAL);
        assertThat(brand.getGroupName()).isEqualTo("Group name goes here");
        assertThat(brand.getAddress()).isEqualTo(FIXED_ADDRESS);
        assertThat(brand.getMailAddress()).isEqualTo(MailAddress.of("webmaster@acmetreni.com"));
    }

    @Test
    @SneakyThrows
    void it_should_set_the_brand_id() {
        var brand = createNewBrandWithName("ACME");
        assertThat(brand).isNotNull();
        assertThat(brand.getId()).isEqualTo(FIXED_BRAND_ID);
    }

    @Test
    @SneakyThrows
    void it_should_set_the_initial_version_and_created_date() {
        var brand = createNewBrandWithName("ACME");
        assertThat(brand).isNotNull();
        assertThat(brand.getVersion()).isEqualTo(1);
        assertThat(brand.getCreatedDate()).isEqualTo(FIXED_INSTANT);
        assertThat(brand.getModifiedDate()).isNull();
    }

    @SneakyThrows
    private Brand createNewBrandWithName(String name) {
        return factory.createNewBrand(
                name,
                "Associazione Costruzioni Modellistiche Esatte",
                new URI("https://www.acmetreni.com"),
                "Group name goes here",
                "ACME description goes here",
                FIXED_ADDRESS,
                BrandKind.INDUSTRIAL,
                MailAddress.of("webmaster@acmetreni.com"));
    }
}
