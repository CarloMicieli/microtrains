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
package io.github.carlomicieli.queries.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.*;

/** It represent the sorting criteria for {@code Query}s. */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
public final class Sorting {

    public static final Sorting DEFAULT_SORT = new Sorting(Collections.emptyList());
    private final List<SortCriteria> criteriaList;

    /** Return the number of criteria defined in this {@code Sorting} */
    public int size() {
        return criteriaList.size();
    }

    public List<SortCriteria> getCriteriaList() {
        return Collections.unmodifiableList(criteriaList);
    }

    public static Builder by(String propName, Direction direction) {
        List<SortCriteria> list = new ArrayList<>();
        list.add(SortCriteria.of(propName, direction));
        return new Builder(list);
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {
        private final List<SortCriteria> list;

        public Builder andThenBy(String propName, Direction direction) {
            list.add(SortCriteria.of(propName, direction));
            return this;
        }

        public Sorting build() {
            return new Sorting(list);
        }
    }

    @Value
    @AllArgsConstructor(staticName = "of")
    public static class SortCriteria {
        String propertyName;
        Direction direction;
    }
}
