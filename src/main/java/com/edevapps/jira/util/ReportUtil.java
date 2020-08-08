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

import com.atlassian.configurable.ObjectConfigurationFactory;
import com.atlassian.configurable.ObjectDescriptor;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.plugin.report.ReportModuleDescriptor;
import com.atlassian.plugin.Plugin;
import com.edevapps.util.ReflectionUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.dom4j.Element;

public class ReportUtil {

	private static final String ELEMENT_FIELD = "element";
	private static final String OBJECT_DESCRIPTOR_FIELD = "objectDescriptor";

	private ReportUtil() {
	}

	public static void reloadReportParameters() {
		ObjectConfigurationFactory objectConfigurationFactory = ComponentAccessor
				.getComponent(ObjectConfigurationFactory.class);
		Collection<Plugin> pluginList = ComponentAccessor.getPluginAccessor().getEnabledPlugins();
		Collection<ReportModuleDescriptor> reportModuleDescriptors = new ArrayList<>();
		for (Plugin plugin : pluginList) {
			reportModuleDescriptors.addAll(getReportDescriptors(plugin));
		}
		for (ReportModuleDescriptor descriptor : reportModuleDescriptors) {
			try {
				Element element = ReflectionUtil.getFieldValue(ELEMENT_FIELD, descriptor);
				ObjectDescriptor objectDescriptor = ReflectionUtil
						.getFieldValue(OBJECT_DESCRIPTOR_FIELD, descriptor);
				objectConfigurationFactory.loadObjectConfigurationFromElement(element,
						objectDescriptor, descriptor.getCompleteKey(), descriptor.getPlugin().getClassLoader());
			}
			catch (NoSuchFieldException e) {
				// Not supported descriptor
				e.printStackTrace();
			}
			catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}

	private static List<ReportModuleDescriptor> getReportDescriptors(Plugin plugin) {
		return plugin.getModuleDescriptors().stream()
				.filter(descriptor -> descriptor instanceof ReportModuleDescriptor)
				.map(descriptor -> ((ReportModuleDescriptor) descriptor))
				.collect(Collectors.toList());
	}
}
