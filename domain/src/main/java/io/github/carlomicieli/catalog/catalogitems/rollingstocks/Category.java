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
package io.github.carlomicieli.catalog.catalogitems.rollingstocks;

/** The enumeration of the model categories. */
public enum Category {
    /** The steam locomotives category */
    STEAM_LOCOMOTIVE,

    /** The diesel locomotives category */
    DIESEL_LOCOMOTIVE,

    /** The electric locomotives category */
    ELECTRIC_LOCOMOTIVE,

    /** The railcar category */
    RAILCAR,

    /** The electric multiple unit category */
    ELECTRIC_MULTIPLE_UNIT,

    /** The freight cars category */
    FREIGHT_CAR,

    /** The passenger cars category */
    PASSENGER_CAR,

    /** The train set category */
    TRAIN_SET,

    /** The starter sets (usually includes the tracks) category */
    STARTER_SET
}
