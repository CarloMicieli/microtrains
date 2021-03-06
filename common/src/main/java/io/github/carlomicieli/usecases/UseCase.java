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
package io.github.carlomicieli.usecases;

import io.github.carlomicieli.usecases.boundaries.input.UseCaseInput;
import io.micronaut.core.annotation.NonNull;

/**
 * A use case function, given a {@code UseCaseInput} it will eventually produce an {@code
 * UseCaseOutput}
 */
@FunctionalInterface
public interface UseCase<InType extends UseCaseInput> {

  /** Execute this {@code UseCase}. */
  void execute(@NonNull InType input);
}
