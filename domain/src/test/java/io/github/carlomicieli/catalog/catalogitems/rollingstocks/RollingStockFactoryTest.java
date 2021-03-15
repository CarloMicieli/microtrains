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

import static org.assertj.core.api.Assertions.*;

import io.github.carlomicieli.catalog.catalogitems.Control;
import io.github.carlomicieli.catalog.catalogitems.DccInterface;
import io.github.carlomicieli.catalog.catalogitems.Epoch;
import io.github.carlomicieli.catalog.railways.RailwayId;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("A RollingStock factory")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class RollingStockFactoryTest {

    private static final RailwayId FIXED_RAILWAY_ID =
            RailwayId.of(UUID.fromString("e45f901f-0751-489c-910e-ce112d06ccfe"));
    private static final RollingStockId FIXED_ROLLING_STOCK_ID =
            RollingStockId.of(UUID.fromString("e9f5ed5a-edb6-46fa-a55a-bc8632e89d3a"));

    private final RollingStockFactory factory;

    public RollingStockFactoryTest() {
        this.factory = new RollingStockFactory(() -> FIXED_ROLLING_STOCK_ID);
    }

    @Test
    void should_create_new_locomotives() {
        var length = LengthOverBuffer.ofMillimeters(BigDecimal.valueOf(210));
        var locomotive =
                factory.createNewLocomotive(
                        FIXED_RAILWAY_ID,
                        Category.ELECTRIC_LOCOMOTIVE,
                        Epoch.IV,
                        length,
                        MinRadius.ofMillimeters(360),
                        Prototype.ofLocomotive("E656", "E656 210"),
                        Couplers.NEM_352,
                        "blu / grigio",
                        "Milano Centrale",
                        DccInterface.NEM_652,
                        Control.DCC_READY);

        assertThat(locomotive).isNotNull();

        assertThat(locomotive.getId()).isEqualTo(FIXED_ROLLING_STOCK_ID);
        assertThat(locomotive.getRailway()).isEqualTo(FIXED_RAILWAY_ID);

        assertThat(locomotive.getCategory()).isEqualTo(Category.ELECTRIC_LOCOMOTIVE);
        assertThat(locomotive.getEpoch()).isEqualTo(Epoch.IV);
        assertThat(locomotive.getLengthOverBuffer()).isEqualTo(length);
        assertThat(locomotive.getMinRadius()).isEqualTo(MinRadius.ofMillimeters(360));
        assertThat(locomotive.getCouplers()).isEqualTo(Couplers.NEM_352);
        assertThat(locomotive.getLivery()).isEqualTo("blu / grigio");
        assertThat(locomotive.getDepot()).isEqualTo("Milano Centrale");
        assertThat(locomotive.getDccInterface()).isEqualTo(DccInterface.NEM_652);
        assertThat(locomotive.getControl()).isEqualTo(Control.DCC_READY);
    }

    @Test
    void should_create_new_trains() {
        var length = LengthOverBuffer.ofMillimeters(BigDecimal.valueOf(1000));
        var train =
                factory.createNewTrain(
                        FIXED_RAILWAY_ID,
                        Category.ELECTRIC_MULTIPLE_UNIT,
                        Epoch.IV,
                        length,
                        MinRadius.ofMillimeters(360),
                        Couplers.NEM_352,
                        "Etr 220",
                        "blu / grigio",
                        "Milano Centrale",
                        DccInterface.NEM_652,
                        Control.DCC_READY);

        assertThat(train).isNotNull();

        assertThat(train.getId()).isEqualTo(FIXED_ROLLING_STOCK_ID);
        assertThat(train.getRailway()).isEqualTo(FIXED_RAILWAY_ID);

        assertThat(train.getCategory()).isEqualTo(Category.ELECTRIC_MULTIPLE_UNIT);
        assertThat(train.getEpoch()).isEqualTo(Epoch.IV);
        assertThat(train.getLengthOverBuffer()).isEqualTo(length);
        assertThat(train.getMinRadius()).isEqualTo(MinRadius.ofMillimeters(360));
        assertThat(train.getCouplers()).isEqualTo(Couplers.NEM_352);
        assertThat(train.getTypeName()).isEqualTo("Etr 220");
        assertThat(train.getLivery()).isEqualTo("blu / grigio");
        assertThat(train.getDepot()).isEqualTo("Milano Centrale");
        assertThat(train.getDccInterface()).isEqualTo(DccInterface.NEM_652);
        assertThat(train.getControl()).isEqualTo(Control.DCC_READY);
    }

    @Test
    void should_create_new_passenger_cars() {
        var length = LengthOverBuffer.ofMillimeters(BigDecimal.valueOf(303));
        var passengerCar =
                factory.createNewPassengerCar(
                        FIXED_RAILWAY_ID,
                        Epoch.IV,
                        length,
                        MinRadius.ofMillimeters(360),
                        Couplers.NEM_352,
                        "grigio ardesia",
                        "UIC-X",
                        PassengerCarType.OPEN_COACH,
                        ServiceLevel.SecondClass);

        assertThat(passengerCar).isNotNull();

        assertThat(passengerCar.getId()).isEqualTo(FIXED_ROLLING_STOCK_ID);
        assertThat(passengerCar.getRailway()).isEqualTo(FIXED_RAILWAY_ID);

        assertThat(passengerCar.getCategory()).isEqualTo(Category.PASSENGER_CAR);
        assertThat(passengerCar.getEpoch()).isEqualTo(Epoch.IV);
        assertThat(passengerCar.getLengthOverBuffer()).isEqualTo(length);
        assertThat(passengerCar.getMinRadius()).isEqualTo(MinRadius.ofMillimeters(360));
        assertThat(passengerCar.getCouplers()).isEqualTo(Couplers.NEM_352);
        assertThat(passengerCar.getLivery()).isEqualTo("grigio ardesia");
        assertThat(passengerCar.getTypeName()).isEqualTo("UIC-X");
        assertThat(passengerCar.getType()).isEqualTo(PassengerCarType.OPEN_COACH);
        assertThat(passengerCar.getServiceLevel()).isEqualTo(ServiceLevel.SecondClass);
    }

    @Test
    void should_create_new_freight_cars() {
        var length = LengthOverBuffer.ofMillimeters(BigDecimal.valueOf(210));
        var passengerCar =
                factory.createNewFreightCar(
                        FIXED_RAILWAY_ID,
                        Epoch.IV,
                        length,
                        MinRadius.ofMillimeters(360),
                        Couplers.NEM_352,
                        "castano",
                        "Gondola",
                        FreightCarType.GONDOLA);

        assertThat(passengerCar).isNotNull();

        assertThat(passengerCar.getId()).isEqualTo(FIXED_ROLLING_STOCK_ID);
        assertThat(passengerCar.getRailway()).isEqualTo(FIXED_RAILWAY_ID);

        assertThat(passengerCar.getCategory()).isEqualTo(Category.FREIGHT_CAR);
        assertThat(passengerCar.getEpoch()).isEqualTo(Epoch.IV);
        assertThat(passengerCar.getLengthOverBuffer()).isEqualTo(length);
        assertThat(passengerCar.getMinRadius()).isEqualTo(MinRadius.ofMillimeters(360));
        assertThat(passengerCar.getCouplers()).isEqualTo(Couplers.NEM_352);
        assertThat(passengerCar.getLivery()).isEqualTo("castano");
        assertThat(passengerCar.getTypeName()).isEqualTo("Gondola");
        assertThat(passengerCar.getType()).isEqualTo(FreightCarType.GONDOLA);
    }
}
