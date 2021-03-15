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

import java.util.Arrays;
import java.util.Locale;

/** Utility methods to validate country and language codes against the ISO value. */
public interface ISOValidationUtils {

    /**
     * Returns {@code true} if the value is a valid 2-letter language code as defined in ISO 639.
     *
     * @param lang the language code
     * @return {@code true} if the value is a valid language; {@code false} otherwise
     */
    static boolean languageIsValid(String lang) {
        if (lang == null || lang.isBlank()) {
            return true;
        }
        return Arrays.binarySearch(Locale.getISOLanguages(), lang.toLowerCase()) >= 0;
    }

    /**
     * Returns {@code true} if the value is a valid 2-letter country code as defined in ISO 3166.
     *
     * @param country the country code
     * @return {@code true} if the value is a valid country; {@code false} otherwise
     */
    static boolean countryIsValid(String country) {
        if (country == null || country.isBlank()) {
            return true;
        }
        return Arrays.binarySearch(Locale.getISOCountries(), country.toUpperCase()) >= 0;
    }
}
