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

package com.edevapps.jira.web.actions.action;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.edevapps.Action;
import com.edevapps.jira.web.actions.ActionContext;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotFoundAction implements Action<Void> {

  private Logger log = LoggerFactory.getLogger(NotFoundAction.class);

  private final String action;

  public NotFoundAction(String action) {
    this.action = requireNonNull(action, "action");
  }

  @Override
  public Void execute() {
    requireNonNull(action, "action");
    HttpServletResponse response = ActionContext.tryGetCurrentContext().getServletResponse();
    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    try {
      response.getWriter().write("Action '" + this.action + "' is not found.");
    } catch (IOException e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
    }
    return null;
  }
}
