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
package io.github.carlomicieli.catalog.catalogitems.deliverydates;

/** Thrown to indicate that a method has been passed an illegal or inappropriate argument. */
public class DeliveryDateFormatException extends IllegalArgumentException {

    /** Constructs an {@code DeliveryDateFormatException} with no detail message. */
    public DeliveryDateFormatException() {
        super();
    }

    /**
     * Constructs an {@code DeliveryDateFormatException} with the specified detail message.
     *
     * @param details the detail message
     */
    public DeliveryDateFormatException(String details) {
        super(details);
    }
}
