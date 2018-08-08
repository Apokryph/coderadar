package org.wickedsource.coderadar.score.domain.scoreprojectvalue;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.wickedsource.coderadar.metric.domain.metricvalue.*;

public interface ScoreProjectValueRepository extends CrudRepository<MetricValue, Long> {

  @Query(
      "delete from MetricValue v where v.id.commit.id in (select c.id from Commit c where c.project.id = :projectId)")
  @Modifying
  int deleteByProjectId(@Param("projectId") Long projectId);
}
