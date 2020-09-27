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

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.util.I18nHelper;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public abstract class AbstractI18nHelper implements I18nHelper {

  private final I18nHelper i18nHelper;

  public AbstractI18nHelper(final JiraAuthenticationContext jiraAuthenticationContext) {
    this.i18nHelper = requireNonNull(jiraAuthenticationContext, "jiraAuthenticationContext")
        .getI18nHelper();
  }

  @Override
  public Locale getLocale() {
    return this.i18nHelper.getLocale();
  }

  @Override
  public ResourceBundle getDefaultResourceBundle() {
    return this.i18nHelper.getDefaultResourceBundle();
  }

  @Override
  public String getUnescapedText(String s) {
    return this.i18nHelper.getUnescapedText(s);
  }

  @Override
  public String getUntransformedRawText(String s) {
    return this.i18nHelper.getUntransformedRawText(s);
  }

  @Override
  public boolean isKeyDefined(String s) {
    return this.i18nHelper.isKeyDefined(s);
  }

  @Override
  public String getText(String s) {
    return this.i18nHelper.getText(s);
  }

  @Override
  public String getText(String s, String s1) {
    return this.i18nHelper.getText(s, s1);
  }

  @Override
  public String getText(String s, String s1, String s2) {
    return this.i18nHelper.getText(s, s1, s2);
  }

  @Override
  public String getText(String s, String s1, String s2, String s3) {
    return this.i18nHelper.getText(s, s1, s2, s3);
  }

  @Override
  public String getText(String s, String s1, String s2, String s3, String s4) {
    return this.i18nHelper.getText(s, s1, s2, s3, s4);
  }

  @Override
  public String getText(String s, Object o, Object o1, Object o2) {
    return this.i18nHelper.getText(s, o, o1, o2);
  }

  @Override
  public String getText(String s, Object o, Object o1, Object o2, Object o3) {
    return this.i18nHelper.getText(s, o, o1, o2, o3);
  }

  @Override
  public String getText(String s, Object o, Object o1, Object o2, Object o3, Object o4) {
    return this.i18nHelper.getText(s, o, o1, o2, o3, o4);
  }

  @Override
  public String getText(String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5) {
    return this.i18nHelper.getText(s, o, o1, o2, o3, o4, o5);
  }

  @Override
  public String getText(String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5,
      Object o6) {
    return this.i18nHelper.getText(s, o, o1, o2, o3, o4, o5, o6);
  }

  @Override
  public String getText(String s, String s1, String s2, String s3, String s4, String s5, String s6,
      String s7) {
    return this.i18nHelper.getText(s, s1, s2, s3, s4, s5, s6, s7);
  }

  @Override
  public String getText(String s, Object o, Object o1, Object o2, Object o3, Object o4, Object o5,
      Object o6, Object o7) {
    return this.i18nHelper.getText(s, o, o1, o2, o3, o4, o5, o6, o7);
  }

  @Override
  public String getText(String s, String s1, String s2, String s3, String s4, String s5, String s6,
      String s7, String s8, String s9) {
    return this.i18nHelper.getText(s, s1, s2, s3, s4, s5, s6, s7, s8, s9);
  }

  @Override
  public String getText(String s, Object o) {
    return this.i18nHelper.getText(s, o);
  }

  @Override
  public Set<String> getKeysForPrefix(String s) {
    return this.i18nHelper.getKeysForPrefix(s);
  }

  @Override
  public ResourceBundle getResourceBundle() {
    return this.i18nHelper.getResourceBundle();
  }
}
