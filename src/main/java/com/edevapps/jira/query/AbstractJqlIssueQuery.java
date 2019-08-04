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

import static com.edevapps.jira.util.ComponentsUtil.getSearchService;
import static java.util.Objects.requireNonNull;

import com.atlassian.jira.bc.issue.search.SearchService;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.search.SearchException;
import com.atlassian.jira.issue.search.SearchResults;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.web.bean.PagerFilter;
import com.atlassian.query.Query;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class AbstractJqlIssueQuery implements IssueQuery {
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

	private final SearchService searchService = getSearchService();
	private final ApplicationUser applicationUser;

	public AbstractJqlIssueQuery(ApplicationUser applicationUser) {
		this.applicationUser = requireNonNull(applicationUser, "applicationUser");
	}

	protected Query parseQuery(String jql) {
		return this.searchService.parseQuery(this.applicationUser, jql).getQuery();
	}

	protected abstract Query getQuery();

	private SearchResults<Issue> buildSearchResults() throws IllegalStateException, QueryException {
		try {
			return this.searchService.search(this.applicationUser, getQuery(), PagerFilter.getUnlimitedFilter());
		} catch (SearchException e) {
			throw new QueryException(e);
		}
	}

	@Override
	public List<Issue> execute() {
		return buildSearchResults().getResults();
	}

	protected String formatDate(Date date) {
		return new SimpleDateFormat(DATE_FORMAT).format(date);
	}
}
