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
package io.github.carlomicieli.catalog.scales;

import io.github.carlomicieli.domain.AggregateRootFactory;
import io.github.carlomicieli.util.Slug;
import java.time.Clock;
import java.util.List;
import java.util.function.Supplier;

public class ScaleFactory extends AggregateRootFactory<Scale, ScaleId> {
    public ScaleFactory(Clock clock, Supplier<ScaleId> identifierSource) {
        super(clock, identifierSource);
    }

    /**
     * Creates a new {@code Scale}, this method is not making any validation. The caller needs to
     * ensure only a valid object is created.
     */
    public Scale createNewScale(
            String name,
            Ratio ratio,
            ScaleGauge gauge,
            String description,
            Integer weight,
            List<ScaleStandard> standards) {
        var newId = generateNewId();
        var createdDate = getCurrentInstant();

        Slug scaleSlug = Scale.buildSlug(name);

        return Scale.builder()
                .id(newId)
                .name(name)
                .slug(scaleSlug)
                .ratio(ratio)
                .gauge(gauge)
                .description(description)
                .weight(weight)
                .standards(standards)
                .version(1)
                .createdDate(createdDate)
                .build();
    }
}
