package com.mindtree.net.core.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mindtree.net.core.service.CSVAemComponent;

@Component(service = Servlet.class)
@SlingServletPaths(value = { "/bin/csvpage" })
public class CsvToAemComponentServlet extends SlingSafeMethodsServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LoggerFactory.getLogger(CsvToAemComponentServlet.class);

	@Reference
	CSVAemComponent csvAemComponent;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			out.println("Hi, Your page is created, you can check in Sites!");
			response.setContentType("text/plain");
			int pagesCreated = csvAemComponent.addPage();
			if (pagesCreated > 0) {
				LOG.info("============================================================");
				LOG.info(pagesCreated + "page is created");
				LOG.info("============================================================");
				out.println(pagesCreated + " pages are created");

			} else {
				LOG.error("Page is not created");
				out.println("page is not created");
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

}
