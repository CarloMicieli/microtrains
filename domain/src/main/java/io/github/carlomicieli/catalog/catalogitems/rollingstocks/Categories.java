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

public final class Categories {

  public static Category tryParse(String value, Category defaultVal) {
    try {
      return Category.valueOf(value);
    } catch (Exception ex) {
      return defaultVal;
    }
  }

  public static boolean isLocomotive(Category cat) {
    return cat == Category.STEAM_LOCOMOTIVE
        || cat == Category.ELECTRIC_LOCOMOTIVE
        || cat == Category.DIESEL_LOCOMOTIVE;
  }

  public static boolean isTrain(String category) {
    throw new UnsupportedOperationException();
  }

  public static boolean isTrain(Category cat) {
    return cat == Category.ELECTRIC_MULTIPLE_UNIT
        || cat == Category.RAILCAR
        || cat == Category.TRAIN_SET
        || cat == Category.STARTER_SET;
  }

  public static boolean isFreightCar(Category cat) {
    return cat == Category.FREIGHT_CAR;
  }

  public static boolean isPassengerCar(Category cat) {
    return cat == Category.PASSENGER_CAR;
  }
}
