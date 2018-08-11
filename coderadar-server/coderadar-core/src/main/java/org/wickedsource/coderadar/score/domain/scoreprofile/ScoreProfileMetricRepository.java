package org.wickedsource.coderadar.score.domain.scoreprofile;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScoreProfileMetricRepository extends CrudRepository<ScoreProfileMetric, Long> {

    ScoreProfileMetric findByName(String scoreProfileMetricName);

    void deleteByProfileId(Long id);
}
