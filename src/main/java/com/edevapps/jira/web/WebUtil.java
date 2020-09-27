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

package com.edevapps.jira.web;

import static com.edevapps.util.ObjectsUtil.requireNonNull;
import static com.edevapps.util.ObjectsUtil.requireNonNullOrEmpty;
import static com.edevapps.util.StringUtil.toStrings;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

public class WebUtil {

  public static final String DELIMITER = ",";

  public static String getRequestParameter(String name, HttpServletRequest request) {
    String parameter = requireNonNull(request, "request")
        .getParameter(requireNonNullOrEmpty(name, "name"));
    if (parameter == null) {
      throw new IllegalStateException("Unknown request parameter '" + name + "'.");
    }
    return parameter;
  }

  public static String tryGetRequestParameter(String name, HttpServletRequest request) {
    return requireNonNull(request, "request")
        .getParameter(requireNonNullOrEmpty(name, "name"));
  }

  public static boolean containsParameter(String key, HttpServletRequest request) {
    return requireNonNull(request, "request").getParameter(key) != null;
  }

  public static Collection<Long> getIdCollectionFromParameter(String key,
      HttpServletRequest request) {
    return toStrings(
        getRequestParameter(key, request), DELIMITER).stream()
        .map(Long::valueOf).collect(Collectors.toList());
  }

  public static Collection<String> getStringCollectionFromParameter(String key,
      HttpServletRequest request) {
    return new ArrayList<>(toStrings(getRequestParameter(key, request), DELIMITER));
  }
}
