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
package io.github.carlomicieli.collecting.wishlists;

import io.github.carlomicieli.collecting.valueobject.Owner;
import io.github.carlomicieli.domain.AggregateRootFactory;
import java.time.Clock;
import java.util.function.Supplier;

public class WishlistFactory extends AggregateRootFactory<Wishlist, WishlistId> {
    public WishlistFactory(Clock clock, Supplier<WishlistId> identifierSource) {
        super(clock, identifierSource);
    }

    public Wishlist createNewWishlist(
            Owner owner, String listName, Visibility visibility, Budget budget) {
        throw new UnsupportedOperationException("TODO");
    }
}
