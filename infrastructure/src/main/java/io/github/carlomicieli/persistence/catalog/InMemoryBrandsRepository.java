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
package io.github.carlomicieli.persistence.catalog;

import io.github.carlomicieli.catalog.brands.Brand;
import io.github.carlomicieli.catalog.brands.BrandId;
import io.github.carlomicieli.catalog.brands.BrandsRepository;
import io.github.carlomicieli.util.Slug;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;

@Singleton
public class InMemoryBrandsRepository implements BrandsRepository {
    private final Map<BrandId, Brand> brands;

    public InMemoryBrandsRepository() {
        brands = new ConcurrentHashMap<>();
    }

    @Override
    public BrandId add(Brand brand) {
        brands.put(brand.getId(), brand);
        return brand.getId();
    }

    @Override
    public void update(Brand brand) {
        brands.put(brand.getId(), brand);
    }

    @Override
    public void delete(BrandId id) {
        brands.remove(id);
    }

    @Override
    public Optional<Brand> getBySlug(Slug slug) {
        return brands.values().stream().filter(b -> slug.equals(b.getSlug())).findFirst();
    }

    @Override
    public boolean exists(Slug slug) {
        return brands.values().stream().anyMatch(b -> slug.equals(b.getSlug()));
    }
}
