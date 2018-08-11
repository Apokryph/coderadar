package org.wickedsource.coderadar.metricquery.rest.commit.score;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;

import java.util.Map;

public class CommitScoreOutputResource extends ResourceSupport {

    private Map<String, CommitScoreDTO> profiles;

    public CommitScoreOutputResource() {}

}
