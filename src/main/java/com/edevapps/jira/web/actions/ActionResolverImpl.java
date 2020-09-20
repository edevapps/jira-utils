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

import com.edevapps.Action;
import com.edevapps.jira.web.actions.ActionContext.ActionType;
import com.edevapps.jira.web.actions.action.InternalServerErrorAction;
import com.edevapps.jira.web.actions.action.NotFoundAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionResolverImpl implements ActionResolver {

  private static final Logger log = LoggerFactory.getLogger(ActionResolverImpl.class);

  private final Map<String, Object> parameters = new ConcurrentHashMap<>();
  private final Map<String, ActionFactory> getActionFactories = new ConcurrentHashMap<>();
  private final Map<String, ActionFactory> postActionFactories = new ConcurrentHashMap<>();
  private final Map<String, ActionFactory> putActionFactories = new ConcurrentHashMap<>();
  private final Map<String, ActionFactory> deleteActionFactories = new ConcurrentHashMap<>();

  public ActionResolverImpl(Map<String, Object> parameters,
      Collection<ActionFactory> actionFactories) {
    this.parameters.putAll(requireNonNull(parameters, "parameters"));
    fillFactories(requireNonNull(actionFactories, "actionFactories"));
  }

  public Map<String, Object> getParameters() {
    return Collections.unmodifiableMap(parameters);
  }

  @Override
  public boolean isCollectParameters() {
    return this.getActionFactories.entrySet().stream()
        .anyMatch(actionFactoryEntry -> actionFactoryEntry.getValue().isCollectParameters())
        || this.postActionFactories.entrySet().stream()
        .anyMatch(actionFactoryEntry -> actionFactoryEntry.getValue().isCollectParameters())
        || this.putActionFactories.entrySet().stream()
        .anyMatch(actionFactoryEntry -> actionFactoryEntry.getValue().isCollectParameters())
        || this.deleteActionFactories.entrySet().stream()
        .anyMatch(actionFactoryEntry -> actionFactoryEntry.getValue().isCollectParameters());
  }

  private void fillFactories(Collection<ActionFactory> actionFactories) {
    for (ActionFactory actionFactory : actionFactories) {
      if (ActionType.GET.equals(actionFactory.getActionType())) {
        this.getActionFactories.put(actionFactory.getActionKey(), actionFactory);
      } else if (ActionType.POST.equals(actionFactory.getActionType())) {
        this.postActionFactories.put(actionFactory.getActionKey(), actionFactory);
      } else if (ActionType.PUT.equals(actionFactory.getActionType())) {
        this.putActionFactories.put(actionFactory.getActionKey(), actionFactory);
      } else if (ActionType.DELETE.equals(actionFactory.getActionType())) {
        this.deleteActionFactories.put(actionFactory.getActionKey(), actionFactory);
      }
    }
  }

  @Override
  public boolean contains(ActionContext context) {
    return tryGetActionFactory(requireNonNull(context, "context")) != null;
  }

  @Override
  public Action<Void> resolve(ActionContext context) throws IndexOutOfBoundsException {
    requireNonNull(context, "context");
    try {
      ActionFactory actionFactory = tryGetActionFactory(context);
      if (actionFactory == null) {
        return new NotFoundAction(context.getAction());
      }
      if (actionFactory.isCollectParameters()) {
        context.collectParameters(actionFactory.createParametersMapper(context));
      }
      return actionFactory.createAction();
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage(), e);
      return new InternalServerErrorAction(e.getMessage());
    }
  }

  private ActionFactory tryGetActionFactory(ActionContext context) {
    ActionFactory actionFactory = null;
    if (ActionType.GET.equals(context.getActionType()) && this.getActionFactories
        .containsKey(context.getAction())) {
      actionFactory = getActionFactories.get(context.getAction());
    } else if (ActionType.POST.equals(context.getActionType()) && this.postActionFactories
        .containsKey(context.getAction())) {
      actionFactory = postActionFactories.get(context.getAction());
    } else if (ActionType.PUT.equals(context.getActionType()) && this.putActionFactories
        .containsKey(context.getAction())) {
      actionFactory = putActionFactories.get(context.getAction());
    } else if (ActionType.DELETE.equals(context.getActionType()) && this.deleteActionFactories
        .containsKey(context.getAction())) {
      actionFactory = deleteActionFactories.get(context.getAction());
    }
    return actionFactory;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private final Map<String, Object> parameters = new HashMap<>();
    private final List<ActionFactory> actionFactories = new ArrayList<>();

    public Builder putParameter(String key, Object value) {
      this.parameters.put(key, value);
      return this;
    }

    public Builder putAllParameters(Map<String, Object> parameters) {
      this.parameters.putAll(parameters);
      return this;
    }

    public Builder addFactory(ActionFactory actionFactory) {
      this.actionFactories.add(requireNonNull(actionFactory, "actionFactory"));
      return this;
    }

    public ActionResolver build() {
      return new ActionResolverImpl(this.parameters, this.actionFactories);
    }
  }
}
