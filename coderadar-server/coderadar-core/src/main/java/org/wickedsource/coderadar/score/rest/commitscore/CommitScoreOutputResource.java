package org.wickedsource.coderadar.score.rest.commitscore;

import org.springframework.hateoas.ResourceSupport;

public class CommitScoreOutputResource extends ResourceSupport {

    private  Long scoreValue;

    public CommitScoreOutputResource() {}

    public Long getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Long scoreValue) {
        this.scoreValue = scoreValue;
    }
}
