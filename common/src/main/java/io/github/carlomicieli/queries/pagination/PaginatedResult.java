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
package io.github.carlomicieli.queries.pagination;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A paginated result for a {@code Query}
 *
 * @param <T> the view model data type
 */
@EqualsAndHashCode
@ToString
public final class PaginatedResult<T> {

    private final List<T> results;
    private final boolean previous;
    private final boolean next;
    private final Page currentPage;

    private PaginatedResult(Page currentPage, List<T> results) {
        this.results = results;
        this.currentPage = currentPage;

        this.next = results.size() > currentPage.getLimit();
        this.previous = currentPage.getStart() > 0;
    }

    public static <E> PaginatedResult<E> of(Page currentPage, List<E> results) {
        return new PaginatedResult<>(currentPage, results);
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public Optional<Page> nextPage() {
        return next ? Optional.of(currentPage.next()) : Optional.empty();
    }

    public Page previousPage() {
        return previous ? currentPage.prev() : Page.DEFAULT_PAGE;
    }

    public Stream<T> getResults() {
        return results.stream().limit(currentPage.getLimit());
    }

    public boolean hasPrevious() {
        return previous;
    }

    public boolean hasNext() {
        return next;
    }
}
