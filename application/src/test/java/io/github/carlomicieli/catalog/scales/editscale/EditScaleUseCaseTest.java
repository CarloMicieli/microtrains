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

import static org.mockito.Mockito.mock;

import io.github.carlomicieli.catalog.scales.ScalesRepository;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("Edit scale use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class EditScaleUseCaseTest {
    private final EditScaleUseCase useCase;
    private final EditScaleOutputPort outputPort;
    private final ScalesRepository scalesRepository;

    public EditScaleUseCaseTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        this.scalesRepository = mock(ScalesRepository.class);
        this.outputPort = mock(EditScaleOutputPort.class);
        this.useCase = new EditScaleUseCase(factory.getValidator(), outputPort, scalesRepository);
    }
}
