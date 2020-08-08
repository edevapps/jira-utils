/*
 * Copyright (c) 2020, The Eduard Burenkov. All rights reserved. http://edevapps.com
 */

package com.edevapps.jira.security;

public interface LicenseChecker {

  void checkLicense() throws SecurityException;

  boolean isLicensed();

  String getLicenseError();
}
