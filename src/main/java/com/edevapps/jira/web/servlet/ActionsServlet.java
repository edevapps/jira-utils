package com.edevapps.jira.web.servlet;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.edevapps.jira.web.servlet.ActionContext.ActionType;
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
