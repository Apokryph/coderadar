package org.wickedsource.coderadar.score.domain.scorefilevalue;

import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "project_file_value")
public class ScoreFileValue {

  @EmbeddedId private ScoreFileValueId id;

  private Long value;

  public ScoreFileValue() {}

  public ScoreFileValue(ScoreFileValueId id, Long value) {
    this.id = id;
    this.value = value;
  }

  public Commit getCommit() {
    return id.getCommit();
  }

  public File getFile() {
    return id.getFile();
  }

  public ScoreProfile getScoreProfile() { return id.getProfile(); }

  public ScoreFileValueId getId() {
    return id;
  }

  public void setId(ScoreFileValueId id) {
    this.id = id;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
