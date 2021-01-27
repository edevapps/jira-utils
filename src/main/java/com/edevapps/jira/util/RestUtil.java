/*
 * Copyright (c) 2020, The Eduard Burenkov. All rights reserved. http://edevapps.com
 */

package com.edevapps.jira.util;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class RestUtil {

  public static Response buildErrorResponse(Exception e) {
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(requireNonNull(e, "e").getMessage())
        .build();
  }
}
