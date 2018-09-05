package org.wickedsource.coderadar.metricquery.rest.commit.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wickedsource.coderadar.core.rest.validation.NoMetricValuesFoundException;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueDTO;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueRepository;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileMetric;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileMetricRepository;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommitScoreService {

    private ScoreProfileMetricRepository scoreProfileMetricRepository;
    private ScoreProfileRepository scoreProfileRepository;
    private MetricValueRepository metricValueRepository;

    @Autowired
    public CommitScoreService(ScoreProfileMetricRepository scoreProfileMetricRepository, ScoreProfileRepository scoreProfileRepository, MetricValueRepository metricValueRepository) {
        this.scoreProfileMetricRepository = scoreProfileMetricRepository;
        this.scoreProfileRepository = scoreProfileRepository;
        this.metricValueRepository = metricValueRepository;
    }

    public List<ScoreValuePerCommitDTO> getScoreValuesPerCommit(List<String> scoreProfileNames, Long projectId, Integer commitSequenceNumber) {
        List<ScoreValuePerCommitDTO> scoreValuesPerCommit = new ArrayList<>();

        for (String scoreProfileName : scoreProfileNames) {

            List<ScoreProfileMetric> scoreProfileMetrics = scoreProfileRepository.findByName(scoreProfileName).getMetrics();
            List<String> scoreProfileMetricNames = new ArrayList<>();

            for (ScoreProfileMetric metric : scoreProfileMetrics) {
                scoreProfileMetricNames.add(metric.getName());
            }

            List<MetricValueDTO> profileMetricScoreValues = metricValueRepository.findValuesAggregatedByCommitAndMetric(projectId, commitSequenceNumber, scoreProfileMetricNames);
            List<Long> fileCountsInCommit = metricValueRepository.countFilesPerCommitAndMetric(projectId, commitSequenceNumber, scoreProfileMetricNames);

            if (profileMetricScoreValues.isEmpty()) {
                throw new NoMetricValuesFoundException();
            } else {
                for (int i = 0; i < profileMetricScoreValues.size(); i++) {
                    long aggregatedValue = profileMetricScoreValues.get(i).getValue();
                    long fileCount = fileCountsInCommit.get(i);
                    long result = aggregatedValue / fileCount;

                    profileMetricScoreValues.set(i, new MetricValueDTO(profileMetricScoreValues.get(i).getMetric(), result));
                }
            }

            scoreValuesPerCommit.add(new ScoreValuePerCommitDTO(scoreProfileName, getScoreValueFromMetrics(profileMetricScoreValues)));
        }

        return scoreValuesPerCommit;
    }

    private Long getScoreValueFromMetrics(List<MetricValueDTO> metrics) {
        float scoreValue = 0.0f;
        long weightCount = 0;

        for (MetricValueDTO metric : metrics) {
            ScoreProfileMetric scoreProfileMetric = scoreProfileMetricRepository.findByName(metric.getMetric());

            scoreValue = scoreValue + calculateScoreMetricValue(scoreProfileMetric, metric.getValue());
            weightCount = weightCount + scoreProfileMetric.getScoreMetricWeight();
        }

        return (long) ((scoreValue / weightCount) * 100);
    }

    private float calculateScoreMetricValue(ScoreProfileMetric metric, long value) {
        long minRange, maxRange;
        float a, b, c, scorePoints;
        switch (metric.getMetricType()) {
            case COMPLIANCE:

                minRange = metric.getScoreFailValue();
                maxRange = metric.getScoreOptimalValue();

                value = clampValue(value, minRange, maxRange);

                a = value - minRange;
                b = maxRange - minRange;
                c = a / b;

                scorePoints = c * metric.getScoreMetricWeight();

                return scorePoints;

            case VIOLATION:
                minRange = metric.getScoreOptimalValue();
                maxRange = metric.getScoreFailValue();

                value = clampValue(value, minRange, maxRange);

                a = maxRange - value;
                b = maxRange - minRange;
                c = a / b;

                scorePoints = c * metric.getScoreMetricWeight();

                return scorePoints;

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
