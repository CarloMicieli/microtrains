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
package io.github.carlomicieli.catalog.brands;

// The kinds for railway models brands
public enum BrandKind {
  // these manufactures produce models using the die casting method
  INDUSTRIAL,

  // these manufacturers produce models which are made of brass or similar alloys.
  //
  // They are usually more expensive than the industrial series due to the limited
  // production quantities and the "hand made" nature of the production
  BRASS_MODELS
}
