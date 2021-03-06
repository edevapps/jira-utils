/*
 *     Copyright (c) 2019, The Eduard Burenkov (http://edevapps.com)
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

package com.edevapps.jira.web.conditions;

import com.atlassian.plugin.PluginParseException;
import com.atlassian.plugin.web.Condition;
import com.edevapps.jira.util.ComponentsUtil;
import java.util.Map;

public class UserIsLoggedInCondition implements Condition {

  @Override
  public void init(Map<String, String> map) throws PluginParseException {
  }

  @Override
  public boolean shouldDisplay(Map<String, Object> map) {
    return ComponentsUtil.getLoggedInUser() != null;
  }
}
