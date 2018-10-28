/*
 *     Copyright (c) 2018, The Eduard Burenkov (http://edevapps.com)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.edevapps.jira.query;

import static com.edevapps.jira.util.ComponentsUtil.getLoggedInUser;
import static com.edevapps.jira.util.ComponentsUtil.getSearchService;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.web.bean.PagerFilter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractJqlIssueQuery implements IssueQuery {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	
	public static class Keywords {
		
		public static final String ASSIGNEE = "assignee";
		public static final String CREATED = "created";
		public static final String CHANGED = "changed";
		public static final String CLOSED = "closed";
		public static final String STATUS = "status";
		public static final String WAS = "was";
		public static final String IN = "in";
		public static final String DURING = "during";
		public static final String BY = "by";
		public static final String NOT = "not";
		public static final String BEFORE = "before";
		public static final String AFTER = "after";
		public static final String AND = "and";
		public static final String OR = "or";
		public static final String MEMBERSOF = "membersof";
		public static final String TO = "to";
		public static final String TYPE = "type";
		public static final String ISSUE_TYPE = "issuetype";
		public static final String PROJECT = "project";
		public static final String BREACHED_FUNC = "breached()";
	}
	
	protected abstract String getJql();
	
	protected SearchResults buildSearchResults() {
		SearchService searchService = getSearchService();
		com.atlassian.query.Query query = searchService
				.parseQuery(getLoggedInUser(), getJql()).getQuery();
		try {
			return searchService
					.search(getLoggedInUser(), query, PagerFilter.getUnlimitedFilter());
		} catch (SearchException e) {
			throw new QueryException(e);
		}
	}
	
	@Override
	public List<Issue> execute() {
		return buildSearchResults().getIssues();
	}
	
	protected String formatDate(Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}
}
