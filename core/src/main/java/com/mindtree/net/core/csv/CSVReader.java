package com.mindtree.net.core.csv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
			LOG.info("=====================================================================================");
		} catch (LoginException e) {
			LOG.error("Login Failed");
		}
	}

	public String getCsvFile() {
		LOG.info("=====================================================================================");
		LOG.info("CSV File reading using the one Single String function");
		try {

			Resource resource = resourceResolver.getResource("/content/dam/SlingTest/CSV/reader.csv");

			Asset asset = resource.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);
			path = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
			LOG.info(path);

			LOG.info("=====================================================================================");

		} catch (Exception e) {
			// TODO: handle exception
			LOG.info("Exception" + e);
		}
		return path;

	}

	public List<String> getPage() {

		LOG.info("=====================================================================================");
		LOG.info("CSV File reading using the Buffered reader");
		List<String> lines = new ArrayList<String>();

		LOG.info("The control is coming inside the GetPage");

		try {
			String path = "/content/dam/SlingTest/CSV/reader.csv";

			Resource resource = resourceResolver.getResource(path);

			Asset asset = resource.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			InputStream inputStream = rendition.adaptTo(InputStream.class);

			try (BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream))) {
				String line = "";
				while ((line = bufferReader.readLine()) != null) {
					LOG.info(line);
					lines.add(line);
				}

				LOG.info("=====================================================================================");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			LOG.info(e.getMessage());
		}
		return lines;

	}

}
