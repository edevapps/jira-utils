package com.edevapps.jira.web.actions;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ParametersMapper {

  void toMap(HttpServletRequest request, Map<String, Object> parameters);

  default void toResponse(Map<String, Object> parameters, HttpServletResponse response) { }
}
