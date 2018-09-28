package org.wickedsource.coderadar.score.domain.scorefilevalue;

import org.wickedsource.coderadar.analyzer.api.FileMetrics;
import org.wickedsource.coderadar.analyzer.api.Metric;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValue;
import org.wickedsource.coderadar.metric.domain.metricvalue.MetricValueId;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileMetric;

import java.util.List;

public class ScoreFileService {

    /*private long clampValue(long value, long min, long max) {
        if (value > max) {
            return max;
        }
        if (value < min) {
            return min;
        }

        return value;
    }

    private void storeFileScoreValues(Commit commit, String filePath, FileMetrics metrics) {

        List<ScoreProfile> scoreProfiles = scoreProfileRepository.findByProjectId(commit.getProject().getId());
        File file = fileRepository.findInCommit(filePath, commit.getName(), commit.getProject().getId());

        if (scoreProfiles == null || scoreProfiles.isEmpty()) {
            throw new IllegalStateException(
                    String.format(
                            "could not find scoreProfiles in project %s",
                            commit.getProject().getName()));
        }

        for (ScoreProfile scoreProfile : scoreProfiles) {
            List<ScoreProfileMetric> scoreProfileMetrics = scoreProfile.getMetrics();
            float fileScore = 0.0f;
            int weightCount = 0;

            for (ScoreProfileMetric scoreProfileMetric : scoreProfileMetrics) {

                for (Metric metric : metrics.getMetrics()) {
                    MetricValueId id = new MetricValueId(commit, file, metric.getId());
                    MetricValue metricValue = new MetricValue(id, metrics.getMetricCount(metric));

                    if (metricValue.getMetricName().equals(scoreProfileMetric.getName())) {
                        float scorePoints, a, b, c;
                        long minRange, maxRange;
                        long value = metricValue.getValue();

                        switch (scoreProfileMetric.getMetricType()) {
                            case COMPLIANCE:

                                minRange = scoreProfileMetric.getScoreFailValue();
                                maxRange = scoreProfileMetric.getScoreOptimalValue();

                                value = clampValue(value, minRange, maxRange);

                                a = value - minRange;
                                b = maxRange - minRange;
                                c = a / b;
                                scorePoints = c * scoreProfileMetric.getScoreMetricWeight();

                                //scorePoints = ((float)(value - minRange) / (float)(maxRange - minRange)) * scoreProfileMetric.getScoreMetricWeight();
                                fileScore = fileScore + scorePoints;
                                break;

                            case VIOLATION:
                                minRange = scoreProfileMetric.getScoreOptimalValue();
                                maxRange = scoreProfileMetric.getScoreFailValue();

                                value = clampValue(value, minRange, maxRange);

                                a = maxRange - value;
                                b = maxRange - minRange;
                                c = a / b;
                                scorePoints = c * scoreProfileMetric.getScoreMetricWeight();
                                //scorePoints = ((float)(maxRange - value) / (float)(maxRange - minRange)) * scoreProfileMetric.getScoreMetricWeight();
                                fileScore = fileScore + scorePoints;
                                break;

                            default:
                                throw new IllegalStateException(
                                        String.format("unsupported ScoreMetricType: %s", scoreProfileMetric.getMetricType()));
                        }

                        weightCount = weightCount + scoreProfileMetric.getScoreMetricWeight();
                    }
                }
            }

            if (fileScore != 0.0f) {
                fileScore = fileScore / weightCount * 100;
            }

            ScoreFileValueId id = new ScoreFileValueId(commit, file, scoreProfile);
            ScoreFileValue fileValue = new ScoreFileValue(id, (long) fileScore);
            scoreFileValueRepository.save(fileValue);

            logger.info(
                    "stored commitscore for file {} in commit {} for profile {}",
                    id,
                    commit.getId(),
                    scoreProfile.getName());
        }
    }*/
}
