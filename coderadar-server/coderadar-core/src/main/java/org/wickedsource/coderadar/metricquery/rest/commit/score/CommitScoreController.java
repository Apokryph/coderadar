package org.wickedsource.coderadar.metricquery.rest.commit.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreMetricType;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileMetric;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileMetricRepository;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/projects/{projectId}/score/perCommit")
public class CommitScoreController {
    private ProjectVerifier projectVerifier;

    private MetricValueRepository metricValueRepository;

    private CommitRepository commitRepository;

    private ScoreProfileRepository scoreProfileRepository;

    private ScoreProfileMetricRepository scoreProfileMetricRepository;


    @Autowired
    public CommitScoreController(ProjectVerifier projectVerifier, MetricValueRepository metricValueRepository, CommitRepository commitRepository, ScoreProfileRepository scoreProfileRepository, ScoreProfileMetricRepository scoreProfileMetricRepository) {
        this.projectVerifier = projectVerifier;
        this.metricValueRepository = metricValueRepository;
        this.commitRepository = commitRepository;
        this.scoreProfileRepository = scoreProfileRepository;
        this.scoreProfileMetricRepository = scoreProfileMetricRepository;
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "application/hal+json"
    )
    public ResponseEntity<CommitScoreOutputResource> queryScorePerCommit(@PathVariable Long projectId, @Valid @RequestBody CommitScoreQuery query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);

        Commit commit = commitRepository.findByName(query.getCommit());
        if (commit == null) {
            throw new ResourceNotFoundException();
        }

        CommitScoreOutputResource resource = new CommitScoreOutputResource();

        List<String> scoreProfileNames = query.getProfiles();

        for (String scoreProfileName : scoreProfileNames) {

            List<ScoreProfileMetric> scoreProfileMetrics = scoreProfileRepository.findByName(scoreProfileName).getMetrics();
            List<String> scoreProfileMetricNames = new ArrayList<>();

            for (ScoreProfileMetric metric : scoreProfileMetrics) {
                scoreProfileMetricNames.add(metric.getName());
            }

            List<MetricValueDTO> profileMetricSccoreValues = metricValueRepository.findValuesAggregatedByCommitAndMetric(projectId, commit.getSequenceNumber(), scoreProfileMetricNames);

        }


        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    private int getScoreValueFromMetrics(List<MetricValueDTO> metrics) {
        int scoreValue = 0;
        int weightCount = 0;

        for (MetricValueDTO metric : metrics) {
            ScoreProfileMetric scoreProfileMetric = scoreProfileMetricRepository.findByName(metric.getMetric());

            scoreValue = scoreValue + calculateScoreMetricValue(scoreProfileMetric, metric.getValue());
            weightCount = weightCount + scoreProfileMetric.getScoreMetricWeight();
        }

        return scoreValue / weightCount;
    }

    private int calculateScoreMetricValue(ScoreProfileMetric metric, long value) {
        long minRange, maxRange;
        switch (metric.getMetricType()) {
            case COMPLIANCE:

                minRange = metric.getScoreFailValue();
                maxRange = metric.getScoreOptimalValue();

                value = clampValue(value, minRange, maxRange);

                return (int) (((value - minRange) / (maxRange - minRange)) * metric.getScoreMetricWeight());

            case VIOLATION:
                minRange = metric.getScoreOptimalValue();
                maxRange = metric.getScoreFailValue();

                value = clampValue(value, minRange, maxRange);

                return (int) (((maxRange - value) / (maxRange - minRange)) * metric.getScoreMetricWeight());

            default:
                throw new IllegalStateException(
                        String.format("unsupported ScoreMetricType: %s", metric.getMetricType()));
        }
    }

    private long clampValue(long value, long min, long max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }

        return value;
    }
}
