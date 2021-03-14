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

import io.github.carlomicieli.catalog.catalogitems.Control;
import io.github.carlomicieli.catalog.catalogitems.DccInterface;
import io.github.carlomicieli.catalog.catalogitems.Epoch;
import io.github.carlomicieli.catalog.railways.RailwayId;
import io.github.carlomicieli.domain.EntityFactory;
import java.util.function.Supplier;

public class RollingStockFactory extends EntityFactory<RollingStock, RollingStockId> {
  public RollingStockFactory(Supplier<RollingStockId> identifierSource) {
    super(identifierSource);
  }

  public Locomotive createNewLocomotive(
      RailwayId railway,
      Category category,
      Epoch epoch,
      LengthOverBuffer length,
      MinRadius minRadius,
      Prototype prototype,
      Couplers couplers,
      String livery,
      String depot,
      DccInterface dccInterface,
      Control control) {
    var newId = generateNewId();

    return Locomotive.builder()
        .id(newId)
        .category(category)
        .control(control)
        .couplers(couplers)
        .dccInterface(dccInterface)
        .depot(depot)
        .epoch(epoch)
        .lengthOverBuffer(length)
        .livery(livery)
        .minRadius(minRadius)
        .prototype(prototype)
        .railway(railway)
        .build();
  }

  public FreightCar createNewFreightCar(
      RailwayId railway,
      Epoch epoch,
      LengthOverBuffer length,
      MinRadius minRadius,
      Couplers couplers,
      String livery,
      String typeName,
      FreightCarType freightCarType) {
    var newId = generateNewId();

    return FreightCar.builder()
        .id(newId)
        .category(Category.FREIGHT_CAR)
        .couplers(couplers)
        .epoch(epoch)
        .lengthOverBuffer(length)
        .livery(livery)
        .minRadius(minRadius)
        .railway(railway)
        .type(freightCarType)
        .typeName(typeName)
        .build();
  }

  public PassengerCar createNewPassengerCar(
      RailwayId railway,
      Epoch epoch,
      LengthOverBuffer length,
      MinRadius minRadius,
      Couplers couplers,
      String livery,
      String typeName,
      PassengerCarType passengerCarType,
      ServiceLevel serviceLevel) {
    var newId = generateNewId();

    return PassengerCar.builder()
        .id(newId)
        .category(Category.PASSENGER_CAR)
        .couplers(couplers)
        .epoch(epoch)
        .lengthOverBuffer(length)
        .livery(livery)
        .minRadius(minRadius)
        .railway(railway)
        .type(passengerCarType)
        .serviceLevel(serviceLevel)
        .typeName(typeName)
        .build();
  }

  public Train createNewTrain(
      RailwayId railway,
      Category category,
      Epoch epoch,
      LengthOverBuffer length,
      MinRadius minRadius,
      Couplers couplers,
      String typeName,
      String livery,
      String depot,
      DccInterface dccInterface,
      Control control) {
    var newId = generateNewId();

    return Train.builder()
        .id(newId)
        .control(control)
        .couplers(couplers)
        .category(category)
        .depot(depot)
        .dccInterface(dccInterface)
        .epoch(epoch)
        .lengthOverBuffer(length)
        .livery(livery)
        .minRadius(minRadius)
        .railway(railway)
        .typeName(typeName)
        .build();
  }
}
