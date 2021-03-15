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
package io.github.carlomicieli.catalog.catalogitems;

import com.google.common.base.Strings;
import java.util.Optional;
import lombok.Value;

/** It represents a catalog item number. */
@Value
public class ItemNumber implements Comparable<ItemNumber> {
    String value;

    private ItemNumber(String value) {
        if (Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("Item number value cannot be blank or null");
        }

        this.value = value;
    }

    public static ItemNumber of(String value) {
        return new ItemNumber(value);
    }

    public static Optional<ItemNumber> tryCreate(String v) {
        if (Strings.isNullOrEmpty(v)) {
            return Optional.empty();
        }

        return Optional.of(new ItemNumber(v));
    }

    @Override
    public int compareTo(ItemNumber that) {
        return this.value.compareTo(that.value);
    }
}
