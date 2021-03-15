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
import io.github.carlomicieli.queries.sorting.Sorting;
import java.util.stream.Stream;

/**
 * It represents a {@code Query} without pagination.
 *
 * <p>Queries never modify the database. A query returns a view models that does not encapsulate any
 * domain knowledge.
 *
 * @param <C> the {@code Criteria} data type
 * @param <T> the view model data type
 */
public interface QueryWithCriteria<C extends Criteria, T> extends Query<C, T> {

    /**
     * Execute this {@code Query} using the provided {@code criteria} in order to select the
     * corresponding data.
     *
     * @throws QueryExecutionException in case of any error
     */
    default Stream<T> execute(C criteria) {
        return execute(criteria, Sorting.DEFAULT_SORT);
    }

    /**
     * Execute this {@code Query} using the provided {@code criteria} in order to select the
     * corresponding data.
     *
     * @throws QueryExecutionException in case of any error
     */
    Stream<T> execute(C criteria, Sorting orderBy);
}
