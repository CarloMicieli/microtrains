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
package io.github.carlomicieli.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class EnumUtilsTest {
    @Test
    void shouldReturnAnIterableForEnumLabels() {
        Iterable<String> it = EnumUtils.labelsFor(Visibility.class);
        assertThat(it).hasSize(2).contains("public", "private");
    }

    @Test
    void shouldReturnStreamWithEnumLabels() {
        Stream<String> labelsStream = EnumUtils.labelsStream(Visibility.class);

        List<String> it = labelsStream.collect(Collectors.toList());
        assertThat(it).hasSize(2).contains("public", "private");
    }

    @Test
    void shouldParseStringsAsEnumValuesInASafeManner() {
        Try<Category> cat = EnumUtils.tryParseEnum(Category.class, "electric-locomotives");
        assertThat(cat.get()).isNotNull().isEqualTo(Category.ELECTRIC_LOCOMOTIVES);
    }

    @Test
    void shouldParseStringsAsEnumValues() {
        Category cat = EnumUtils.parseEnum(Category.class, "electric-locomotives");
        assertThat(cat).isNotNull().isEqualTo(Category.ELECTRIC_LOCOMOTIVES);
    }

    @Test
    void shouldProduceValueForAKeyInEnums() {
        String key = EnumUtils.keyFor(Category.ELECTRIC_MULTIPLE_UNIT);
        assertThat(key).isNotEmpty().isEqualTo("electric-multiple-unit");
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenTheValueIsNotValid() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EnumUtils.parseEnum(Category.class, "not-valid"));
    }

    @SuppressWarnings("unused")
    enum Visibility {
        PRIVATE,
        PUBLIC
    }

    enum Category {
        ELECTRIC_LOCOMOTIVES,
        ELECTRIC_MULTIPLE_UNIT
    }
}
