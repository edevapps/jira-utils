/*
 *     Copyright (c) 2018, The Eduard Burenkov (http://edevapps.com)
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

package com.edevapps.jira.util;

import static com.atlassian.jira.config.properties.APKeys.JIRA_BASEURL;
import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.atlassian.jira.bc.group.search.GroupPickerSearchService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.bc.security.login.LoginService;
import com.atlassian.jira.bc.user.search.UserSearchParams;
import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.StatusManager;
import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.preferences.ExtendedPreferences;
import com.atlassian.jira.user.preferences.PreferenceKeys;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentsUtil {

  public static final String JIRA_ADMINISTRATORS_GROUP = "jira-administrators";

  public static String getBaseUrl() {
    return ComponentAccessor.getApplicationProperties().getString(JIRA_BASEURL);
  }

  public static SearchService getSearchService() {
    return ComponentAccessor.getComponent(SearchService.class);
  }

  public static ApplicationUser getLoggedInUser() {
    return ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
  }

  public static DateTimeFormatterFactory getDateTimeFormatterFactory() {
    return ComponentAccessor.getComponent(DateTimeFormatterFactory.class);
  }

  public static PropertiesManager getPropertiesManager() {
    return ComponentAccessor.getComponent(PropertiesManager.class);
  }

  public static ProjectManager getProjectManager() {
    return ComponentAccessor.getComponent(ProjectManager.class);
  }

  public static LoginService getLoginService() {
    return ComponentAccessor.getComponent(LoginService.class);
  }

  public static StatusManager getStatusManager() {
    return ComponentAccessor.getComponent(StatusManager.class);
  }

  public static UserSearchService getUserSearchService() {
    return ComponentAccessor.getComponent(UserSearchService.class);
  }

  public static ExtendedPreferences getLoggedInUserPreferences() {
    return ComponentAccessor.getUserPreferencesManager()
        .getExtendedPreferences(ComponentsUtil.getLoggedInUser());
  }

  public static String getLoggedInUserLocale() {
    return getLoggedInUserPreferences().getString(PreferenceKeys.USER_LOCALE);
  }

  public static String tryGetLoggedInUserLocale() {
    try {
      return getLoggedInUserPreferences().getString(PreferenceKeys.USER_LOCALE);
    } catch (Exception e) {
      return null;
    }
  }

  public static boolean isLoggedInUserAdministrator() {
    ApplicationUser user = getLoggedInUser();
    if(user == null) {
      return false;
    }
    return isUserAdministrator(user);
  }

  public static boolean isUserAdministrator(ApplicationUser user) {
      return ComponentAccessor.getGroupManager()
        .isUserInGroup(requireNonNull(user, "user"), JIRA_ADMINISTRATORS_GROUP);
  }

  public static PermissionManager getPermissionManager() {
    return ComponentAccessor.getComponent(PermissionManager.class);
  }

  public static List<ApplicationUser> findAllUsers() {
    return new ArrayList<>(ComponentAccessor.getUserSearchService()
        .findUsers("", new UserSearchParams(true, true, false)));
  }

  public static GroupPickerSearchService getGroupPickerSearchService() {
    return ComponentAccessor.getComponent(GroupPickerSearchService.class);
  }
}
