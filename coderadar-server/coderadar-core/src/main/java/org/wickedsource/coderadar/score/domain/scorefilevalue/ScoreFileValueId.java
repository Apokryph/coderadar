package org.wickedsource.coderadar.score.domain.scorefilevalue;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.file.domain.File;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Kobs
 */
@Embeddable
public class ScoreFileValueId implements Serializable {

  @OneToOne
  @JoinColumn(name = "commit_id")
  private Commit commit;

  @OneToOne
  @JoinColumn(name = "file_id")
  private File file;

  @OneToOne
  @JoinColumn(name = "score_profile_id")
  private ScoreProfile profile;

  public ScoreFileValueId() {}

  public ScoreFileValueId(Commit commit, File file, ScoreProfile profile) {
    this.commit = commit;
    this.file = file;
    this.profile = profile;
  }

  public Commit getCommit() {
    return commit;
  }

  public void setCommit(Commit commit) {
    this.commit = commit;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public ScoreProfile getProfile() { return profile; }

  public void setProfile(ScoreProfile profile) { this.profile = profile; }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof ScoreFileValueId)) {
      return false;
    }
    ScoreFileValueId that = (ScoreFileValueId) obj;
    return this.profile.equals(that.profile)
        && this.commit.getId().equals(that.commit.getId())
        && this.file.getId().equals(that.file.getId());
  }

  @Override
  public int hashCode() {
    return 17 + profile.hashCode() + commit.getId().hashCode() + file.getId().hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
