package org.wickedsource.coderadar.score.domain.scoreprofile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author Kobs
 */
public interface ScoreProfileRepository extends CrudRepository<ScoreProfile, Long> {

  ScoreProfile findByName(String scoreProfileName);

  List<ScoreProfile> findByProjectId(Long projectId);

  Page<ScoreProfile> findByProjectId(Long projectId, Pageable pageable);

  int deleteByProjectId(Long id);
}
