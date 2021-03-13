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

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import io.github.carlomicieli.usecases.boundaries.input.UseCaseBeanValidator;
import io.github.carlomicieli.usecases.boundaries.input.UseCaseInput;
import io.github.carlomicieli.usecases.boundaries.input.UseCaseInputValidator;
import io.github.carlomicieli.usecases.boundaries.output.UseCaseOutput;
import io.github.carlomicieli.usecases.boundaries.output.port.StandardOutputPort;
import io.github.carlomicieli.validation.ValidationError;
import io.micronaut.core.annotation.NonNull;
import java.util.List;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("A use case")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class UseCaseTest {
  private final MyTestUseCase useCase;
  private final MyUseCaseOutputPort outputPort;

  public UseCaseTest(@Mock MyUseCaseOutputPort outputPort) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    var useCaseValidator = new UseCaseBeanValidator<MyUseCaseInput>(factory.getValidator());
    this.useCase = new MyTestUseCase(useCaseValidator, outputPort);
    this.outputPort = outputPort;
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  void it_should_output_an_error_when_input_is_null() {
    var thrown = assertThatThrownBy(() -> useCase.execute(null));
    assertThat(thrown).isNotNull();
  }

  @Test
  void it_should_validate_input_before_execution() {
    var expectedErrors = List.of(ValidationError.of("question", "must not be blank", ""));
    var input = MyUseCaseInput.of("");
    useCase.execute(input);
    verify(outputPort, times(1)).invalidRequest(expectedErrors);
  }

  @Test
  void it_should_produce_the_standard_output_on_the_happy_path() {
    var expectedOutput = MyUseCaseOutput.of(42);

    var input = MyUseCaseInput.of("What's up?");
    useCase.execute(input);

    verify(outputPort, times(1)).standard(expectedOutput);
  }

  @AllArgsConstructor(staticName = "of")
  @Value
  static class MyUseCaseInput implements UseCaseInput {
    @NotBlank String question;
  }

  @Value
  @AllArgsConstructor(staticName = "of")
  static class MyUseCaseOutput implements UseCaseOutput {
    int answer;
  }

  interface MyUseCaseOutputPort extends StandardOutputPort<MyUseCaseOutput> {}

  static class MyTestUseCase
      extends AbstractUseCase<MyUseCaseInput, MyUseCaseOutput, MyUseCaseOutputPort> {

    public MyTestUseCase(
        UseCaseInputValidator<MyUseCaseInput> inputValidator, MyUseCaseOutputPort outputPort) {
      super(inputValidator, outputPort);
    }

    @Override
    protected void handle(@NonNull MyUseCaseInput input) {
      outputPort.standard(MyUseCaseOutput.of(42));
    }
  }
}
