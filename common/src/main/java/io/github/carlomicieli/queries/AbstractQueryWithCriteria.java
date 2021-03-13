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
import io.github.carlomicieli.queries.sorting.Sorting;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class AbstractQueryWithCriteria<C extends Criteria, T>
    extends QueryCriteriaValidation<C> implements QueryWithCriteria<C, T> {

  protected AbstractQueryWithCriteria(CriteriaValidator<C> criteriaValidator) {
    super(criteriaValidator);
  }

  @Override
  public final Stream<T> execute(C criteria, Sorting orderBy) {
    validateOrderBy(orderBy);
    validateCriteria(criteria);

    return handle(criteria, orderBy);
  }

  protected abstract Stream<T> handle(C criteria, Sorting orderBy);

  private static void validateOrderBy(Sorting orderBy) {
    Objects.requireNonNull(orderBy);
  }
}
