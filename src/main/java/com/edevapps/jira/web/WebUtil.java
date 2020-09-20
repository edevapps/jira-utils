/*
 * Copyright (c) 2020, The Eduard Burenkov. All rights reserved. http://edevapps.com
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
