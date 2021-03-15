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
package io.github.carlomicieli.web;

import io.github.carlomicieli.web.problems.ProblemDetail;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import io.micronaut.web.router.exceptions.UnsatisfiedBodyRouteException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GlobalErrorsHandler {

    @Error(global = true, exception = UnsatisfiedBodyRouteException.class)
    public HttpResponse<ProblemDetail> onUnsatisfiedBodyRouteException(
            UnsatisfiedBodyRouteException ex) {
        var problemDetail = ProblemDetail.unprocessableEntity(ex.getMessage());
        return HttpResponse.unprocessableEntity().body(problemDetail);
    }
}
