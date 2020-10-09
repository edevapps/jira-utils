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

package com.edevapps.jira.configuration.settings;

import static com.edevapps.util.ObjectsUtil.requireNonNull;
import static com.edevapps.util.ObjectsUtil.requireNonNullOrEmpty;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.transaction.TransactionTemplate;

public class AppSettingsImpl implements AppSettings {

  private final PluginSettingsFactory pluginSettingsFactory;
  private final TransactionTemplate transactionTemplate;

  public AppSettingsImpl(
      PluginSettingsFactory pluginSettingsFactory,
      TransactionTemplate transactionTemplate) {
    this.pluginSettingsFactory = requireNonNull(pluginSettingsFactory, "pluginSettingsFactory");
    this.transactionTemplate = requireNonNull(transactionTemplate, "transactionTemplate");
  }

  @Override
  public void setString(String key, String value) {
    setProperty(key, value);
  }

  @Override
  public String getString(String key) {
    Object value = getProperties().get(requireNonNullOrEmpty(key, "key"));
    return value == null ? null : value.toString();
  }

  protected PluginSettings getProperties() {
    return pluginSettingsFactory.createGlobalSettings();
  }

  protected void setProperty(final String key, final Object value) {
    requireNonNullOrEmpty(key, "key");
    transactionTemplate.execute(() -> {
      getProperties().put(key, value);
      return null;
    });
  }
}
