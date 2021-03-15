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
package io.github.carlomicieli.catalog.railways.createrailway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import io.github.carlomicieli.catalog.railways.*;
import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import io.github.carlomicieli.countries.Country;
import io.github.carlomicieli.lengths.Length;
import io.github.carlomicieli.util.Slug;
import java.math.BigDecimal;
import java.net.URI;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
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

@DisplayName("Create railway use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CreateRailwayUseCaseTest {
    private final CreateRailwayUseCase useCase;
    private final CreateRailwayOutputPort outputPort;
    private final RailwaysRepository railwaysRepository;

    public CreateRailwayUseCaseTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        var railwaysFactory =
                new RailwayFactory(
                        Clock.fixed(Instant.now(), ZoneId.systemDefault()), RailwayId::randomId);

        this.railwaysRepository = mock(RailwaysRepository.class);
        this.outputPort = mock(CreateRailwayOutputPort.class);
        this.useCase =
                new CreateRailwayUseCase(
                        factory.getValidator(), outputPort, railwaysFactory, railwaysRepository);
    }

    @Test
    void it_should_create_a_new_railway() throws Exception {
        when(railwaysRepository.add(isA(Railway.class))).thenReturn(RailwayId.randomId());
        var input =
                CreateRailwayInput.builder()
                        .name("name")
                        .companyName("company name")
                        .websiteUrl("http://website")
                        .description("description")
                        .headquarters("headquarters")
                        .country("it")
                        .railwayGauge(
                                RailwayGaugeInput.builder()
                                        .inches(BigDecimal.valueOf(0.65))
                                        .millimeters(BigDecimal.valueOf(12.5))
                                        .trackGauge("standard")
                                        .build())
                        .periodOfActivity(
                                PeriodOfActivityInput.builder()
                                        .operatingSince(LocalDate.of(1900, 1, 1))
                                        .operatingUntil(LocalDate.of(2000, 1, 1))
                                        .status("inactive")
                                        .build())
                        .totalRailwayLength(
                                TotalRailwayLengthInput.builder()
                                        .kilometers(BigDecimal.valueOf(1000))
                                        .miles(BigDecimal.valueOf(621.4))
                                        .build())
                        .build();

        useCase.execute(input);

        ArgumentCaptor<Railway> railwayArgumentCaptor = ArgumentCaptor.forClass(Railway.class);
        verify(railwaysRepository).add(railwayArgumentCaptor.capture());

        var railway = railwayArgumentCaptor.getValue();
        assertThat(railway).isNotNull();
        assertThat(railway.getSlug()).isNotNull();
        assertThat(railway.getCompanyName()).isEqualTo("company name");
        assertThat(railway.getName()).isEqualTo("name");
        assertThat(railway.getCountry()).isEqualTo(Country.of("it"));
        assertThat(railway.getDescription()).isEqualTo("description");
        assertThat(railway.getHeadquarters()).isEqualTo("headquarters");
        assertThat(railway.getWebsiteUrl()).isEqualTo(new URI("http://website"));

        assertThat(railway.getPeriodOfActivity()).isNotNull();
        assertThat(railway.getPeriodOfActivity().getRailwayStatus())
                .isEqualTo(RailwayStatus.INACTIVE);
        assertThat(railway.getPeriodOfActivity().getOperatingSince())
                .isEqualTo(LocalDate.of(1900, 1, 1));
        assertThat(railway.getPeriodOfActivity().getOperatingUntil())
                .isEqualTo(LocalDate.of(2000, 1, 1));

        assertThat(railway.getTotalLength()).isNotNull();
        assertThat(railway.getTotalLength().getKilometers())
                .isEqualTo(Length.ofKilometers(BigDecimal.valueOf(1000)));
        assertThat(railway.getTotalLength().getMiles())
                .isBetween(
                        Length.ofMiles(BigDecimal.valueOf(621.3)),
                        Length.ofMiles(BigDecimal.valueOf(621.4)));

        assertThat(railway.getTrackGauge()).isNotNull();
        assertThat(railway.getTrackGauge().getTrackGauge()).isEqualTo(TrackGauge.STANDARD);
        assertThat(railway.getTrackGauge().getMillimeters())
                .isEqualTo(Length.ofMillimeters(BigDecimal.valueOf(12.5)));
        assertThat(railway.getTrackGauge().getInches())
                .isBetween(
                        Length.ofInches(BigDecimal.valueOf(0.4)),
                        Length.ofInches(BigDecimal.valueOf(0.6)));

        ArgumentCaptor<CreateRailwayOutput> argumentCaptor =
                ArgumentCaptor.forClass(CreateRailwayOutput.class);
        verify(outputPort).standard(argumentCaptor.capture());
        var output = argumentCaptor.getValue();
        assertThat(output.getSlug()).isEqualTo(Slug.of("name"));
    }

    @Test
    void it_should_return_an_error_if_the_railway_already_exists() {
        when(railwaysRepository.exists(Slug.of("FS"))).thenReturn(true);
        var input = CreateRailwayInput.builder().name("FS").build();

        useCase.execute(input);

        ArgumentCaptor<Slug> argumentCaptor = ArgumentCaptor.forClass(Slug.class);
        verify(outputPort).railwayAlreadyExists(argumentCaptor.capture());
        var slug = argumentCaptor.getValue();
        assertThat(slug).isNotNull().isEqualTo(Slug.of("FS"));
    }

    @Test
    void it_should_return_an_error_if_repository_throws_an_exception() {
        when(railwaysRepository.add(isA(Railway.class))).thenThrow(RuntimeException.class);
        var input = CreateRailwayInput.builder().name("FS").build();

        useCase.execute(input);

        ArgumentCaptor<Exception> argumentCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(outputPort).error(argumentCaptor.capture());
    }
}
