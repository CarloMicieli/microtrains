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
package io.github.carlomicieli.webapi.catalog.brands.createnew;

import io.github.carlomicieli.catalog.brands.createbrand.CreateBrandOutput;
import io.github.carlomicieli.catalog.brands.createbrand.CreateBrandOutputPort;
import io.github.carlomicieli.util.Slug;
import io.github.carlomicieli.web.viewmodels.DefaultHttpResultPresenter;
import javax.inject.Singleton;

@Singleton
public final class CreateBrandPresenter extends DefaultHttpResultPresenter<CreateBrandOutput>
        implements CreateBrandOutputPort {
    @Override
    public void standard(CreateBrandOutput output) {
        created("/brands/{slug}", output.getSlug());
    }

    @Override
    public void brandAlreadyExists(Slug slug) {}
}
