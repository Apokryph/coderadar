package org.wickedsource.coderadar.score.domain;

import javax.validation.constraints.*;

public class ScoreMetricDTO {

  private String metricName;

  private ScoreMetricType metricType;

  @Min(0)
  @Max(1)
  private float metricScoreWeight;
  public ScoreMetricDTO() {}

  public ScoreMetricDTO(String metricName, ScoreMetricType metricType,float metricScoreWeight) {
    this.metricName = metricName;
    this.metricType = metricType;
    this.metricScoreWeight = metricScoreWeight;
  }

  public String getMetricName() {
    return metricName;
  }

  public void setMetricName(String metricName) {
    this.metricName = metricName;
  }

  public ScoreMetricType getMetricType() {
    return metricType;
  }

  public void setMetricType(ScoreMetricType metricType) {
    this.metricType = metricType;
  }

  public float getMetricScoreWeight() {
    return metricScoreWeight;
  }

  public void setMetricScoreWeight(float metricScoreWeight) {
    this.metricScoreWeight = metricScoreWeight;
  }
}
