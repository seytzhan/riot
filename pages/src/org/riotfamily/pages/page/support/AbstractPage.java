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

import javax.servlet.http.HttpServletRequest;

import org.riotfamily.pages.member.WebsiteMember;
import org.riotfamily.pages.page.Page;

public abstract class AbstractPage implements Page {

	private Page parent;
		
	private String pathComponent;

	private String path;
	
	private String title;
	
	private String keywords;
	
	private String description;
	
	private String controllerName;
	
	private boolean hidden;
	
	private boolean folder;
	
	private boolean published;
	
	private boolean everPublished;
	
	private int position;

	private boolean systemPage;
	
	
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public final String getControllerName() {
		return this.controllerName;
	}

	public final void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public final boolean isFolder() {
		return this.folder;
	}

	public final void setFolder(boolean folder) {
		this.folder = folder;
	}

	public final boolean isHidden() {
		return this.hidden;
	}

	public final void setHidden(boolean hidden) {
		this.hidden = hidden;
	}	

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
		if (published) {
			this.everPublished = true;
		}
	}

	public boolean isNew() {
		if (!everPublished) {
			return true;
		}
		if (parent instanceof AbstractPage) {
			AbstractPage ap = (AbstractPage) parent;
			return ap.isNew();
		}
		return false;
	}
	
	public final Page getParent() {
		return this.parent;
	}	
	
	public void setParent(Page parent) {
		this.parent = parent;
	}

	public void setParentAndUpdateChildPages(Page parent) {
		this.parent = parent;
		if (parent instanceof AbstractPage) {
			AbstractPage ap = (AbstractPage) parent;
			if (ap.isNew()) {
				setPublished(true);
			}
		}
	}

	public final String getPath() {
		if (path == null) {
			updatePath();
		}
		return path;
	}

	public final void setPath(String path) {
		this.path = path;
		if (path != null && pathComponent == null) {
			int i = path.lastIndexOf('/');
			if (i >= 0 && i < path.length() - 1) {
				pathComponent = path.substring(i + 1);
			}
		}
	}
	
	public void updatePath() {
		if (parent != null) {
			path = parent.getPath() + '/' + pathComponent;
		}
		else {
			path = '/' + pathComponent;
		}
	}

	public final String getPathComponent() {
		return this.pathComponent;
	}

	public final void setPathComponent(String pathComponent) {
		this.pathComponent = pathComponent;
	}

	public final int getPosition() {
		return position;
	}

	public final void setPosition(int position) {
		this.position = position;
	}
	
	public boolean isAccessible(HttpServletRequest request, WebsiteMember member) {
		return parent == null || parent.isAccessible(request, member);
	}

	public boolean isSystemPage() {
		return this.systemPage;
	}

	public void setSystemPage(boolean systemPage) {
		this.systemPage = systemPage;
	}
	
}