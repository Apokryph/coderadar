package org.wickedsource.coderadar.metricquery.rest.commit.score;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CommitScoreOutputResource extends ResourceSupport {

    private Map<String, Long> profiles;

    public CommitScoreOutputResource() {}

    @JsonIgnore
    public void addScoreValues(List<ScoreValuePerCommitDTO> scoreValuesPerCommit) {
        initProfiles();
        for(ScoreValuePerCommitDTO scoreValue : scoreValuesPerCommit) {
            String profile = scoreValue.getProfile();
            Long score = scoreValue.getValue();

            addScoreValue(profile,score);
        }
    }

    private void initProfiles() {
        if (profiles == null) {
            profiles = new HashMap<>();
        }
    }

    @JsonIgnore
    public Long getScoreValue(String profile) {
        if (profiles == null) {
            return null;
        }
        return profiles.get(profile);
    }

    @JsonIgnore
    public void addScoreValue (String profile, Long value) {
        profiles.put(profile, value);
    }

    public Map<String, Long> getProfiles() {
        return profiles;
    }

}
