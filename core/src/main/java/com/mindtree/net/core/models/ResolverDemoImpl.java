package com.mindtree.net.core.models;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

@Component(name = "ResolverDemo", service = ResolverDemo.class, immediate = true)
public class ResolverDemoImpl implements ResolverDemo {

	@Reference
	ResourceResolverFactory resourceResolverFactory;
	private static final Logger LOG = LoggerFactory.getLogger(ResolverDemoImpl.class);
	public static final String SERVICE_NAME = "newVariable";
	String path = null;
	String name = null;
	String modified = null;
	ResourceResolver resourceResolver = null;

	@Activate
	@Modified
	public void activate() {
		LOG.info("The control is coming inside the ResourceResolver and the bundle is activated!");
		Map<String, Object> map = new HashMap<>();
		map.put(ResourceResolverFactory.SUBSERVICE, SERVICE_NAME);
		try {
			resourceResolver = resourceResolverFactory.getServiceResourceResolver(map);

			LOG.info("Resource Resolver registered");
		} catch (LoginException e) {
			LOG.error("Login Failed");
		}
	}

	@Override
	public String getPagePath() {
		try {
			Resource resource = resourceResolver.getResource("/content/SlingTest/en");

			Page page = resource.adaptTo(Page.class);
			path = page.getPath();
			LOG.info(resource.getPath());
			LOG.info(page.getPath());

		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception" + e);
		}
		return path;

	}

	@Override
	public String getPageName() {
		try {
			Resource resource = resourceResolver.getResource("/content/SlingTest/en");

			Page page = resource.adaptTo(Page.class);
			name = page.getName();
			LOG.info(resource.getName());
			LOG.info(page.getName());

		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception" + e);
		}
		return name;
	}

	@Override
	public String getLastModified() {
		try {
			Resource resource = resourceResolver.getResource("/content/SlingTest/en");

			Page page = resource.adaptTo(Page.class);
			modified = page.getLastModifiedBy();

			LOG.info(page.getLastModifiedBy());

		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception" + e);
		}
		return modified;
	}
}
