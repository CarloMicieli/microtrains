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

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Strings;
import java.util.Map;
import java.util.Optional;
import lombok.Data;

/**
 * The model railway industry adopted an 'Era', or 'Epoch' system; the idea being to group models
 * into a defined time bracket, so that locomotives, coaching and wagon stock could be reasonably
 * grouped together.
 */
@Data
public final class Epoch {
  private final String value1;
  private final String value2;

  private static final Map<String, Epoch> cachedValues;

  public static final Epoch I = new Epoch("I");

  public static final Epoch II = new Epoch("II");

  public static final Epoch IIa = new Epoch("IIa");

  public static final Epoch IIb = new Epoch("IIb");

  public static final Epoch III = new Epoch("III");

  public static final Epoch IIIa = new Epoch("IIIa");

  public static final Epoch IIIb = new Epoch("IIIb");

  public static final Epoch IV = new Epoch("IV");

  public static final Epoch IVa = new Epoch("IVa");

  public static final Epoch IVb = new Epoch("IVb");

  public static final Epoch V = new Epoch("V");

  public static final Epoch Va = new Epoch("Va");

  public static final Epoch Vb = new Epoch("Vb");

  public static final Epoch VI = new Epoch("VI");

  static {
    cachedValues =
        Map.ofEntries(
            epochEntry(Epoch.I),
            epochEntry(Epoch.II),
            epochEntry(Epoch.IIa),
            epochEntry(Epoch.IIb),
            epochEntry(Epoch.III),
            epochEntry(Epoch.IIIa),
            epochEntry(Epoch.IIIb),
            epochEntry(Epoch.IV),
            epochEntry(Epoch.IVa),
            epochEntry(Epoch.IVb),
            epochEntry(Epoch.V),
            epochEntry(Epoch.Va),
            epochEntry(Epoch.Vb),
            epochEntry(Epoch.VI));
  }

  private static Map.Entry<String, Epoch> epochEntry(Epoch ep) {
    return Map.entry(ep.toString().toUpperCase(), ep);
  }

  private Epoch(String epoch1) {
    this(epoch1, null);
  }

  private Epoch(String epoch1, String epoch2) {
    checkArgument(!Strings.isNullOrEmpty(epoch1), "epoch1 is required");

    this.value1 = epoch1;
    this.value2 = epoch2;
  }

  public static Optional<Epoch> tryParse(String str) {
    if (Strings.isNullOrEmpty(str)) {
      return Optional.empty();
    }

    var cached = cachedValues.get(str.trim().toUpperCase());
    if (cached != null) {
      return Optional.of(cached);
    }

    if (str.indexOf('/') != -1) {
      var tokens = str.split("/");
      if (tokens.length == 2) {
        var epoch1 = Epoch.parse(tokens[0].trim());
        var epoch2 = Epoch.parse(tokens[1].trim());
        return Optional.of(new Epoch(epoch1.value1, epoch2.value1));
      }
    }

    return Optional.empty();
  }

  /**
   * Try to parse the provided string as {@code Epoch}. This method is also able to parse multiple
   * epochs, different values need to be separated by {@code /}.
   *
   * @throws IllegalArgumentException when the provided value is not a valid {@code Epoch}
   */
  public static Epoch parse(String str) {
    return Epoch.tryParse(str)
        .orElseThrow(() -> new IllegalArgumentException("The value is not a valid epoch"));
  }

  @Override
  public String toString() {
    if (this.value2 == null || this.value2.isBlank()) {
      return this.value1;
    }

    return String.format("%s/%s", value1, value2);
  }
}
