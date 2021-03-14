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

public enum PassengerCarType {
  /**
   * An "open coach" has a central aisle; the car's interior is often filled with row upon row of
   * seats as in a passenger airliner.
   */
  OPEN_COACH,

  /**
   * "closed" coaches or "compartment" cars have a side corridor to connect individual compartments
   * along the body of the train, each with two rows of seats facing each other.
   */
  COMPARTMENT_COACH,

  /** A dining car (or diner) is used to serve meals to the passengers. */
  DINING_CAR,

  /** Lounge cars carry a bar and public seating. */
  LOUNGE,

  /**
   * The observation car almost always operated as the last car in a passenger train, in US
   * practice. Its interior could include features of a coach, lounge, diner, or sleeper. /// The
   * main spotting feature was at the tail end of the car.
   */
  OBSERVATION,

  /**
   * Often called "sleepers" or "Pullman cars", these cars provide sleeping arrangements for
   * passengers travelling at night. /// Early models were divided into sections, where coach
   * seating converted at night into semi-private berths.
   */
  SLEEPING_CAR,

  /**
   * The baggage car is a car that was normally placed between the train's motive power and the
   * remainder of the passenger train. /// The car's interior is normally wide open and is used to
   * carry passengers' checked baggage.
   */
  BAGGAGE_CAR,

  DOUBLE_DECKER,

  COMBINE_CAR,

  DRIVING_TRAILER,

  RAILWAY_POST_OFFICE
}
