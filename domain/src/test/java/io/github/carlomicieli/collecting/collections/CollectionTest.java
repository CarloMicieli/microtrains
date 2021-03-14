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
package io.github.carlomicieli.collecting.collections;

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.collecting.valueobject.Owner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A collection")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CollectionTest {
  private static final Owner FIXED_OWNER = Owner.withName("George");

  @Test
  void is_created_with_an_owner() {
    var c = Collection.builder().owner(FIXED_OWNER).build();
    assertThat(c).isNotNull();
    assertThat(c.getOwner()).isEqualTo(FIXED_OWNER);
  }

  @Test
  void is_created_empty() {
    var c = Collection.builder().owner(FIXED_OWNER).build();
    assertThat(c.size()).isZero();
  }
}
