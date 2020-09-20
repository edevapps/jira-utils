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

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ActionContext {

  private static final ThreadLocal<ActionContext> currentContextThreadLocal = new ThreadLocal<>();

  private static final String ACTION_PAR = "action";

  public enum ActionType {
    GET,
    POST,
    PUT,
    DELETE,
  }

  private final String action;
  private final ActionType actionType;
  private final HttpServletRequest servletRequest;
  private final HttpServletResponse servletResponse;
  private final Map<String, Object> parameters = new HashMap<>();

  private ActionContext(String action, ActionType actionType, HttpServletRequest servletRequest,
      HttpServletResponse servletResponse) {
    this.action = action;
    this.actionType = actionType;
    this.servletRequest = servletRequest;
    this.servletResponse = servletResponse;
  }

  public static ActionContext getCurrentContext() {
    return currentContextThreadLocal.get();
  }

  public static ActionContext tryGetCurrentContext() throws IllegalStateException {
    ActionContext.checkExist();
    return getCurrentContext();
  }

  public String getAction() {
    return action;
  }

  public ActionType getActionType() {
    return actionType;
  }

  public HttpServletRequest getServletRequest() {
    return servletRequest;
  }

  public HttpServletResponse getServletResponse() {
    return servletResponse;
  }

  public boolean addParameter(String name, Object value) {
    if (this.parameters.containsKey(name)) {
      return false;
    }
    this.parameters.put(name, value);
    return true;
  }

  public Object getParameter(String name) {
    return this.parameters.get(name);
  }

  @SuppressWarnings("unchecked")
  public <T> T getParameterAs(Class<T> type, String name) throws ClassCastException {
    return (T) this.parameters.get(name);
  }

  public void collectParameters(ParametersMapper parametersMapper) {
    requireNonNull(parametersMapper, "parametersMapper")
        .toMap(this.servletRequest, this.parameters);
  }

  public void setResponseParameters(ParametersMapper parametersMapper) {
    requireNonNull(parametersMapper, "parametersMapper")
        .toResponse(this.parameters, this.getServletResponse());
  }

  public static void checkExist() throws IllegalStateException {
    if (currentContextThreadLocal.get() == null) {
      throw new IllegalStateException("Curent action context is not exist.");
    }
  }

  public static boolean hasExist() {
    return currentContextThreadLocal.get() != null;
  }

  static ActionContext createCurrentContext(ActionType actionType,
      HttpServletRequest servletRequest,
      HttpServletResponse servletResponse) {
    currentContextThreadLocal.set(
        new ActionContext(getActionFromRequest(servletRequest), actionType, servletRequest,
            servletResponse));

    return currentContextThreadLocal.get();
  }

  private static String getActionFromRequest(HttpServletRequest req) {
    return requireNonNull(req.getParameter(ACTION_PAR),
        "HttpServletRequest.getParameter({" + ACTION_PAR + "})");
  }
}
