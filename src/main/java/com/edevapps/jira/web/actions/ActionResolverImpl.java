package com.edevapps.jira.web.actions;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.edevapps.Action;
import com.edevapps.jira.web.actions.action.InternalServerErrorAction;
import com.edevapps.jira.web.actions.action.NotFoundAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionResolverImpl implements ActionResolver {

  private static final Logger log = LoggerFactory.getLogger(ActionResolverImpl.class);

  private final Map<String, ActionFactory> actionFactories = new ConcurrentHashMap<>();
  private final boolean collectParameters;

  public ActionResolverImpl(Collection<ActionFactory> actionFactories, boolean collectParameters) {
    fillFactories(actionFactories);
    this.collectParameters = collectParameters;
  }

  @Override
  public boolean isCollectParameters() {
    return collectParameters;
  }

  private void fillFactories(Collection<ActionFactory> actionFactories) {
    requireNonNull(actionFactories, "actionFactories");
    for (ActionFactory actionFactory : actionFactories) {
      this.actionFactories.put(actionFactory.getActionKey(), actionFactory);
    }
  }

  @Override
  public boolean contains(ActionContext context) {
    requireNonNull(context, "context");
    return this.actionFactories.containsKey(context.getAction());
  }

  @Override
  public Action<Void> resolve(ActionContext context) throws IndexOutOfBoundsException {
    requireNonNull(context, "context");
    try {
      if (this.actionFactories.containsKey(context.getAction())) {
        ActionFactory actionFactory = actionFactories.get(context.getAction());
        if(this.collectParameters) {
          context.collectParameters(actionFactory.createParametersMapper());
        }
        return actionFactory.createAction();
      }
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
      return new InternalServerErrorAction(e.getMessage());
    }
    return new NotFoundAction(context.getAction());
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final List<ActionFactory> actionFactories = new ArrayList<>();
    private boolean collectParameters;

    public Builder setCollectParameters(boolean collectParameters) {
      this.collectParameters = collectParameters;
      return this;
    }

    public Builder addFactory(ActionFactory actionFactory) {
      this.actionFactories.add(requireNonNull(actionFactory, "actionFactory"));
      return this;
    }

    public ActionResolver build() {
      return new ActionResolverImpl(this.actionFactories, this.collectParameters);
    }
  }
}
