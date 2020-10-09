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

package com.edevapps.jira.util;

import static com.atlassian.jira.config.properties.APKeys.JIRA_BASEURL;
import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.atlassian.crowd.embedded.api.Group;
import com.atlassian.jira.bc.group.search.GroupPickerSearchService;
import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.bc.security.login.LoginService;
import com.atlassian.jira.bc.user.search.UserSearchParams;
import com.atlassian.jira.bc.user.search.UserSearchService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.StatusManager;
import com.atlassian.jira.config.properties.PropertiesManager;
import com.atlassian.jira.datetime.DateTimeFormatterFactory;
import com.atlassian.jira.permission.ProjectPermissions;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.security.PermissionManager;
import com.atlassian.jira.security.roles.ProjectRoleManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.user.preferences.ExtendedPreferences;
import com.atlassian.jira.user.preferences.PreferenceKeys;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    if (user == null) {
      return false;
    }
    return isUserAdministrator(user);
  }

  public static boolean isUserAdministrator(ApplicationUser user) {
    return ComponentAccessor.getGroupManager()
        .isUserInGroup(requireNonNull(user, "user"), JIRA_ADMINISTRATORS_GROUP);
  }

  public static boolean isLoggedInUserProjectAdministrator(Project project) {
    ApplicationUser user = getLoggedInUser();
    if (user == null) {
      return false;
    }
    return isUserProjectAdministrator(user, project);
  }

  public static boolean isUserProjectAdministrator(ApplicationUser user, Project project) {
    return getPermissionManager()
        .hasPermission(ProjectPermissions.ADMINISTER_PROJECTS, requireNonNull(project, "project"),
            requireNonNull(user, "user"));
  }

  public static PermissionManager getPermissionManager() {
    return ComponentAccessor.getPermissionManager();
  }

  public static ApplicationUser findUser(String value) {
    return findUsers(value).stream().findFirst().orElse(null);
  }

  public static List<ApplicationUser> findUsers(String value) {
    return Collections.unmodifiableList(new ArrayList<>(ComponentAccessor.getUserSearchService()
        .findUsers(value, new UserSearchParams(false, true, false))));
  }

  public static ApplicationUser findUserByKey(final String userKey) {
    return findAllUsers().stream()
        .filter(applicationUser -> applicationUser.getKey().equals(userKey)).findFirst()
        .orElse(null);
  }

  public static List<ApplicationUser> findAllUsers() {
    return Collections.unmodifiableList(new ArrayList<>(ComponentAccessor.getUserSearchService()
        .findUsers("", new UserSearchParams(true, true, false))));
  }

  public static List<Group> findAllGroups() {
    return Collections
        .unmodifiableList(new ArrayList<>(getGroupPickerSearchService().findGroups("")));
  }

  public static GroupPickerSearchService getGroupPickerSearchService() {
    return ComponentAccessor.getComponent(GroupPickerSearchService.class);
  }

  public static ProjectRoleManager getProjectRoleManager() {
    return ComponentAccessor.getComponent(ProjectRoleManager.class);
  }
}
