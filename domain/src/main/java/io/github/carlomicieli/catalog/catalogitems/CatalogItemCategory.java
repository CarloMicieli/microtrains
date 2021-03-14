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
package io.github.carlomicieli.catalog.catalogitems;

public enum CatalogItemCategory {
  UNSPECIFIED(' '),
  /** The train category */
  TRAINS('T'),
  /** The locomotive category (including steam, diesel and electric locomotives) */
  LOCOMOTIVES('L'),
  /** The passenger cars category */
  PASSENGER_CARS('P'),
  /** The freight cars category */
  FREIGHT_CARS('F');

  private char symbol;

  CatalogItemCategory(char symbol) {
    this.symbol = symbol;
  }

  char getSymbol() {
    return symbol;
  }
}
