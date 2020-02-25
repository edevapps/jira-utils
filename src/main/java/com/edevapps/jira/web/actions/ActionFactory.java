package com.edevapps.jira.web.actions;

import com.edevapps.Action;

public interface ActionFactory {

  String getActionKey();

  Action<Void> createAction();

  ParametersMapper createParametersMapper();
}
