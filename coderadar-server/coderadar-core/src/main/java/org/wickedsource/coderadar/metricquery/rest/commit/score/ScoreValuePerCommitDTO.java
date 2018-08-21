package org.wickedsource.coderadar.metricquery.rest.commit.score;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ScoreValuePerCommitDTO {

    @JsonIgnore public String profile;
    public Long value;

    public ScoreValuePerCommitDTO() {
    }

    public ScoreValuePerCommitDTO(String profile, Long value) {
        this.profile = profile;
        this.value = value;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
