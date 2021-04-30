package com.mindtree.net.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class OSGiConfigImpl implements OSGiConfig {

	@OSGiService
	OSGiConfig oSGiConfig;

	@Override
	public String getServiceName() {
		return oSGiConfig.getServiceName();
	}

	@Override
	public int getServiceCount() {
		return oSGiConfig.getServiceCount();
	}

	@Override
	public boolean isLiveData() {
		return oSGiConfig.isLiveData();
	}

	@Override
	public String[] getCountries() {
		return oSGiConfig.getCountries();
	}

	@Override
	public String getRunModes() {
		return oSGiConfig.getRunModes();
	}

}
