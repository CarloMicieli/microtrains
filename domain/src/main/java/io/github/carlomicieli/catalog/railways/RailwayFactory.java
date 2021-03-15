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

import io.github.carlomicieli.countries.Country;
import io.github.carlomicieli.domain.AggregateRootFactory;
import io.github.carlomicieli.util.Slug;
import java.net.URI;
import java.time.Clock;
import java.util.function.Supplier;

public final class RailwayFactory extends AggregateRootFactory<Railway, RailwayId> {
    public RailwayFactory(Clock clock, Supplier<RailwayId> identifierSource) {
        super(clock, identifierSource);
    }

    /**
     * Creates a new {@code Railway}, this method is not making any validation. The caller needs to
     * ensure only a valid object is created.
     */
    public Railway createNewRailway(
            String name,
            String description,
            String companyName,
            Country country,
            PeriodOfActivity periodOfActivity,
            RailwayGauge trackGauge,
            RailwayLength totalLength,
            URI websiteUrl,
            String headquarters) {
        var newId = generateNewId();
        var createdDate = getCurrentInstant();

        Slug railwaySlug = Railway.buildSlug(name);

        return Railway.builder()
                .id(newId)
                .slug(railwaySlug)
                .name(name)
                .description(description)
                .companyName(companyName)
                .country(country)
                .periodOfActivity(periodOfActivity)
                .trackGauge(trackGauge)
                .totalLength(totalLength)
                .websiteUrl(websiteUrl)
                .headquarters(headquarters)
                .createdDate(createdDate)
                .version(1)
                .build();
    }
}
