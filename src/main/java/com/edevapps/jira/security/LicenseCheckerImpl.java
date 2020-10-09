/*
 * Copyright (c) 2020, The Eduard Burenkov. All rights reserved. http://edevapps.com
 */

package com.edevapps.jira.security;

import static com.edevapps.util.ObjectsUtil.requireNonNull;
import static com.edevapps.util.StringUtil.EMPTY_STRING;

import com.atlassian.upm.api.license.PluginLicenseManager;
import com.atlassian.upm.api.license.entity.LicenseError;
import com.atlassian.upm.api.util.Option;

public class LicenseCheckerImpl implements LicenseChecker {

  private final PluginLicenseManager pluginLicenseManager;

  public LicenseCheckerImpl(final PluginLicenseManager pluginLicenseManager) {
    this.pluginLicenseManager = requireNonNull(pluginLicenseManager, "pluginLicenseManager");
  }

  @Override
  public void checkLicense() throws SecurityException {
    try {
      if (this.pluginLicenseManager.getLicense().isDefined()) {
        Option<LicenseError> licenseError = this.pluginLicenseManager.getLicense().get()
            .getError();
        if (licenseError.isDefined()) {
          throw new SecurityException("License error:" + licenseError.get().toString());
        }
      } else {
        throw new SecurityException("No license installed");
      }
    } catch (Exception ex) {
      throw new SecurityException("Error retrieving license:" + ex.getMessage());
    }
  }

  @Override
  public boolean isLicensed() {
    try {
      if (this.pluginLicenseManager.getLicense().isDefined()) {
        return !this.pluginLicenseManager.getLicense().get().getError().isDefined();
      } else {
        return false;
      }
    } catch (Exception ex) {
      return false;
    }
  }

  @Override
  public String getLicenseError() {
    try {
      if (this.pluginLicenseManager.getLicense().isDefined()) {
        Option<LicenseError> licenseError = this.pluginLicenseManager.getLicense().get().getError();
        if (licenseError.isDefined()) {
          return licenseError.get().toString();
        }
      }
      return EMPTY_STRING;
    } catch (Exception ex) {
      return ex.getMessage();
    }
  }
}
