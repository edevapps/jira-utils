package com.edevapps.jira.web.actions.action;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.edevapps.Action;
import com.edevapps.jira.web.actions.ActionContext;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InternalServerErrorAction implements Action<Void> {

  private static final Logger log = LoggerFactory.getLogger(InternalServerErrorAction.class);

  private final String error;

  public InternalServerErrorAction(String error) {
    this.error = requireNonNull(error, "error");
  }

  @Override
  public Void execute() {
    HttpServletResponse response = ActionContext.tryGetCurrentContext().getServletResponse();
    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    try {
      response.getWriter().write("Error '" + this.error + "'.");
    } catch (IOException e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
    }
    return null;
  }
}
