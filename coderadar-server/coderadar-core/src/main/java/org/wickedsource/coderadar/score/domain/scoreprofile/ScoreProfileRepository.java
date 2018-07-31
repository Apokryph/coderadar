package org.wickedsource.coderadar.score.domain.scoreprofile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface ScoreProfileRepository extends CrudRepository<ScoreProfile, Long> {

  Page<ScoreProfile> findByProjectId(Long projectId, Pageable pageable);
}
