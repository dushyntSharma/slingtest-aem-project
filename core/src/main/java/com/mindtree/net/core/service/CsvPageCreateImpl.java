package com.mindtree.net.core.service;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.day.cq.wcm.api.WCMException;
import com.mindtree.net.core.models.PageModel;

@Component(service = CsvPageCreate.class, immediate = true)
public class CsvPageCreateImpl implements CsvPageCreate {

	@Reference
	ResourceResolverFactory resourceResolverFactory;

	private static final Logger LOG = LoggerFactory.getLogger(CsvPageCreateImpl.class);

	public static final String SERVICE_NAME = "newVariable";

	public static final String RESOURCE_PATH = "/content/dam/SlingTest/CSV/CsvPage.csv";

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

	public List<PageModel> getCsvContent() {

		List<PageModel> pageProperties = null;

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader bufferedReader = null;

		try {
			Resource resource = resourceResolver.getResource(RESOURCE_PATH);
			LOG.info("resource is coming");
			Asset asset = resource.adaptTo(Asset.class);
			Rendition rendition = asset.getOriginal();
			inputStream = rendition.adaptTo(InputStream.class);
			inputStreamReader = new InputStreamReader(inputStream);
			bufferedReader = new BufferedReader(inputStreamReader);

			pageProperties = new LinkedList<>(); // importance of linkedlist over here

			pageProperties = bufferedReader.lines().skip(1).map(singleLine -> {
				String[] arr = singleLine.split(",");
				PageModel pageModel = new PageModel();
				pageModel.setPageParent(arr[3].trim());
				pageModel.setPageName(arr[0].trim());
				pageModel.setPageTemplate(arr[1].trim());
				pageModel.setPageTitle(arr[2].trim());
				return pageModel;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			LOG.error("We failed to get the CSV datas");
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
				if (inputStreamReader != null)
					inputStreamReader.close();
				if (inputStream != null)
					inputStream.close();
			} catch (Exception e) {
				LOG.error("Resources could not be released properly");
			}
		}

		return pageProperties;
	}

//	public static final String PARENT_PATH = "/content/workFlowProject/en";
//	public static final String PAGE_NAME = "CsvPage";
//	public static final String WHICH_TEMPLATE = "/conf/workFlowProject/settings/wcm/templates";
//	public static final String PAGE_TITLE = "CsvPageCretion";

	@Override
	public List<Page> createPage() {
		List<Page> pagesCreated = new LinkedList<>();
		List<PageModel> pageProperties = getCsvContent(); // excluded the csv for now
		LOG.info("******************        Hi i am comming from create Page *******************************");

		PageManager pageManager = resourceResolver.adaptTo(PageManager.class);
		Page page;

		try {
			for (PageModel pageModel : pageProperties) {

				LOG.info("*+=+++++++=+=++++++++ hi iam from imple try block ++===============***");
				LOG.info("===============================");
				LOG.info(pageModel.getPageName());
				LOG.info(pageModel.getPageParent());
				LOG.info(pageModel.getPageTemplate());
				LOG.info(pageModel.getPageTitle());
				LOG.info("===============================");

				Page page1 = pageManager.create(pageModel.getPageParent(), pageModel.getPageName(),
						pageModel.getPageTemplate(), pageModel.getPageTitle());

				LOG.info("*+=+++++++=+=++++++++ hi iam from imple try block after create page  ++===============***");
				if (page1 != null) {
					pagesCreated.add(page1);
				}
			}
			return pagesCreated;
		} catch (WCMException e) {
			LOG.error("Page not created");
		}
		return Collections.emptyList();

	}

}
