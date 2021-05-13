package com.mindtree.net.core.csv;

import java.util.List;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CSVModel {

	@OSGiService
	CSVReader csvReader;

	public String getPagePath() {
		return csvReader.getCsvFile();
	}

	public List<String> getPage() {
		return csvReader.getPage();
	}

}
