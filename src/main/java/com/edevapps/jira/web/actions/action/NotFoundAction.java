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
