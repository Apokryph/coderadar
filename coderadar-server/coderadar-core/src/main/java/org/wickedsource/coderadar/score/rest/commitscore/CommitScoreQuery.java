package org.wickedsource.coderadar.score.rest.commitscore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Objects of this class provide parameters to query for commitscore aggregated per
 * commit.
 */
public class CommitScoreQuery {

    @NotNull
    private String commit;

    @Size(min = 1)
    private List<String> profiles;

    public CommitScoreQuery () {}

    private void initProfiles() {
        if (profiles == null) {
            profiles = new ArrayList<>();
        }
    }

    public List<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<String> profiles) {
        this.profiles = profiles;
    }

    public void addProfile(String profileName) {
        initProfiles();
        this.profiles.add(profileName);
    }

    public void addProfiles(String... profileNames) {
        initProfiles();
        this.profiles.addAll(Arrays.asList(profileNames));
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }
}