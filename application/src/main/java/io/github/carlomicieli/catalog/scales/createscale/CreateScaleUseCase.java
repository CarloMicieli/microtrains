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

import io.github.carlomicieli.catalog.scales.*;
import io.github.carlomicieli.catalog.valueobject.Gauge;
import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import io.github.carlomicieli.usecases.AbstractUseCase;
import io.github.carlomicieli.util.EnumUtils;
import io.github.carlomicieli.util.Try;
import io.micronaut.core.annotation.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import javax.validation.Validator;

@Singleton
public final class CreateScaleUseCase
        extends AbstractUseCase<CreateScaleInput, CreateScaleOutput, CreateScaleOutputPort> {
    private final ScaleFactory scaleFactory;
    private final ScalesRepository scalesRepository;

    protected CreateScaleUseCase(
            Validator validator,
            CreateScaleOutputPort outputPort,
            ScaleFactory scaleFactory,
            ScalesRepository scalesRepository) {
        super(validator, outputPort);

        this.scaleFactory = Objects.requireNonNull(scaleFactory);
        this.scalesRepository = Objects.requireNonNull(scalesRepository);
    }

    @Override
    protected void handle(@NonNull CreateScaleInput input) {
        var slug = Scale.buildSlug(input.getName());

        if (scalesRepository.exists(slug)) {
            outputPort.scaleAlreadyExists(slug);
            return;
        }

        var ratio = Ratio.of(input.getRatio());
        var scaleGauge = extractScaleGauge(input.getGauge());
        var standards = scaleStandards(input);

        var scale =
                scaleFactory.createNewScale(
                        input.getName(),
                        ratio,
                        scaleGauge,
                        input.getDescription(),
                        input.getWeight(),
                        standards);

        try {
            scalesRepository.add(scale);
            outputPort.standard(CreateScaleOutput.of(slug));
        } catch (Exception ex) {
            outputPort.error(ex);
        }
    }

    private List<ScaleStandard> scaleStandards(CreateScaleInput input) {
        if (input.getStandards() == null) {
            return null;
        }

        return input.getStandards().stream()
                .map(s -> EnumUtils.tryParseEnum(ScaleStandard.class, s))
                .filter(Try::isSuccess)
                .map(Try::get)
                .collect(Collectors.toList());
    }

    private ScaleGauge extractScaleGauge(ScaleGaugeInput input) {
        if (input == null) {
            return null;
        }

        var trackGauge =
                EnumUtils.tryParseEnum(TrackGauge.class, input.getTrackGauge())
                        .getOrElse(() -> TrackGauge.STANDARD);

        var inches =
                (input.getInches() == null)
                        ? null
                        : Gauge.ofInches(input.getInches().doubleValue());
        var millimeters =
                (input.getMillimeters() == null)
                        ? null
                        : Gauge.ofInches(input.getMillimeters().doubleValue());

        return ScaleGauge.builder()
                .trackGauge(trackGauge)
                .inches(inches)
                .millimetres(millimeters)
                .build();
    }
}
