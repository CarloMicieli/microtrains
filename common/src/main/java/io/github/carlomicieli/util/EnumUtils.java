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

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A utility class for Java {@code Enum}s.
 *
 * @author Carlo Micieli
 * @since 1.0
 */
public interface EnumUtils {
  /**
   * Returns the keyword for the provided {@code enum} value.
   *
   * @param val the {@code enum} value to be parsed
   * @param <T> The enum type whose constant is to be returned
   * @return the keyword
   */
  static <T extends Enum<T>> String keyFor(T val) {
    return val.name().toLowerCase().replace('_', '-');
  }

  /**
   * Returns the enum constant of the specified enum type with the specified name.
   *
   * <p>If {@code str} is not a valid value for the given {@code enumType} then this method will
   * throw an {@code IllegalArgumentException}.
   *
   * @param enumType the Class object of the enum type from which to return a constant
   * @param name the name of the constant to return
   * @return the enum constant of the specified enum type with the specified name
   */
  static <T extends Enum<T>> T parseEnum(Class<T> enumType, String name) {
    Objects.requireNonNull(name);
    return T.valueOf(enumType, name.toUpperCase().replace('-', '_'));
  }

  /**
   * Returns the enum constant of the specified enum type with the specified name.
   *
   * @param enumType the Class object of the enum type from which to return a constant
   * @param name the name of the constant to return
   * @return the enum constant of the specified enum type with the specified name
   */
  static <T extends Enum<T>> Try<T> tryParseEnum(Class<T> enumType, String name) {
    return Try.apply(() -> parseEnum(enumType, name));
  }

  /**
   * Returns the labels list for the constants of the specified enum type.
   *
   * @param enumType the Class object of the enum type from which to return a constant
   * @param <T> The enum type whose constant is to be returned
   * @return the labels list
   */
  static <T extends Enum<T>> Iterable<String> labelsFor(Class<T> enumType) {
    return labelsStream(enumType).collect(Collectors.toList());
  }

  /**
   * Returns the labels stream for the constants of the specified enum type.
   *
   * @param enumType the Class object of the enum type from which to return a constant
   * @param <T> The enum type whose constant is to be returned
   * @return the labels stream
   */
  static <T extends Enum<T>> Stream<String> labelsStream(Class<T> enumType) {
    return Stream.of(enumType.getEnumConstants()).map(EnumUtils::keyFor);
  }
}
