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

import io.github.carlomicieli.catalog.railways.PeriodOfActivityInput;
import io.github.carlomicieli.catalog.railways.RailwayGaugeInput;
import io.github.carlomicieli.catalog.railways.TotalRailwayLengthInput;
import io.github.carlomicieli.usecases.boundaries.input.UseCaseInput;
import io.github.carlomicieli.util.Slug;
import io.micronaut.core.annotation.Introspected;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@Introspected
public class EditRailwayInput implements UseCaseInput {
    Slug railwaySlug;
    ModifiedValues modifiedValues;

    @Value
    @Builder
    public static class ModifiedValues {
        @Size(max = 25)
        String name;

        String companyName;
        String country;
        String description;
        PeriodOfActivityInput periodOfActivity;
        TotalRailwayLengthInput totalRailwayLength;
        RailwayGaugeInput railwayGauge;
        String websiteUrl;
        String headquarters;
    }
}
