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
package io.github.carlomicieli.usecases;

import io.github.carlomicieli.usecases.boundaries.input.UseCaseBeanValidator;
import io.github.carlomicieli.usecases.boundaries.input.UseCaseInput;
import io.github.carlomicieli.usecases.boundaries.input.UseCaseInputValidator;
import io.github.carlomicieli.usecases.boundaries.output.UseCaseOutput;
import io.github.carlomicieli.usecases.boundaries.output.port.StandardOutputPort;
import io.github.carlomicieli.validation.ValidationError;
import io.micronaut.core.annotation.NonNull;
import java.util.List;
import java.util.Objects;
import javax.validation.Validator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractUseCase<
        InType extends UseCaseInput,
        OutType extends UseCaseOutput,
        OutPortType extends StandardOutputPort<OutType>>
    implements UseCase<InType> {

  protected UseCaseInputValidator<InType> inputValidator;
  protected OutPortType outputPort;

  protected AbstractUseCase(@NonNull Validator validator, @NonNull OutPortType outputPort) {
    Objects.requireNonNull(validator);
    this.outputPort = Objects.requireNonNull(outputPort);
    this.inputValidator = new UseCaseBeanValidator<>(validator);
  }

  @Override
  public final void execute(@NonNull InType input) {
    Objects.requireNonNull(input);

    List<ValidationError> validationErrors = inputValidator.validateInput(input);
    if (!validationErrors.isEmpty()) {
      outputPort.invalidRequest(validationErrors);
      return;
    }

    try {
      handle(input);
    } catch (Exception ex) {
      // log.error("execute", ex);
      outputPort.error(ex);
    }
  }

  /**
   * The {@code UseCase} handler method. This method must not throw exceptions, but return the error
   * message using the appropriate output port.
   */
  protected abstract void handle(@NonNull InType input);
}
