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

import static com.edevapps.jira.util.ComponentsUtil.getPropertiesManager;

public class PropertiesUtil {
    
    public static final String DATE_PICKER_JAVA_FORMAT_PROP = "jira.date.picker.java.format";
    public static final String DATE_TIME_PICKER_JAVA_FORMAT_PROP = "jira.date.time.picker.java.format";
    public static final String DATE_COMPLETE_PROP = "jira.lf.date.complete";
    
    public static String getDatePickerJavaFormat() {
        return getPropertiesManager().getPropertySet().getString(DATE_PICKER_JAVA_FORMAT_PROP);
    }
    
    public static String getDateTimePickerJavaFormat() {
        return getPropertiesManager().getPropertySet().getString(DATE_TIME_PICKER_JAVA_FORMAT_PROP);
    }

    public static String getDateCompleteFormat() {
        return getPropertiesManager().getPropertySet().getString(DATE_COMPLETE_PROP);
    }
}
