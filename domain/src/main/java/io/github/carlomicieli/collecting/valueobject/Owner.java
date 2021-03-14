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
package io.github.carlomicieli.collecting.valueobject;

import com.google.common.base.Strings;
import lombok.Value;

/** A Collection or Wish list owner name */
@Value
public class Owner {
  String name;

  private Owner(String name) {
    if (Strings.isNullOrEmpty(name)) {
      throw new IllegalArgumentException("Owner name is required");
    }
    this.name = name;
  }

  public static Owner withName(String name) {
    return new Owner(name);
  }

  @Override
  public String toString() {
    return name;
  }
}
