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
package io.github.carlomicieli.countries;

import java.util.Map;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * It represents an ISO country.
 *
 * <p>A simple wrapper around country code ({@code ISO 3166 alpha-2} country code), to ensure it
 * allows only valid country codes
 */
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Country {

    private static final Map<String, Country> COUNTRIES;

    static {
        COUNTRIES =
                Map.ofEntries(
                        putCountry("at", "Austria"),
                        putCountry("be", "Belgium"),
                        putCountry("ca", "Canada"),
                        putCountry("cn", "China"),
                        putCountry("dk", "Denmark"),
                        putCountry("fi", "Finland"),
                        putCountry("fr", "France"),
                        putCountry("de", "Germany"),
                        putCountry("it", "Italy"),
                        putCountry("jp", "Japan"),
                        putCountry("mx", "Mexico"),
                        putCountry("nl", "Netherlands"),
                        putCountry("no", "Norway"),
                        putCountry("ro", "Romania"),
                        putCountry("ru", "Russian Federation"),
                        putCountry("es", "Spain"),
                        putCountry("se", "Sweden"),
                        putCountry("ch", "Switzerland"),
                        putCountry("tr", "Turkey"),
                        putCountry("uk", "United Kingdom"),
                        putCountry("us", "United States"));
    }

    private static Map.Entry<String, Country> putCountry(String code, String name) {
        return Map.entry(code, new Country(code, name));
    }

    String code;
    String englishName;

    public static Country of(String code) {
        if (code == null || code.length() != 2) {
            throw new IllegalArgumentException("Invalid country code");
        }

        if (!ISOValidationUtils.countryIsValid(code)) {
            throw new IllegalArgumentException("Invalid country code");
        }

        return COUNTRIES.getOrDefault(code.toLowerCase(), new Country(code, ""));
    }
}
