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
package io.github.carlomicieli.queries.criteria;

import io.github.carlomicieli.validation.CustomValidator;
import io.github.carlomicieli.validation.ValidationError;
import java.util.List;
import javax.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CriteriaBeanValidator<C extends Criteria>
        implements CriteriaValidator<C>, CustomValidator<C> {
    private final Validator validator;

    @Override
    public List<ValidationError> validateCriteria(C criteria) {
        return validate(criteria);
    }
}
