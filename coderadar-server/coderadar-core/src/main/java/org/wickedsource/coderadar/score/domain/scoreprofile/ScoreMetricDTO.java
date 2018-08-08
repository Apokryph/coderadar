package org.wickedsource.coderadar.score.domain.scoreprofile;

import javax.validation.constraints.*;

public class ScoreMetricDTO {

  private String metricName;

  private ScoreMetricType metricType;

  @Min(0)
  @Max(1)
  private float metricScoreWeight;

  private long scoreMetricMinRange;

  private long scoreMetricMaxRange;

  public ScoreMetricDTO() {}

  public ScoreMetricDTO(String metricName, ScoreMetricType metricType, float metricScoreWeight, long scoreMetricMinRange, long scoreMetricMaxRange) {
    this.metricName = metricName;
    this.metricType = metricType;
    this.metricScoreWeight = metricScoreWeight;
    this.scoreMetricMinRange = scoreMetricMinRange;
    this.scoreMetricMaxRange = scoreMetricMaxRange;
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

    public long getScoreMetricMinRange() { return scoreMetricMinRange; }

    public void setScoreMetricMinRange(long scoreMetricMinRange) { this.scoreMetricMinRange = scoreMetricMinRange; }

    public long getScoreMetricMaxRange() { return scoreMetricMaxRange; }

    public void setScoreMetricMaxRange(long scoreMetricMaxRange) { this.scoreMetricMaxRange = scoreMetricMaxRange; }
}
