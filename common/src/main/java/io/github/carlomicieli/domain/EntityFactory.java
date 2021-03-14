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

import java.util.Objects;
import java.util.function.Supplier;

public abstract class EntityFactory<T extends Entity<ID>, ID extends Identifier> {
  private final Supplier<ID> identifierSource;

  public EntityFactory(Supplier<ID> identifierSource) {
    this.identifierSource =
        Objects.requireNonNull(identifierSource, "An identifier source is required");
  }

  protected ID generateNewId() {
    return identifierSource.get();
  }
}
