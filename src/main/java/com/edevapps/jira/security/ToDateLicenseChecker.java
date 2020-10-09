/*
 * Copyright (c) 2020, The Eduard Burenkov. All rights reserved. http://edevapps.com
 */

package com.edevapps.jira.security;

import static com.edevapps.util.ObjectsUtil.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ToDateLicenseChecker implements LicenseChecker {

  private final Calendar endDate;

  public ToDateLicenseChecker(Date endDate) {
    this.endDate = Calendar.getInstance();
    this.endDate.setTime(requireNonNull(endDate, "endDate"));
  }

  @Override
  public void checkLicense() throws SecurityException {
    if (!isLicensed()) {
      throw new SecurityException(getLicenseError());
    }
  }

  @Override
  public boolean isLicensed() {
    Calendar now = Calendar.getInstance();
    now.setTime(new Date());
    return this.endDate.after(now);
  }

  @Override
  public String getLicenseError() {
    if (!isLicensed()) {
      return "The license has expired, end to date:" + new SimpleDateFormat("yyyy.MM.dd")
          .format(this.endDate.getTime())
          + ".";
    }
    return "";
  }
}
