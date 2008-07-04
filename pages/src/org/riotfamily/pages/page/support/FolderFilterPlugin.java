/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 * 
 * The Original Code is Riot.
 * 
 * The Initial Developer of the Original Code is
 * Neteye GmbH.
 * Portions created by the Initial Developer are Copyright (C) 2006
 * the Initial Developer. All Rights Reserved.
 * 
 * Contributor(s):
 *   Felix Gnass [fgnass at neteye dot de]
 * 
 * ***** END LICENSE BLOCK ***** */
package org.riotfamily.pages.page.support;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.riotfamily.common.web.filter.FilterPlugin;
import org.riotfamily.common.web.filter.PluginChain;
import org.riotfamily.pages.page.Page;
import org.riotfamily.pages.page.PageMap;
import org.springframework.web.util.UrlPathHelper;

/**
 * @author Felix Gnass [fgnass at neteye dot de]
 * @since 6.4
 */
public class FolderFilterPlugin extends FilterPlugin {

	public static final String WEBSITE_SERVLET_SUFFIX_INIT_PARAM = 
			"webiste-servlet-suffix";
	
	public static final String DEFAULT_SERVLET_SUFFIX = ".html";
	
	private String servletSuffix = DEFAULT_SERVLET_SUFFIX;
	
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
	
	public void setServletSuffix(String servletSuffix) {
		this.servletSuffix = servletSuffix;
	}
	
	protected void initPlugin() {
		String servletSuffixParam = getServletContext().getInitParameter(
					WEBSITE_SERVLET_SUFFIX_INIT_PARAM);
		
		if (servletSuffixParam != null) {
			servletSuffix = servletSuffixParam;
		}
	}

	public void doFilter(HttpServletRequest request, 
			HttpServletResponse response, PluginChain chain) 
			throws ServletException, IOException {
	
		String uri = request.getRequestURI();
		if (uri.lastIndexOf('.') < uri.lastIndexOf('/')) {
			String path = urlPathHelper.getPathWithinApplication(request);
			PageMap pageMap = PageMap.getInstance(getServletContext());
			Page page = pageMap.getPage(path);
			if (page != null && page.isFolder()) {
				request.getRequestDispatcher(path + servletSuffix)
						.forward(request, response);
					
				return;
			}
		}
		chain.doFilter(request, response);
	}

}