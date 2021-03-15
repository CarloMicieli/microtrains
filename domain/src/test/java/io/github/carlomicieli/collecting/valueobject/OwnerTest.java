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
package io.github.carlomicieli.collecting.valueobject;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("Owner")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OwnerTest {
    @Test
    void is_created_by_the_name() {
        var owner = Owner.withName("John Doe");
        assertThat(owner).isNotNull();
        assertThat(owner.getName()).isEqualTo("John Doe");
    }

    @Test
    void should_have_a_string_representation() {
        var owner = Owner.withName("John Doe");
        assertThat(owner.toString()).isEqualTo("John Doe");
    }

    @Test
    void must_have_a_name_otherwise_an_exception_is_thrown() {
        var ex = catchThrowableOfType(() -> Owner.withName(""), IllegalArgumentException.class);
        assertThat(ex).isNotNull();
        assertThat(ex).hasMessage("Owner name is required");
    }

    @Test
    void should_compare_two_owners_for_equality() {
        var o1 = Owner.withName("John");
        var o2 = Owner.withName("John");

        assertThat(o1).isEqualTo(o1);
        assertThat(o1).isEqualTo(o2);
    }
}
