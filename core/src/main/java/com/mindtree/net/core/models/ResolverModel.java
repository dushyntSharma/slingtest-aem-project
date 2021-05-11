package com.mindtree.net.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ResolverModel {

	@OSGiService
	ResolverDemo resolverDemo;

	public String getPagePath() {
		return resolverDemo.getPagePath();
	}

	public String getPageName() {
		return resolverDemo.getPageName();
	}

	public String getLastModified() {
		return resolverDemo.getLastModified();
	}

}
