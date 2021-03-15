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

import java.time.Instant;

/**
 * An aggregate root.
 *
 * <pre>
 *   "Cluster the entities and value objects into aggregates and define boundaries around each. Choose one
 *   entity to be the root of each aggregate and control all access to the objects inside the boundary
 *   through the root" -- Eric Evans in Domain Driven Design
 * </pre>
 *
 * @param <ID> the ID type
 */
public interface AggregateRoot<ID extends Identifier> extends Entity<ID> {
    /** The instant when this aggregate was created */
    Instant getCreatedDate();

    /** The instant when this aggregate was modified the last time */
    Instant getModifiedDate();

    /** The version number for this aggregate */
    int getVersion();
}
