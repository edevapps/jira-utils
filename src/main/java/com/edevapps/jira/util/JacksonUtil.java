/*
 *     Copyright (c) 2020, The Eduard Burenkov (http://edevapps.com)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.edevapps.jira.util;

import static com.edevapps.util.ObjectsUtil.requireNonNull;
import static com.edevapps.util.ObjectsUtil.requireNonNullOrEmpty;

import java.io.IOException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtil {

  public static String jsonOf(Object dataObject) {
    try {
      return new ObjectMapper().writeValueAsString(
          requireNonNull(dataObject, "dataObject"));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static <T> T objectOf(String json, Class<T> objectType) {
    try {
      return new ObjectMapper().readValue(
              requireNonNullOrEmpty(json, "json"),
              requireNonNull(objectType, "objectType"));
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
