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

package com.edevapps.jira.web.actions;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.edevapps.jira.web.actions.ActionContext.ActionType;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ActionsServlet extends HttpServlet {

  private static final long serialVersionUID = 3630134798903555630L;

  private transient final ActionResolver actionResolver;

  public ActionsServlet(final ActionResolver actionResolver) {
    this.actionResolver = requireNonNull(actionResolver, "actionResolver");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
    this.actionResolver.resolve(ActionContext.createCurrentContext(ActionType.GET, req, resp))
        .execute();
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
    this.actionResolver.resolve(ActionContext.createCurrentContext(ActionType.POST, req, resp))
        .execute();
  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    this.actionResolver.resolve(ActionContext.createCurrentContext(ActionType.PUT, req, resp))
        .execute();
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
    this.actionResolver.resolve(ActionContext.createCurrentContext(ActionType.DELETE, req, resp))
        .execute();
  }
}
