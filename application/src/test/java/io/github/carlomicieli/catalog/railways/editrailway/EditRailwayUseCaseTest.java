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
package io.github.carlomicieli.catalog.railways.editrailway;

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
import java.time.LocalDate;
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

@DisplayName("Edit railway use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class EditRailwayUseCaseTest {
    private final EditRailwayUseCase useCase;
    private final EditRailwayOutputPort outputPort;
    private final RailwaysRepository railwaysRepository;

    public EditRailwayUseCaseTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        this.railwaysRepository = mock(RailwaysRepository.class);
        this.outputPort = mock(EditRailwayOutputPort.class);
        this.useCase =
                new EditRailwayUseCase(factory.getValidator(), outputPort, railwaysRepository);
    }

    @Test
    void it_should_update_a_railway() throws Exception {
        var original =
                Railway.builder()
                        .name("name")
                        .slug(Slug.of("name"))
                        .companyName("company name")
                        .country(Country.of("it"))
                        .description("description")
                        .headquarters("headquarters")
                        .websiteUrl(new URI("http://website"))
                        .periodOfActivity(PeriodOfActivity.activeRailway(LocalDate.of(1900, 1, 1)))
                        .totalLength(RailwayLength.ofKilometers(1000))
                        .trackGauge(RailwayGauge.ofMillimeters(1435, TrackGauge.STANDARD))
                        .build();
        when(railwaysRepository.getBySlug(Slug.of("name"))).thenReturn(Optional.of(original));

        var periodOfActivityInput =
                PeriodOfActivityInput.builder()
                        .operatingSince(LocalDate.of(1999, 1, 1))
                        .operatingUntil(LocalDate.of(2000, 12, 31))
                        .status("inactive")
                        .build();
        var railwayGaugeInput =
                RailwayGaugeInput.builder()
                        .millimeters(BigDecimal.valueOf(1000))
                        .trackGauge("narrow")
                        .build();
        var totalLength = TotalRailwayLengthInput.builder().miles(BigDecimal.valueOf(1000)).build();
        var input =
                EditRailwayInput.builder()
                        .railwaySlug(Slug.of("name"))
                        .modifiedValues(
                                EditRailwayInput.ModifiedValues.builder()
                                        .name("new name")
                                        .companyName("new company name")
                                        .description("new description")
                                        .headquarters("new headquarters")
                                        .websiteUrl("http://new.website")
                                        .country("de")
                                        .periodOfActivity(periodOfActivityInput)
                                        .railwayGauge(railwayGaugeInput)
                                        .totalRailwayLength(totalLength)
                                        .build())
                        .build();

        useCase.execute(input);

        ArgumentCaptor<Railway> railwayArgumentCaptor = ArgumentCaptor.forClass(Railway.class);
        verify(railwaysRepository).update(railwayArgumentCaptor.capture());

        var railway = railwayArgumentCaptor.getValue();
        assertThat(railway).isNotNull();
        assertThat(railway.getSlug()).isNotNull();
        assertThat(railway.getCompanyName()).isEqualTo("new company name");
        assertThat(railway.getName()).isEqualTo("new name");
        assertThat(railway.getCountry()).isEqualTo(Country.of("de"));
        assertThat(railway.getDescription()).isEqualTo("new description");
        assertThat(railway.getHeadquarters()).isEqualTo("new headquarters");
        assertThat(railway.getWebsiteUrl()).isEqualTo(new URI("http://new.website"));

        assertThat(railway.getPeriodOfActivity()).isNotNull();
        assertThat(railway.getPeriodOfActivity().getRailwayStatus())
                .isEqualTo(RailwayStatus.INACTIVE);
        assertThat(railway.getPeriodOfActivity().getOperatingSince())
                .isEqualTo(LocalDate.of(1999, 1, 1));
        assertThat(railway.getPeriodOfActivity().getOperatingUntil())
                .isEqualTo(LocalDate.of(2000, 12, 31));

        assertThat(railway.getTotalLength()).isNotNull();
        assertThat(railway.getTotalLength().getKilometers())
                .isEqualTo(Length.ofMiles(BigDecimal.valueOf(1000)));

        assertThat(railway.getTrackGauge()).isNotNull();
        assertThat(railway.getTrackGauge().getTrackGauge()).isEqualTo(TrackGauge.NARROW);
        assertThat(railway.getTrackGauge().getMillimeters())
                .isEqualTo(Length.ofMillimeters(BigDecimal.valueOf(1000)));
        assertThat(railway.getTrackGauge().getInches())
                .isBetween(
                        Length.ofInches(BigDecimal.valueOf(39)),
                        Length.ofInches(BigDecimal.valueOf(40)));

        ArgumentCaptor<EditRailwayOutput> argumentCaptor =
                ArgumentCaptor.forClass(EditRailwayOutput.class);
        verify(outputPort).standard(argumentCaptor.capture());
        var output = argumentCaptor.getValue();
        assertThat(output.getSlug()).isEqualTo(Slug.of("name"));
    }

    @Test
    void it_should_return_an_error_if_the_railway_does_not_exist() {
        when(railwaysRepository.getBySlug(Slug.of("FS"))).thenReturn(Optional.empty());
        var input = EditRailwayInput.builder().railwaySlug(Slug.of("FS")).build();

        useCase.execute(input);

        ArgumentCaptor<Slug> argumentCaptor = ArgumentCaptor.forClass(Slug.class);
        verify(outputPort).railwayDoesNotExist(argumentCaptor.capture());
        var slug = argumentCaptor.getValue();
        assertThat(slug).isNotNull().isEqualTo(Slug.of("FS"));
    }

    @Test
    void it_should_return_an_error_if_repository_throws_an_exception() {
        when(railwaysRepository.getBySlug(Slug.of("FS")))
                .thenReturn(Optional.of(Railway.builder().build()));
        doThrow(RuntimeException.class).when(railwaysRepository).update(isA(Railway.class));
        var input = EditRailwayInput.builder().railwaySlug(Slug.of("FS")).build();

        useCase.execute(input);

        ArgumentCaptor<Exception> argumentCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(outputPort).error(argumentCaptor.capture());
    }
}
