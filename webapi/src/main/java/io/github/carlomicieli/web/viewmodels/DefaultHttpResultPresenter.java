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
package io.github.carlomicieli.web.viewmodels;

import io.github.carlomicieli.usecases.boundaries.output.UseCaseOutput;
import io.github.carlomicieli.usecases.boundaries.output.port.StandardOutputPort;
import io.github.carlomicieli.util.Slug;
import io.github.carlomicieli.validation.ValidationError;
import io.github.carlomicieli.web.problems.ProblemDetail;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.uri.UriBuilder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * An helper class that help to translate from use case outputs to http responses.
 *
 * @param <TOutput> the use case output type
 */
public abstract class DefaultHttpResultPresenter<TOutput extends UseCaseOutput>
        implements StandardOutputPort<TOutput> {
    private HttpResponse<?> viewModel;

    public HttpResponse<?> viewModel() {
        return viewModel;
    }

    public void error(String errorMessage) {
        viewModel = HttpResponse.serverError(ProblemDetail.error(errorMessage));
    }

    public void error(Throwable ex) {
        viewModel = HttpResponse.serverError(ProblemDetail.error(ex));
    }

    public void invalidRequest(List<ValidationError> validationErrors) {
        var errors =
                validationErrors.stream()
                        .collect(
                                Collectors.toMap(
                                        ValidationError::getPropertyName,
                                        ValidationError::getErrorMessage));
        viewModel = HttpResponse.badRequest(ProblemDetail.invalidRequest(errors));
    }

    public void created(String path, Slug slug) {
        var resourceUri = UriBuilder.of(path).expand(Map.of("slug", slug));
        viewModel = HttpResponse.created(resourceUri);
    }
}
