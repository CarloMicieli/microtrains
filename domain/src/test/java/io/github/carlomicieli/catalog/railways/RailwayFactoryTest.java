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
package io.github.carlomicieli.catalog.railways;

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import io.github.carlomicieli.countries.Country;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A Railway factory")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RailwayFactoryTest {
    private static final Instant FIXED_INSTANT = Instant.parse("1988-11-25T10:15:30.00Z");
    private static final RailwayId FIXED_RAILWAY_ID =
            RailwayId.of(UUID.fromString("e9f5ed5a-edb6-46fa-a55a-bc8632e89d3a"));
    private static final Clock FIXED_CLOCK = Clock.fixed(FIXED_INSTANT, ZoneId.systemDefault());

    private final RailwayFactory factory;

    public RailwayFactoryTest() {
        this.factory = new RailwayFactory(FIXED_CLOCK, () -> FIXED_RAILWAY_ID);
    }

    @SneakyThrows
    @Test
    void should_create_new_railways() {
        var italy = Country.of("IT");
        var periodOfActivity = PeriodOfActivity.activeRailway(LocalDate.of(1905, 7, 1));
        var gauge = RailwayGauge.ofMillimeters(1435, TrackGauge.STANDARD);
        var totalLength = RailwayLength.ofKilometers(123456);
        var website = new URI("http://www.trenitalia.com");

        var railway =
                factory.createNewRailway(
                        "FS",
                        "Ferrovie italiane",
                        "Ferrovie dello stato",
                        italy,
                        periodOfActivity,
                        gauge,
                        totalLength,
                        website,
                        "Rome");

        assertThat(railway).isNotNull();
        assertThat(railway.getId()).isEqualTo(FIXED_RAILWAY_ID);
        assertThat(railway.getCountry()).isEqualTo(italy);
        assertThat(railway.getName()).isEqualTo("FS");
        assertThat(railway.getDescription()).isEqualTo("Ferrovie italiane");
        assertThat(railway.getCompanyName()).isEqualTo("Ferrovie dello stato");
        assertThat(railway.getPeriodOfActivity()).isEqualTo(periodOfActivity);
        assertThat(railway.getTrackGauge()).isEqualTo(gauge);
        assertThat(railway.getTotalLength()).isEqualTo(totalLength);
        assertThat(railway.getWebsiteUrl()).isEqualTo(website);
        assertThat(railway.getHeadquarters()).isEqualTo("Rome");
        assertThat(railway.getCreatedDate()).isNotNull();
        assertThat(railway.getModifiedDate()).isNull();
        assertThat(railway.getVersion()).isEqualTo(1);
    }
}
