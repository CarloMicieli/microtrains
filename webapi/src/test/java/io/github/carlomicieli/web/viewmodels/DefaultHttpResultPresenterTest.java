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

import static io.github.carlomicieli.web.assertions.HttpResponseAssert.*;
import static io.github.carlomicieli.web.assertions.ProblemDetailAssert.*;
import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.usecases.boundaries.output.UseCaseOutput;
import io.github.carlomicieli.validation.ValidationError;
import io.github.carlomicieli.web.problems.ProblemDetail;
import io.github.carlomicieli.web.problems.URN;
import io.micronaut.http.HttpStatus;
import java.util.List;
import java.util.Map;
import lombok.Value;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("DefaultHttpResultPresenter")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DefaultHttpResultPresenterTest {

    private final DefaultHttpResultPresenter<TestOutputCase> httpResultPresenter =
            new TestDefaultHttpResultPresenter();

    @Test
    void it_should_produce_a_bad_request_view_model_from_invalid_requests() {
        httpResultPresenter.invalidRequest(
                List.of(ValidationError.of("value", "must be positive", -1)));
        var viewModel = httpResultPresenter.viewModel();

        assertThat(viewModel)
                .isNotNull()
                .hasStatus(HttpStatus.BAD_REQUEST)
                .hasReason("Bad Request");

        assertThat(viewModel.body()).isInstanceOf(ProblemDetail.class);
        var problemDetails = (ProblemDetail) viewModel.body();
        assertThat(problemDetails)
                .hasProblemType(URN.fromProblemType("bad-request"))
                .hasTitle("Invalid request")
                .hasDetails(
                        "Fields validation failed for this request. Check them before you try again.")
                .hasStatus(400)
                .isWithInstanceId()
                .isWithTimestamp();

        assertThat(problemDetails.getFields()).contains(Map.entry("value", "must be positive"));
    }

    @Test
    void it_should_produce_a_generic_error_view_model_from_a_throwable() {
        httpResultPresenter.error(new RuntimeException("My test exception"));
        var viewModel = httpResultPresenter.viewModel();

        assertThat(viewModel)
                .isNotNull()
                .hasStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .hasReason("Internal Server Error");

        assertThat(viewModel.body()).isInstanceOf(ProblemDetail.class);
        var problemDetails = (ProblemDetail) viewModel.body();
        assertThat(problemDetails)
                .hasProblemType(URN.fromProblemType("internal-server-error"))
                .hasTitle("Internal Server Error")
                .hasDetails("Oops, something went wrong")
                .hasStatus(500)
                .isWithInstanceId()
                .isWithTimestamp();
    }

    @Test
    void it_should_produce_a_generic_error_view_model_from_an_error_message() {
        httpResultPresenter.error("My test error");
        var viewModel = httpResultPresenter.viewModel();

        assertThat(viewModel)
                .isNotNull()
                .hasStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .hasReason("Internal Server Error");

        assertThat(viewModel.body()).isInstanceOf(ProblemDetail.class);
        var problemDetails = (ProblemDetail) viewModel.body();
        assertThat(problemDetails)
                .hasProblemType(URN.fromProblemType("internal-server-error"))
                .hasTitle("Internal Server Error")
                .hasDetails("My test error")
                .hasStatus(500)
                .isWithInstanceId()
                .isWithTimestamp();
    }

    static class TestDefaultHttpResultPresenter extends DefaultHttpResultPresenter<TestOutputCase> {
        @Override
        public void standard(TestOutputCase output) {}
    }

    @Value
    static class TestOutputCase implements UseCaseOutput {
        int value;
        String label;
    }
}
