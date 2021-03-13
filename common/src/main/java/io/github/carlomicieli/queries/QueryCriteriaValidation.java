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
package io.github.carlomicieli.queries;

import io.github.carlomicieli.queries.criteria.Criteria;
import io.github.carlomicieli.queries.criteria.CriteriaValidator;
import io.github.carlomicieli.queries.criteria.InvalidCriteriaException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class QueryCriteriaValidation<C extends Criteria> {
  private final CriteriaValidator<C> criteriaValidator;

  protected void validateCriteria(C criteria) {
    Objects.requireNonNull(criteria);

    var validationErrors = criteriaValidator.validateCriteria(criteria);
    if (!validationErrors.isEmpty()) {
      throw new InvalidCriteriaException(validationErrors);
    }
  }
}
