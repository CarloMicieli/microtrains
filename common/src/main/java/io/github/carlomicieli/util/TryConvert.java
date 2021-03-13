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

public interface TryConvert {
  /**
   * Try to convert the provided string to an <em>Integer</em> instance.
   *
   * @param s the string to be converted
   * @return an Integer
   */
  static Try<Integer> toInteger(final String s) {
    return Try.apply(() -> Integer.valueOf(s));
  }

  /**
   * Try to convert the provided string to a <em>Double</em> instance.
   *
   * @param s the string to be converted
   * @return a Double
   */
  static Try<Double> toDouble(final String s) {
    return Try.apply(() -> Double.valueOf(s));
  }
}
