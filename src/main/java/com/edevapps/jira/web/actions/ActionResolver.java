package com.edevapps.jira.web.actions;


import com.edevapps.Action;
import com.edevapps.Resolver;

public interface ActionResolver extends Resolver<Action<Void>, ActionContext> {

  boolean isCollectParameters();
}
