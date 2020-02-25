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

package com.edevapps.jira.web;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.atlassian.sal.api.message.I18nResolver;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class I18nParameter {

  private final Map<String, String> properties = new HashMap<>();

  public I18nParameter(I18nResolver i18nResolver, Set<String> properties) {
    fillParameters(requireNonNull(i18nResolver, "i18nResolver"),
        requireNonNull(properties, "properties"));
  }

  private void fillParameters(I18nResolver i18nResolver, Set<String> properties) {
    for(String property: properties) {
      this.properties.put(property, i18nResolver.getText(property));
    }
  }

  public String getKey() {
    return "i18n";
  }

  public String getText(String key) {
    return this.properties.get(key);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final Set<String> properties = new HashSet<>();

    public Builder addProperty(String name) {
      this.properties.add(requireNonNull(name, "name"));
      return this;
    }

    public Builder addProperties(String[] names) {
      this.properties.addAll(Arrays.asList(requireNonNull(names, "names")));
      return this;
    }

    public I18nParameter build(I18nResolver i18nResolver) {
      return new I18nParameter(i18nResolver, this.properties);
    }
  }
}
