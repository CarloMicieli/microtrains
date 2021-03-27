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
package io.github.carlomicieli.catalog.scales.editscale;

import io.github.carlomicieli.catalog.scales.ScalesRepository;
import io.github.carlomicieli.usecases.AbstractUseCase;
import io.micronaut.core.annotation.NonNull;
import java.util.Objects;
import javax.inject.Singleton;
import javax.validation.Validator;

@Singleton
public final class EditScaleUseCase
        extends AbstractUseCase<EditScaleInput, EditScaleOutput, EditScaleOutputPort> {
    private final ScalesRepository scalesRepository;

    protected EditScaleUseCase(
            Validator validator,
            EditScaleOutputPort outputPort,
            ScalesRepository scalesRepository) {
        super(validator, outputPort);

        this.scalesRepository = Objects.requireNonNull(scalesRepository);
    }

    @Override
    protected void handle(@NonNull EditScaleInput input) {}
}
