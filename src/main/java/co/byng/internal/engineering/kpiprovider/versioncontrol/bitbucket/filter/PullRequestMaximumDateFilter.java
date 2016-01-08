/**
 * PullRequestMaximumDateFilter.java
 * Created 29-Dec-2015 20:11:48
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.filter;

import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequest;
import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.PullRequestFilter;
import java.util.Calendar;
import java.util.Locale;



/**
 * PullRequestMaximumDateFilter
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class PullRequestMaximumDateFilter implements PullRequestFilter<PullRequest> {

    protected final Locale locale;
    protected final Calendar maxDate;

    public PullRequestMaximumDateFilter(
        final Locale locale,
        final Calendar maxDate
    ) {
        this.locale = locale;
        this.maxDate = maxDate;
    }

    public PullRequestMaximumDateFilter(final Calendar maxDate) {
        this(Locale.getDefault(), maxDate);
    }

    @Override
    public boolean accept(PullRequest pullRequest) {
        Calendar createdDate = pullRequest.getDateCreated().toCalendar(this.locale);

        return (createdDate.before(this.maxDate));
    }
    
}
