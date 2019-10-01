package com.edevapps.jira.web.servlet;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionContext {

  private static final ThreadLocal<ActionContext> currentContextThreadLocal = new  ThreadLocal<>();

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

  private static void setCurrentContext(ActionContext context) {
    currentContextThreadLocal.set(context);
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

  private static final String ACTION_PAR = "action";

  private static String getActionFromRequest(HttpServletRequest req) {
    return requireNonNull(req.getParameter(ACTION_PAR),
        "HttpServletRequest.getParameter({" + ACTION_PAR + "})");
  }

  static ActionContext createCurrentContext(ActionType actionType,
      HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
    setCurrentContext(new ActionContext(getActionFromRequest(servletRequest), actionType, servletRequest, servletResponse));
    return currentContextThreadLocal.get();
  }
}
