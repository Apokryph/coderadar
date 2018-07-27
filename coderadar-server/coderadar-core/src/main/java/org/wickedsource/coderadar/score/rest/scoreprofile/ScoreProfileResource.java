package org.wickedsource.coderadar.score.rest;

import org.springframework.hateoas.ResourceSupport;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreMetricDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ScoreProfileResource extends ResourceSupport {

  @NotNull private String profileName;

  @NotNull
  @Size(min = 1)
  private List<ScoreMetricDTO> metrics = new ArrayList<>();

  public String getProfileName() {
    return profileName;
  }

  public void setProfileName(String profileName) {
    this.profileName = profileName;
  }

  public List<ScoreMetricDTO> getMetrics() {
    return metrics;
  }

  public void setMetrics(List<ScoreMetricDTO> metrics) {
    this.metrics = metrics;
  }

  public void addMetric(ScoreMetricDTO metric) {
    this.metrics.add(metric);
  }
}
