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

package com.edevapps.jira.templates;

import static com.edevapps.util.ObjectsUtil.requireNonNull;
import static com.edevapps.util.ObjectsUtil.requireNonNullOrEmpty;

import com.atlassian.sal.api.message.I18nResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractI18nParameter implements I18nParameter {

  private static final long serialVersionUID = -6821516916881687098L;

  private final Map<String , String> parameters;

  public AbstractI18nParameter(I18nResolver i18nResolver) {
    this.parameters = getParameters(requireNonNull(i18nResolver, "i18nResolver"));
  }

  private Map<String , String> getParameters(I18nResolver i18nResolver) {
    Map<String , String> result = new HashMap<>();
    for(String key: getKeys()) {
      result.put(key, i18nResolver.getText(key));
    }
    return result;
  }

  @Override
  public String getText(String key) {
    requireNonNullOrEmpty(key, "key");
    if(!this.parameters.containsKey(key)) {
      return "$" + key;
    }
    return this.parameters.get(key);
  }

  protected abstract Set<String> getKeys();
}
