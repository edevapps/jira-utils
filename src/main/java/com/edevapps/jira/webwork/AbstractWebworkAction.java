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

package com.edevapps.jira.webwork;

import static com.edevapps.util.StringUtil.EMPTY_STRING;
import static com.edevapps.util.StringUtil.isEmpty;

import com.atlassian.jira.web.action.JiraWebActionSupport;

public abstract class AbstractWebworkAction extends JiraWebActionSupport {

  private static final long serialVersionUID = 7013880134348098002L;

  private static final String NULL_VAL = "null";

  protected String getRequestParameter(String name) {
    String parameter = this.getHttpRequest().getParameter(name);
    if (parameter == null) {
      throw new IllegalStateException("Unknown request parameter '" + name + "'.");
    }
    return parameter;
  }

  protected String tryGetRequestParameter(String name) {
    String parameter = this.getHttpRequest().getParameter(name);
    return isEmpty(name) || NULL_VAL.equals(name)  ? EMPTY_STRING : parameter;
  }

  protected boolean containsRequestParameter(String name) {
    return this.getHttpRequest().getParameterMap().keySet().contains(name);
  }
}
