package com.edevapps.jira.web.actions;

import static com.edevapps.util.ObjectsUtil.requireNonNull;
import static com.edevapps.util.ObjectsUtil.requireNonNullOrEmpty;

import com.atlassian.webresource.api.assembler.PageBuilderService;
import com.atlassian.webresource.api.assembler.RequiredResources;
import com.edevapps.jira.web.I18nParameter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

public class TemplateRenderer {

  private final com.atlassian.templaterenderer.TemplateRenderer templateRenderer;
  private final PageBuilderService pageBuilderService;
  private final I18nParameter i18nParameter;
  private final String templateLocation;
  private final Set<String> webResources;
  private final Map<String, Object> contextProperties;

  public TemplateRenderer(String templateLocation, com.atlassian.templaterenderer.TemplateRenderer templateRenderer,
      PageBuilderService pageBuilderService, final Map<String, Object> contextProperties,
      I18nParameter i18nParameter, Collection<String> webResources) {
    this.templateLocation = requireNonNullOrEmpty(templateLocation, "templateLocation");
    this.templateRenderer = requireNonNull(templateRenderer, "templateRenderer");
    this.contextProperties = new HashMap<>(requireNonNull(contextProperties, "contextProperties"));
    this.pageBuilderService = requireNonNull(pageBuilderService, "pageBuilderService");
    this.i18nParameter = requireNonNull(i18nParameter, "i18nParameter");
    this.webResources = new HashSet<>(requireNonNull(webResources, "webResources"));
  }

  public void render(HttpServletResponse response) {
    contextProperties.put(this.i18nParameter.getKey(), this.i18nParameter);
    response.setContentType("text/html");
    final RequiredResources requiredResources = this.pageBuilderService.assembler().resources();
    this.webResources.forEach(webResource -> requiredResources.requireWebResource(webResource));

    try {
      this.templateRenderer
          .render(this.templateLocation, this.contextProperties, response.getWriter());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public static class Builder implements com.edevapps.Builder<TemplateRenderer> {

    private com.atlassian.templaterenderer.TemplateRenderer templateRenderer;
    private PageBuilderService pageBuilderService;
    private I18nParameter i18nParameter;
    private String templateLocation;
    private final Set<String> webResources = new HashSet<>();
    private final Map<String, Object> contextProperties = new HashMap<>();

    public Builder setTemplateRenderer(
        com.atlassian.templaterenderer.TemplateRenderer templateRenderer) {
      this.templateRenderer = templateRenderer;
      return this;
    }

    public Builder setPageBuilderService(
        PageBuilderService pageBuilderService) {
      this.pageBuilderService = pageBuilderService;
      return this;
    }

    public Builder setI18nParameter(I18nParameter i18nParameter) {
      this.i18nParameter = i18nParameter;
      return this;
    }

    public Builder setTemplateLocation(String templateLocation) {
      this.templateLocation = templateLocation;
      return this;
    }

    public Builder setWebResources(Collection<String> webResources) {
      this.webResources.clear();
      this.webResources.addAll(webResources);
      return this;
    }

    public Builder addWebResource(String webResource) {
      this.webResources.add(webResource);
      return this;
    }

    public Builder setContextProperties(Map<String, Object> contextProperties) {
      this.contextProperties.clear();
      this.contextProperties.putAll(contextProperties);
      return this;
    }

    public Builder addContextProperty(String key, String value) {
      this.contextProperties.clear();
      this.contextProperties.put(key, value);
      return this;
    }

    @Override
    public TemplateRenderer build() {
      return new TemplateRenderer(this.templateLocation, this.templateRenderer,
          this.pageBuilderService, this.contextProperties, this.i18nParameter, this.webResources);
    }
  }
}
