package org.wickedsource.coderadar.score.rest.commitscore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Objects of this class provide parameters to query for commitscore aggregated per
 * commit. You need to provide commit name and score profile name.
 */
public class CommitScoreQuery {

    @NotNull
    private String commit;

    @NotNull
    private String profile;

    public CommitScoreQuery() {
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
