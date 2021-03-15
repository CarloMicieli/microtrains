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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A sorting")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SortingTest {

    @Test
    void by_default_is_created_without_any_criteria() {
        var sorting = Sorting.DEFAULT_SORT;
        assertThat(sorting.size()).isEqualTo(0);
    }

    @Test
    void is_created_using_a_builder() {
        var sorting = Sorting.by("id", Direction.ASC).andThenBy("name", Direction.DESC).build();

        assertThat(sorting).isNotNull();
        assertThat(sorting.size()).isEqualTo(2);
        assertThat(sorting.getCriteriaList().get(0))
                .isEqualTo(Sorting.SortCriteria.of("id", Direction.ASC));
        assertThat(sorting.getCriteriaList().get(1))
                .isEqualTo(Sorting.SortCriteria.of("name", Direction.DESC));
    }
}
