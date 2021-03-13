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

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A paginated result")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PaginatedResultTest {

  @Test
  void is_created_from_a_current_page_and_a_stream_of_results() {
    var results = List.of("one", "two", "three");

    var pr = PaginatedResult.of(Page.DEFAULT_PAGE, results);

    assertThat(pr).isNotNull();
    assertThat(pr.getCurrentPage()).isEqualTo(Page.DEFAULT_PAGE);
    assertThat(pr.getResults().collect(Collectors.toList())).isEqualTo(results);
  }

  @Test
  void is_checking_whether_there_is_a_previous_page() {
    var page = Page.of(2, 4);
    var results = IntStream.range(0, 10).mapToObj(Integer::toString).collect(Collectors.toList());

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.hasPrevious()).isTrue();
  }

  @Test
  void is_checking_whether_there_is_a_next_page() {
    var page = Page.of(0, 4);
    var results = IntStream.range(0, 5).mapToObj(Integer::toString).collect(Collectors.toList());

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.hasNext()).isTrue();
  }

  @Test
  void is_expecting_a_result_with_one_element_more_to_make_the_check_for_the_next_page() {
    var page = Page.of(0, 4);
    var results = List.of("0", "1", "2", "3", "4");

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.getResults().collect(Collectors.toList())).isEqualTo(List.of("0", "1", "2", "3"));
  }

  @SuppressWarnings("OptionalGetWithoutIsPresent")
  @Test
  void is_returning_the_next_page_of_results() {
    var page = Page.of(0, 4);
    var results = List.of("0", "1", "2", "3", "4");

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.nextPage().get()).isEqualTo(Page.of(4, 4));
  }

  @Test
  void is_returning_an_empty_result_when_this_is_the_last_page() {
    var page = Page.of(0, 5);
    var results = List.of("0", "1", "2", "3", "4");

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.nextPage().isEmpty()).isTrue();
  }

  @Test
  void is_returning_the_previous_page_of_results() {
    var page = Page.of(4, 4);
    var results = List.of("0", "1", "2", "3", "4");

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.previousPage()).isEqualTo(Page.of(0, 4));
  }

  @Test
  void is_returning_the_first_page_when_the_current_page_has_no_previous() {
    var page = Page.of(2, 50);
    var results = List.of("0", "1", "2", "3", "4");

    var pr = PaginatedResult.of(page, results);

    assertThat(pr.previousPage()).isEqualTo(Page.of(0, 50));
  }
}
