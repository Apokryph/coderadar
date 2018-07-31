package org.wickedsource.coderadar.score.domain.scoreprofile;

import org.springframework.data.repository.CrudRepository;

public interface ScoreProfileMetricRepository extends CrudRepository<ScoreProfileMetric, Long> {

    void deleteByProfileId(Long id);
}
