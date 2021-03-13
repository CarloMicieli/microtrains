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

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import java.util.Objects;
import org.assertj.core.api.AbstractAssert;

public class HttpClientResponseExceptionAssert
    extends AbstractAssert<HttpClientResponseExceptionAssert, HttpClientResponseException> {
  public HttpClientResponseExceptionAssert(HttpClientResponseException e) {
    super(e, HttpClientResponseExceptionAssert.class);
  }

  public static HttpClientResponseExceptionAssert assertThat(HttpClientResponseException actual) {
    return new HttpClientResponseExceptionAssert(actual);
  }

  public HttpClientResponseExceptionAssert hasStatus(HttpStatus status) {
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
}
