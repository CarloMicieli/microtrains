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
import io.github.carlomicieli.domain.AggregateRoot;
import io.github.carlomicieli.util.Slug;
import java.net.URI;
import java.time.Instant;
import lombok.*;

@Data
@Builder
@With
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class Railway implements AggregateRoot<RailwayId> {
    private final RailwayId id;
    private final Slug slug;
    private final String name;
    private final String companyName;
    private final Country country;
    private final String description;
    private final PeriodOfActivity periodOfActivity;
    private final RailwayGauge trackGauge;
    private final RailwayLength totalLength;
    private final URI websiteUrl;
    private final String headquarters;
    private final Instant createdDate;
    private final Instant modifiedDate;
    private final int version;

    public static Slug buildSlug(String name) {
        return Slug.of(name);
    }
}
