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
package io.github.carlomicieli.usecases.boundaries.output.port;

import io.github.carlomicieli.usecases.boundaries.output.UseCaseOutput;
import io.github.carlomicieli.validation.ValidationError;
import java.util.List;

/** The {@code output port} when use case handling results in an error. */
public interface ErrorOutputPort<OutType extends UseCaseOutput> extends OutputPort {
    /** Output port for generic errors */
    void error(String errorMessage);

    /** Output port for unhandled exceptions */
    void error(Throwable ex);

    /** Output port for requests that failed the validation */
    void invalidRequest(List<ValidationError> validationErrors);
}
