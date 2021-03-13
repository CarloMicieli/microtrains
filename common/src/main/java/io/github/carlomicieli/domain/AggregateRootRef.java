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
package io.github.carlomicieli.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A reference to another aggregate root.
 *
 * @param <T> the aggregate data type
 * @param <ID> the ID type
 */
@AllArgsConstructor
@Data
public abstract class AggregateRootRef<T extends AggregateRoot<ID>, ID extends Identifier> {
  private final ID id;
  private final String slug;
  private final String representation;

  @Override
  public String toString() {
    return representation;
  }
}
