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
package io.github.carlomicieli.catalog.scales.createscale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

import io.github.carlomicieli.catalog.scales.*;
import io.github.carlomicieli.catalog.valueobject.Gauge;
import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import io.github.carlomicieli.util.Slug;
import io.github.carlomicieli.validation.ValidationError;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Create scale use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class CreateScaleUseCaseTest {
    private final CreateScaleUseCase useCase;
    private final CreateScaleOutputPort outputPort;
    private final ScalesRepository scalesRepository;

    public CreateScaleUseCaseTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        var scaleFactory =
                new ScaleFactory(
                        Clock.fixed(Instant.now(), ZoneId.systemDefault()), ScaleId::randomId);

        this.scalesRepository = mock(ScalesRepository.class);
        this.outputPort = mock(CreateScaleOutputPort.class);
        this.useCase =
                new CreateScaleUseCase(
                        factory.getValidator(), outputPort, scaleFactory, scalesRepository);
    }

    @Test
    void it_should_validate_the_input() {
        var input =
                CreateScaleInput.builder()
                        .name("")
                        .description("description")
                        .ratio(BigDecimal.valueOf(-1))
                        .standards(List.of("NMRA"))
                        .weight(100)
                        .gauge(
                                ScaleGaugeInput.builder()
                                        .millimeters(BigDecimal.valueOf(-1))
                                        .inches(BigDecimal.valueOf(-1))
                                        .trackGauge("not valid")
                                        .build())
                        .build();

        useCase.execute(input);

        @SuppressWarnings("unchecked")
        final ArgumentCaptor<List<ValidationError>> argumentCaptor =
                ArgumentCaptor.forClass(List.class);
        verify(outputPort).invalidRequest(argumentCaptor.capture());

        var errors = argumentCaptor.getValue();
        assertThat(errors).hasSize(4);
        assertThat(errors).contains(ValidationError.of("name", "must not be blank", ""));
        assertThat(errors)
                .contains(
                        ValidationError.of(
                                "ratio",
                                "must be greater than or equal to 1.0",
                                BigDecimal.valueOf(-1)));
        assertThat(errors)
                .contains(
                        ValidationError.of(
                                "gauge.inches",
                                "must be greater than 0.0",
                                BigDecimal.valueOf(-1)));
        assertThat(errors)
                .contains(
                        ValidationError.of(
                                "gauge.millimeters",
                                "must be greater than 0.0",
                                BigDecimal.valueOf(-1)));
    }

    @Test
    void it_should_create_a_new_scale() {
        var input =
                CreateScaleInput.builder()
                        .name("name")
                        .description("description")
                        .ratio(BigDecimal.valueOf(87))
                        .standards(List.of("NMRA"))
                        .weight(100)
                        .gauge(
                                ScaleGaugeInput.builder()
                                        .millimeters(BigDecimal.valueOf(16.5))
                                        .inches(BigDecimal.valueOf(0.65))
                                        .trackGauge("standard")
                                        .build())
                        .build();

        useCase.execute(input);

        ArgumentCaptor<Scale> scaleArgumentCaptor = ArgumentCaptor.forClass(Scale.class);
        verify(scalesRepository).add(scaleArgumentCaptor.capture());
        var newScale = scaleArgumentCaptor.getValue();
        assertThat(newScale).isNotNull();
        assertThat(newScale.getName()).isEqualTo("name");
        assertThat(newScale.getDescription()).isEqualTo("description");
        assertThat(newScale.getRatio()).isEqualTo(Ratio.of(87));
        assertThat(newScale.getWeight()).isEqualTo(100);
        assertThat(newScale.getStandards()).isEqualTo(List.of(ScaleStandard.NMRA));

        assertThat(newScale.getGauge()).isNotNull();
        assertThat(newScale.getGauge().getInches()).isEqualTo(Gauge.ofInches(0.65));
        assertThat(newScale.getGauge().getMillimetres()).isEqualTo(Gauge.ofMillimeters(16.5));
        assertThat(newScale.getGauge().getTrackGauge()).isEqualTo(TrackGauge.STANDARD);

        ArgumentCaptor<CreateScaleOutput> outputArgumentCaptor =
                ArgumentCaptor.forClass(CreateScaleOutput.class);
        verify(outputPort).standard(outputArgumentCaptor.capture());
        var createScaleOutput = outputArgumentCaptor.getValue();
        assertThat(createScaleOutput).isNotNull().isEqualTo(CreateScaleOutput.of(Slug.of("name")));
    }

    @Test
    void it_should_return_an_error_if_the_scale_already_exists() {
        when(scalesRepository.exists(Slug.of("H0"))).thenReturn(true);
        var input = CreateScaleInput.builder().name("H0").build();

        useCase.execute(input);

        ArgumentCaptor<Slug> argumentCaptor = ArgumentCaptor.forClass(Slug.class);
        verify(outputPort).scaleAlreadyExists(argumentCaptor.capture());
        var slug = argumentCaptor.getValue();
        assertThat(slug).isNotNull().isEqualTo(Slug.of("H0"));
    }

    @Test
    void it_should_return_an_error_if_repository_throws_an_exception() {
        when(scalesRepository.add(isA(Scale.class))).thenThrow(RuntimeException.class);
        var input = CreateScaleInput.builder().name("H0").build();

        useCase.execute(input);

        ArgumentCaptor<Exception> argumentCaptor = ArgumentCaptor.forClass(Exception.class);
        verify(outputPort).error(argumentCaptor.capture());
    }
}
