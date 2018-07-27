package org.wickedsource.coderadar.score.domain.scoreprojectvalue;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;
import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ScoreProjectValueId implements Serializable {

  @OneToOne
  @JoinColumn(name = "profile_id")
  private ScoreProfile profile;

  @OneToOne
  @JoinColumn(name = "project_id")
  private Project project;


  public ScoreProjectValueId() {}

  public ScoreProjectValueId(Project project, ScoreProfile profile) {
    this.profile = profile;
    this.project = project;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public ScoreProfile getProfile() { return profile; }

  public void setProfile(ScoreProfile profile) { this.profile = profile; }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !(obj instanceof ScoreProjectValueId)) {
      return false;
    }
    ScoreProjectValueId that = (ScoreProjectValueId) obj;
    return this.project.getId().equals(that.project.getId())
        && this.profile.getId().equals(that.profile.getId());
  }

  @Override
  public int hashCode() {
    return 17 + project.getId().hashCode() + profile.getId().hashCode();
  }

  @Override
  public String toString() {
    return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
  }
}
