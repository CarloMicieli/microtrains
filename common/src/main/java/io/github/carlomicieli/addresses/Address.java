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
package io.github.carlomicieli.addresses;

import com.google.common.base.Strings;
import lombok.*;

/** An immutable object value that represents an {@code Address}. */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@With
public class Address {
    String line1;
    String line2;
    String city;
    String region;
    String postalCode;
    String country;

    /**
     * Checks whether the provided {@code address} is empty.
     *
     * <p>An {@code address} is empty if it doesn't contain a valid value for at least one of the
     * following fields: {@code streetAddress}, {@code postalCode}, {@code city} or {@code country}.
     *
     * @param address the address to be checked
     * @return {@code true} if the {@code Address} is empty, {@code false} otherwise.
     */
    public static boolean isEmpty(Address address) {
        if (address == null) {
            return true;
        }

        return Strings.isNullOrEmpty(address.line1)
                || Strings.isNullOrEmpty(address.postalCode)
                || Strings.isNullOrEmpty(address.city)
                || Strings.isNullOrEmpty(address.country);
    }

    public static Address NULL_ADDRESS = new Address("", null, "", null, "", "");
}
