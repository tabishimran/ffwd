/*-
 * -\-\-
 * FastForward API
 * --
 * Copyright (C) 2016 - 2018 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */

package com.spotify.ffwd.filter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.spotify.ffwd.model.v2.Metric;
import java.io.IOException;
import lombok.Data;

@Data
public class MatchKey implements Filter {

  private final String value;

  @Override
  public boolean matchesMetric(Metric metric) {
    return metric.getKey().equals(value);
  }


  public static class Deserializer implements FilterDeserializer.PartialDeserializer {

    @Override
    public Filter deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
      final String key = p.nextTextValue();

      if (p.nextToken() != JsonToken.END_ARRAY) {
        throw ctx.wrongTokenException(p, JsonToken.END_ARRAY, null);
      }

      return new MatchKey(key);
    }
  }
}
