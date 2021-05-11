package com.mindtree.net.core.csv;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
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

import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;

@Component(service = CSVReader.class, immediate = true)
public class CSVReader {

	@Reference
	ResourceResolverFactory resourceResolverFactory;
	private static final Logger LOG = LoggerFactory.getLogger(CSVReader.class);
	public static final String SERVICE_NAME = "newVariable";
	String path = null;

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

	public String getCsvFile() {
		try {

			Resource resource = resourceResolver.getResource("/content/dam/SlingTest/CSV/sample2.csv");

			Asset asset = resource.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);
			path = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			LOG.info(path);

		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception" + e);
		}
		return path;

	}

}
