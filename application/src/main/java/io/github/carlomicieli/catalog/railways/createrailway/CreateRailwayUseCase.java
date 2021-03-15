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

import io.github.carlomicieli.catalog.railways.*;
import io.github.carlomicieli.catalog.valueobject.TrackGauge;
import io.github.carlomicieli.countries.Country;
import io.github.carlomicieli.usecases.AbstractUseCase;
import io.github.carlomicieli.util.EnumUtils;
import io.github.carlomicieli.util.UriUtils;
import io.micronaut.core.annotation.NonNull;
import java.util.Objects;
import javax.inject.Singleton;
import javax.validation.Validator;

@Singleton
public final class CreateRailwayUseCase
        extends AbstractUseCase<CreateRailwayInput, CreateRailwayOutput, CreateRailwayOutputPort> {
    private final RailwayFactory railwayFactory;
    private final RailwaysRepository railwaysRepository;

    protected CreateRailwayUseCase(
            Validator validator,
            CreateRailwayOutputPort outputPort,
            RailwayFactory railwayFactory,
            RailwaysRepository railwaysRepository) {
        super(validator, outputPort);

        this.railwayFactory = Objects.requireNonNull(railwayFactory);
        this.railwaysRepository = Objects.requireNonNull(railwaysRepository);
    }

    @Override
    protected void handle(@NonNull CreateRailwayInput input) {
        var railwaySlug = Railway.buildSlug(input.getName());
        if (railwaysRepository.exists(railwaySlug)) {
            outputPort.railwayAlreadyExists(railwaySlug);
            return;
        }

        var country = Country.of(input.getCountry());
        var websiteUrl = UriUtils.tryConvert(input.getWebsiteUrl()).orElse(null);

        PeriodOfActivity periodOfActivity = extractPeriodOfActivity(input);
        RailwayLength railwayLength = extractRailwayLength(input);
        RailwayGauge railwayGauge = extractRailwayGauge(input);

        var railway =
                railwayFactory.createNewRailway(
                        input.getName(),
                        input.getDescription(),
                        input.getCompanyName(),
                        country,
                        periodOfActivity,
                        railwayGauge,
                        railwayLength,
                        websiteUrl,
                        input.getHeadquarters());

        try {
            railwaysRepository.add(railway);
            outputPort.standard(CreateRailwayOutput.of(railway.getSlug()));
        } catch (Exception ex) {
            outputPort.error(ex);
        }
    }

    private RailwayGauge extractRailwayGauge(CreateRailwayInput input) {
        if (input.getRailwayGauge() == null) {
            return null;
        }

        var trackGauge =
                EnumUtils.tryParseEnum(TrackGauge.class, input.getRailwayGauge().getTrackGauge())
                        .getOrElse(() -> TrackGauge.STANDARD);
        if (input.getRailwayGauge().getMillimeters() != null) {
            return RailwayGauge.ofMillimeters(
                    input.getRailwayGauge().getMillimeters().doubleValue(), trackGauge);
        }

        return RailwayGauge.ofInches(input.getRailwayGauge().getInches().doubleValue(), trackGauge);
    }

    private RailwayLength extractRailwayLength(CreateRailwayInput input) {
        if (input.getTotalRailwayLength() == null) {
            return null;
        }

        if (input.getTotalRailwayLength().getKilometers() != null) {
            return RailwayLength.ofKilometers(
                    input.getTotalRailwayLength().getKilometers().doubleValue());
        }

        return RailwayLength.ofMiles(input.getTotalRailwayLength().getMiles().doubleValue());
    }

    private PeriodOfActivity extractPeriodOfActivity(CreateRailwayInput input) {
        if (input.getPeriodOfActivity() == null) {
            return null;
        }

        var poa = input.getPeriodOfActivity();
        var status = EnumUtils.parseEnum(RailwayStatus.class, poa.getStatus());
        if (status == RailwayStatus.ACTIVE) {
            return PeriodOfActivity.activeRailway(poa.getOperatingSince());
        } else {
            return PeriodOfActivity.inactiveRailway(
                    poa.getOperatingSince(), poa.getOperatingUntil());
        }
    }
}
