package com.mindtree.net.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Via;
import org.apache.sling.models.annotations.injectorspecific.RequestAttribute;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;

import lombok.Getter;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class Testing {
	private static final Logger LOGGER = LoggerFactory.getLogger(Testing.class);

	@RequestAttribute(name = "rAttribute")
	private String reqAttribute;

	@ResourcePath(path = "/content/Test/en/Annotations")
	@Via("resource")
	Resource resource;

	@ScriptVariable
	Page currentPage;

	@Inject
	@Via("resource")
	@Named("jcr:createdBy")
	String createdBy;

	@Inject
	@Via("resource")
	String firstName;

	@ValueMapValue
	String lastName;

	@Inject
	@Via("resource")
	boolean currentEmployee;

	@ValueMapValue
	private List<String> books;

	@Inject
	@Via("resource")
	String country;

	@Inject
	@Via("resource")
	String gender;

	@ValueMapValue
	String fileReference;

	@ValueMapValue
	String pathBrowser;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean getCurrentEmployee() {
		return currentEmployee;
	}

	public String getPageTitle() {
		return currentPage.getTitle();
	}

	public String getRequestAttribute() {
		return reqAttribute;
	}

	public String getHomePageName() {
		return resource.getName();
	}

	public String getLastModifiedBy() {
		return createdBy;
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

	public String getGender() {
		return gender;
	}

	public String getPathBrowser() {
		return pathBrowser;
	}

	@PostConstruct
	public String getFileReference() {
		return fileReference;
	}

	@Getter
	private String output;

	@PostConstruct
	protected void init() {
		output = "FirstName: " + firstName + "\nLastName: " + lastName + "\nEmployee?: " + currentEmployee
				+ "\nCountry: " + country + "\nGender: " + gender;
	}

//	@PostConstruct
//	protected void init() {
//		LOGGER.info("\n Inside INIT {} : {}", currentPage.getTitle(), resource.getPath());
//
//	}
	




}
