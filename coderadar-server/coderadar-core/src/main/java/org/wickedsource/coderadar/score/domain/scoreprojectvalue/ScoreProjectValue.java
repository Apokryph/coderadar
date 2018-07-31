package org.wickedsource.coderadar.score.domain.scoreprojectvalue;

import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "score_project_value")
public class ScoreProjectValue {

  @EmbeddedId private ScoreProjectValueId id;

  private Long value;

  public ScoreProjectValue() {}

  public ScoreProjectValue(ScoreProjectValueId id, Long value) {
    this.id = id;
    this.value = value;
  }

  public Commit getCommit() {
    return id.getCommit();
  }

  public Project getProject() {
    return id.getProject();
  }

  public ScoreProfile getProfile() { return id.getProfile(); }

  public ScoreProjectValueId getId() {
    return id;
  }

  public void setId(ScoreProjectValueId id) {
    this.id = id;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
