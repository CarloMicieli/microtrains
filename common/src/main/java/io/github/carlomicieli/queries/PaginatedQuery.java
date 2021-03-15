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
import io.github.carlomicieli.queries.pagination.Page;
import io.github.carlomicieli.queries.pagination.PaginatedResult;
import io.github.carlomicieli.queries.sorting.Sorting;

/**
 * It represents a {@code Query} with pagination.
 *
 * <p>Queries never modify the database. A query returns a view models that does not encapsulate any
 * domain knowledge.
 *
 * @param <T> the view model data type
 */
public interface PaginatedQuery<T> extends Query<PaginatedQuery.NoCriteria, T> {

    /**
     * Execute this {@code Query} in order to select one page of the corresponding data.
     *
     * @throws QueryExecutionException in case of any error
     */
    PaginatedResult<T> execute(Page currentPage, Sorting orderBy);

    class NoCriteria implements Criteria {}
}
