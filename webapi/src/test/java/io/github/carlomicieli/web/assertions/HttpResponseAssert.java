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

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import java.util.Objects;
import org.assertj.core.api.AbstractAssert;

public class HttpResponseAssert extends AbstractAssert<HttpResponseAssert, HttpResponse<?>> {
    public HttpResponseAssert(HttpResponse<?> e) {
        super(e, HttpResponseAssert.class);
    }

    public static HttpResponseAssert assertThat(HttpResponse<?> actual) {
        return new HttpResponseAssert(actual);
    }

    public HttpResponseAssert hasStatus(HttpStatus status) {
        isNotNull();
        if (!Objects.equals(actual.getStatus(), status)) {
            failWithActualExpectedAndMessage(
                    actual.getStatus(),
                    status,
                    "Expected http status to be <%s> but was <%s>",
                    status,
                    actual.getStatus());
        }
        return this;
    }

    public HttpResponseAssert hasReason(String expectedReason) {
        isNotNull();
        if (!Objects.equals(actual.reason(), expectedReason)) {
            failWithActualExpectedAndMessage(
                    actual.reason(),
                    expectedReason,
                    "Expected reason to be <%s> but was <%s>",
                    expectedReason,
                    actual.reason());
        }
        return this;
    }

    public HttpResponseAssert hasBody(Object expectedBody) {
        isNotNull();
        if (!Objects.equals(actual.body(), expectedBody)) {
            failWithActualExpectedAndMessage(
                    actual.body(),
                    expectedBody,
                    "Expected body to be <%s> but was <%s>",
                    expectedBody,
                    actual.body());
        }
        return this;
    }
}
