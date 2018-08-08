package org.wickedsource.coderadar.score.domain.scorefilevalue;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ScoreFileValueRepository extends CrudRepository<ScoreFileValue, Long> {

    @Query(
            "delete from ScoreFileValue v where v.id.commit.id in (select c.id from Commit c where c.project.id = :projectId)")
    @Modifying
    int deleteByProjectId(@Param("projectId") Long projectId);
}
