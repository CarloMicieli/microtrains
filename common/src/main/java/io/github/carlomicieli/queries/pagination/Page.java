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

import lombok.Value;

@Value
public class Page {
  int start;
  int limit;

  private Page(int start, int limit) {
    if (start < 0) {
      throw new IllegalArgumentException("Page starting index cannot be negative");
    }

    if (limit < 0) {
      throw new IllegalArgumentException("Page limit cannot be negative");
    }

    this.start = start;
    this.limit = limit;
  }

  public static Page of(int start, int limit) {
    return new Page(start, limit);
  }

  public Page next() {
    return Page.of(getLimit() + getStart(), getLimit());
  }

  public Page prev() {
    int newStart = getStart() > getLimit() ? getStart() - getLimit() : 0;
    return Page.of(newStart, getLimit());
  }

  public static final Page DEFAULT_PAGE = new Page(0, 25);
}
