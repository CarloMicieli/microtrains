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
package io.github.carlomicieli.domain;

import static org.assertj.core.api.Assertions.*;

import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import org.junit.jupiter.api.Test;

class AggregateRootTest {

  @Test
  void firstTest() {
    var now = Instant.now();

    var aggregate =
        TestAggregateRoot.builder()
            .id(new TestId(UUID.randomUUID()))
            .first("First value")
            .second("Second value")
            .createdDate(now)
            .modifiedDate(now)
            .version(42)
            .build();
    assertThat(aggregate).isNotNull();
    assertThat(aggregate.getId()).isNotNull();
    assertThat(aggregate.getCreatedDate()).isEqualTo(now);
    assertThat(aggregate.getModifiedDate()).isEqualTo(now);
    assertThat(aggregate.getVersion()).isEqualTo(42);
  }

  @Data
  @AllArgsConstructor
  @Builder
  @With
  static class TestAggregateRoot implements AggregateRoot<TestId> {

    private final TestId id;
    private final Instant createdDate;
    private final Instant modifiedDate;
    private final int version;
    private final String first;
    private final String second;
  }

  static class TestId implements Identifier {
    UUID value;

    TestId(UUID value) {
      this.value = value;
    }

    @Override
    public UUID toUUID() {
      return value;
    }
  }
}
