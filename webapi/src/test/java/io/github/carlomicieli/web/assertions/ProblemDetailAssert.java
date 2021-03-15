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
package io.github.carlomicieli.web.assertions;

import io.github.carlomicieli.web.problems.ProblemDetail;
import io.github.carlomicieli.web.problems.URN;
import java.util.Objects;
import org.assertj.core.api.AbstractAssert;

public final class ProblemDetailAssert extends AbstractAssert<ProblemDetailAssert, ProblemDetail> {
    public ProblemDetailAssert(ProblemDetail problemDetail) {
        super(problemDetail, ProblemDetailAssert.class);
    }

    public static ProblemDetailAssert assertThat(ProblemDetail actual) {
        return new ProblemDetailAssert(actual);
    }

    public ProblemDetailAssert hasProblemType(URN expectedType) {
        isNotNull();
        if (!Objects.equals(actual.getType(), expectedType)) {
            failWithActualExpectedAndMessage(
                    actual.getType(),
                    expectedType,
                    "Expected type to be <%s> but was <%s>",
                    expectedType,
                    actual.getType());
        }
        return this;
    }

    public ProblemDetailAssert hasTitle(String expectedTitle) {
        isNotNull();
        if (!Objects.equals(actual.getTitle(), expectedTitle)) {
            failWithActualExpectedAndMessage(
                    actual.getTitle(),
                    expectedTitle,
                    "Expected title to be <%s> but was <%s>",
                    expectedTitle,
                    actual.getTitle());
        }
        return this;
    }

    public ProblemDetailAssert hasStatus(int status) {
        isNotNull();
        if (!Objects.equals(actual.getStatus(), status)) {
            failWithActualExpectedAndMessage(
                    actual.getStatus(),
                    status,
                    "Expected HTTP status to be <%s> but was <%s>",
                    status,
                    actual.getStatus());
        }
        return this;
    }

    public ProblemDetailAssert hasDetails(String details) {
        isNotNull();
        if (!Objects.equals(actual.getDetail(), details)) {
            failWithActualExpectedAndMessage(
                    actual.getDetail(),
                    details,
                    "Expected details to be <%s> but was <%s>",
                    details,
                    actual.getDetail());
        }
        return this;
    }

    public ProblemDetailAssert isWithTimestamp() {
        isNotNull();
        if (!Objects.nonNull(actual.getTimestamp())) {
            failWithMessage(
                    "Expected timestamp to be non null but was <%s>", actual.getTimestamp());
        }
        return this;
    }

    public ProblemDetailAssert isWithInstanceId() {
        isNotNull();
        if (!Objects.nonNull(actual.getInstance())) {
            failWithMessage("Expected instance to be non null but was <%s>", actual.getInstance());
        }
        return this;
    }
}
