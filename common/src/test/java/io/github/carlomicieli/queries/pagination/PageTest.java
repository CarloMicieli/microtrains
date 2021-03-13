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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A page")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PageTest {

  @Test
  void is_created_with_a_start_index_and_a_limit() {
    var page = Page.of(10, 50);
    assertThat(page).isNotNull();
    assertThat(page.getStart()).isEqualTo(10);
    assertThat(page.getLimit()).isEqualTo(50);
  }

  @Test
  void only_allows_non_negative_start_indexes() {
    var ex = catchThrowableOfType(() -> Page.of(-1, 10), IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage()).isEqualTo("Page starting index cannot be negative");
  }

  @Test
  void only_allows_non_negative_limits() {
    var ex = catchThrowableOfType(() -> Page.of(0, -10), IllegalArgumentException.class);
    assertThat(ex).isNotNull();
    assertThat(ex.getMessage()).isEqualTo("Page limit cannot be negative");
  }

  @Test
  void has_a_default_page() {
    assertThat(Page.DEFAULT_PAGE.getStart()).isEqualTo(0);
    assertThat(Page.DEFAULT_PAGE.getLimit()).isEqualTo(25);
  }

  @Test
  void can_be_compared_to_other_pages() {
    var page1 = Page.of(10, 50);
    var page2 = Page.of(10, 50);
    var page3 = Page.of(0, 10);

    assertThat(page1.equals(page2)).isTrue();
    assertThat(page1.equals(page3)).isFalse();
  }

  @Test
  void can_find_the_following_page() {
    var page = Page.of(10, 50);
    var next = page.next();

    assertThat(next).isNotNull();
    assertThat(next.getLimit()).isEqualTo(50);
    assertThat(next.getStart()).isEqualTo(60);
  }

  @Test
  void can_find_the_previous_page() {
    var page = Page.of(50, 25);
    var previous = page.prev();

    assertThat(previous).isNotNull();
    assertThat(previous.getLimit()).isEqualTo(25);
    assertThat(previous.getStart()).isEqualTo(25);
  }

  @Test
  void can_find_the_previous_page_also_when_the_current_page_is_the_first() {
    var page = Page.of(0, 25);
    var previous = page.prev();

    assertThat(previous).isNotNull();
    assertThat(previous.getLimit()).isEqualTo(25);
    assertThat(previous.getStart()).isEqualTo(0);
  }
}
