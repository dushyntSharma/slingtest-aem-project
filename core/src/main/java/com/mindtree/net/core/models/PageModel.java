package com.mindtree.net.core.models;

public class PageModel {
	private String pageParent;
	private String pageName;
	private String pageTemplate;
	private String pageTitle;

	public PageModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PageModel(String pageParent, String pageName, String pageTemplate, String pageTitle) {
		super();
		this.pageParent = pageParent;
		this.pageName = pageName;
		this.pageTemplate = pageTemplate;
		this.pageTitle = pageTitle;
	}

	public String getPageParent() {
		return pageParent;
	}

	public void setPageParent(String pageParent) {
		this.pageParent = pageParent;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageTemplate() {
		return pageTemplate;
	}

	public void setPageTemplate(String pageTemplate) {
		this.pageTemplate = pageTemplate;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

}
