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
public final class EditRailwayUseCase
        extends AbstractUseCase<EditRailwayInput, EditRailwayOutput, EditRailwayOutputPort> {
    private final RailwaysRepository railwaysRepository;

    protected EditRailwayUseCase(
            Validator validator,
            EditRailwayOutputPort outputPort,
            RailwaysRepository railwaysRepository) {
        super(validator, outputPort);
        this.railwaysRepository = Objects.requireNonNull(railwaysRepository);
    }

    @Override
    protected void handle(@NonNull EditRailwayInput input) {
        var optRailway = railwaysRepository.getBySlug(input.getRailwaySlug());
        if (optRailway.isEmpty()) {
            outputPort.railwayDoesNotExist(input.getRailwaySlug());
            return;
        }

        var railway = optRailway.get();
        var modifiedValues = input.getModifiedValues();

        var websiteUrl =
                UriUtils.tryConvert(modifiedValues.getWebsiteUrl()).orElse(railway.getWebsiteUrl());
        var totalLength = extractRailwayLength(modifiedValues.getTotalRailwayLength());
        var railwayGauge = extractRailwayGauge(modifiedValues.getRailwayGauge());
        var periodOfActivity = extractPeriodOfActivity(modifiedValues.getPeriodOfActivity());

        var modified =
                railway.withCompanyName(modifiedValues.getCompanyName())
                        .withCountry(Country.of(modifiedValues.getCountry()))
                        .withDescription(modifiedValues.getDescription())
                        .withHeadquarters(modifiedValues.getHeadquarters())
                        .withName(modifiedValues.getName())
                        .withWebsiteUrl(websiteUrl)
                        .withTotalLength(totalLength)
                        .withTrackGauge(railwayGauge)
                        .withPeriodOfActivity(periodOfActivity);

        try {
            railwaysRepository.update(modified);
            outputPort.standard(EditRailwayOutput.of(modified.getSlug()));
        } catch (Exception ex) {
            outputPort.error(ex);
        }
    }

    private RailwayLength extractRailwayLength(TotalRailwayLengthInput input) {
        if (input == null) {
            return null;
        }

        if (input.getKilometers() != null) {
            return RailwayLength.ofKilometers(input.getKilometers().doubleValue());
        } else {
            return RailwayLength.ofMiles(input.getMiles().doubleValue());
        }
    }

    private RailwayGauge extractRailwayGauge(RailwayGaugeInput input) {
        if (input == null) {
            return null;
        }

        var trackGauge =
                EnumUtils.tryParseEnum(TrackGauge.class, input.getTrackGauge())
                        .getOrElse(() -> TrackGauge.STANDARD);
        if (input.getMillimeters() != null) {
            return RailwayGauge.ofMillimeters(input.getMillimeters().doubleValue(), trackGauge);
        } else {
            return RailwayGauge.ofInches(input.getInches().doubleValue(), trackGauge);
        }
    }

    private PeriodOfActivity extractPeriodOfActivity(PeriodOfActivityInput input) {
        if (input == null) {
            return null;
        }

        var railwayStatus =
                EnumUtils.tryParseEnum(RailwayStatus.class, input.getStatus())
                        .getOrElse(() -> RailwayStatus.ACTIVE);
        if (railwayStatus == RailwayStatus.ACTIVE) {
            return PeriodOfActivity.activeRailway(input.getOperatingSince());
        } else {
            return PeriodOfActivity.inactiveRailway(
                    input.getOperatingSince(), input.getOperatingUntil());
        }
    }
}
