package com.mindtree.net.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CodeChallenge {

	@Inject
	@Via("resource")
	boolean currentEmployee;

	@ValueMapValue
	private List<String> books;

	@Inject
	@Via("resource")
	String country;

	@ValueMapValue
	String fileReference;

	@ValueMapValue
	String pathBrowser;

	public boolean getCurrentEmployee() {
		return currentEmployee;
	}

	public List<String> getAuthBooks() {
		if (books != null) {
			return new ArrayList<String>(books);
		} else {
			return Collections.emptyList();
		}
	}

	public String getCountry() {
		return country;
	}

	public String getPathBrowser() {
		return pathBrowser;
	}

	@PostConstruct
	public String getFileReference() {
		return fileReference;
	}
}
