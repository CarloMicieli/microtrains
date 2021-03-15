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

import static org.assertj.core.api.Assertions.*;

import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("UriUtils")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class UriUtilsTest {

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @ParameterizedTest
    @MethodSource
    void it_should_convert_strings_to_urls(String s, Optional<URI> expectedResult) {
        var url = UriUtils.tryConvert(s);
        assertThat(url).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> it_should_convert_strings_to_urls() throws Exception {
        return Stream.of(
                Arguments.of("http://localhost", Optional.of(new URI("http://localhost"))),
                Arguments.of(
                        "http://localhost:1234", Optional.of(new URI("http://localhost:1234"))),
                Arguments.of("", Optional.empty()),
                Arguments.of("     ", Optional.empty()),
                Arguments.of("not a valid url", Optional.empty()),
                Arguments.of(null, Optional.empty()));
    }
}
