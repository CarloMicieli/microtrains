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
package io.github.carlomicieli.domain;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * A base class for all aggregate root factories.
 *
 * <pre>
 *   “There are some cases in which an object creation and assembly corresponds to a milestone significant in the domain, such as “open a bank account.” But object creation and assembly usually have no meaning in the domain… To solve this problem, we have to add constructs to the domain design that are not entities, value objects, or services.”
 *   -- Eric Evans in Domain Driven Design
 * </pre>
 *
 * @param <T> the aggregate root data type
 * @param <ID> the aggregate identifier data type
 */
public abstract class AggregateRootFactory<T extends AggregateRoot<ID>, ID extends Identifier> {
    private final Clock clock;
    private final Supplier<ID> identifierSource;

    public AggregateRootFactory(Clock clock, Supplier<ID> identifierSource) {
        this.clock = Objects.requireNonNull(clock, "A clock instance is required");
        this.identifierSource =
                Objects.requireNonNull(identifierSource, "An identifier source is required");
    }

    protected Instant getCurrentInstant() {
        return clock.instant();
    }

    protected ID generateNewId() {
        return identifierSource.get();
    }
}
