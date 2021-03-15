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

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("An epoch")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EpochTest {

    @Test
    void provides_constants_for_the_most_common_epochs() {
        assertThat(Epoch.I.toString()).isEqualTo("I");
        assertThat(Epoch.II.toString()).isEqualTo("II");
        assertThat(Epoch.IIa.toString()).isEqualTo("IIa");
        assertThat(Epoch.IIb.toString()).isEqualTo("IIb");
        assertThat(Epoch.III.toString()).isEqualTo("III");
        assertThat(Epoch.IIIa.toString()).isEqualTo("IIIa");
        assertThat(Epoch.IIIb.toString()).isEqualTo("IIIb");
        assertThat(Epoch.IV.toString()).isEqualTo("IV");
        assertThat(Epoch.IVa.toString()).isEqualTo("IVa");
        assertThat(Epoch.IVb.toString()).isEqualTo("IVb");
        assertThat(Epoch.V.toString()).isEqualTo("V");
        assertThat(Epoch.VI.toString()).isEqualTo("VI");
    }

    @Test
    void should_parse_valid_epoch_values() {
        var epoch = Epoch.parse("III");
        assertThat(epoch).isEqualTo(Epoch.III);
    }

    @Test
    void will_fail_to_parse_invalid_values_as_epochs() {
        var ex = catchThrowableOfType(() -> Epoch.parse("invalid"), IllegalArgumentException.class);
        assertThat(ex).isNotNull();
        assertThat(ex.getMessage()).isEqualTo("The value is not a valid epoch");
    }

    @Test
    void will_parse_multiple_values() {
        var expected = "III/IV";
        var result = Epoch.parse(expected);

        assertThat(result).isNotNull();
        assertThat(result.toString()).isEqualTo(expected);
    }

    @Test
    void will_try_to_parse_multiple_values() {
        var expected = "III/IV";
        var result = Epoch.tryParse(expected);

        assertThat(result).isNotNull();
        assertThat(result).isPresent();
        assertThat(result.get().toString()).isEqualTo(expected);
    }

    @Test
    void will_fail_to_try_to_parse_invalid_values() {
        var expected = "invalid";
        var result = Epoch.tryParse(expected);

        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
    }
}
