/**
 * PullRequestMinimumDateFilter.java
 * Created 11-Dec-2015 22:25:47
 *
 * @author M.D.Ward <matthew.ward@byng.co>
 * Copyright (c) 2015, Byng Services Ltd
 */

package co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.filter;

import co.byng.internal.engineering.kpiprovider.versioncontrol.filter.PullRequestFilter;
import co.byng.internal.engineering.kpiprovider.versioncontrol.bitbucket.model.PullRequest;
import java.util.Calendar;
import java.util.Locale;



/**
 * PullRequestMinimumDateFilter
 * 
 * @author M.D.Ward <matthew.ward@byng.co>
 */
public class PullRequestMinimumDateFilter implements PullRequestFilter<PullRequest> {

    protected Locale locale;
    protected Calendar minDate;



    public PullRequestMinimumDateFilter(Calendar minDate, Locale locale) {
        this.minDate = minDate;
        this.locale = locale;
    }
    
    public PullRequestMinimumDateFilter(Calendar minDate) {
        this(minDate, Locale.getDefault());
    }
    
    @Override
    public boolean accept(PullRequest pullRequest) {
        Calendar createdDate = pullRequest.getDateCreated().toCalendar(this.locale);
        
        return (createdDate.after(this.minDate) || createdDate.equals(this.minDate));
    }

}
