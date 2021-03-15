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
package io.github.carlomicieli.phonenumbers;

import com.google.common.base.Strings;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.Data;

/** An immutable class to store a phone number */
@Data
public final class PhoneNumber {
    private static final PhoneNumberUtil PHONE_NUMBER_UTIL = PhoneNumberUtil.getInstance();
    private final String value;

    private PhoneNumber(String value) {
        if (Strings.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("Phone number value cannot be blank or null");
        }

        this.value = value;
    }

    public static PhoneNumber valueOf(String value) {
        return new PhoneNumber(value);
    }

    public static PhoneNumber valueOf(String value, String regionCode) {
        try {
            var phoneNumber = PHONE_NUMBER_UTIL.parse(value, regionCode);
            var formattedValue =
                    PHONE_NUMBER_UTIL.format(
                            phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
            return new PhoneNumber(formattedValue);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid value for a phone number");
        }
    }
}
