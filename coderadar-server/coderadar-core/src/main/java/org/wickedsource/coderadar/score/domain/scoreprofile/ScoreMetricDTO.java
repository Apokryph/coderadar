package org.wickedsource.coderadar.score.domain.scoreprofile;

import javax.validation.constraints.*;

/**
 * @author Kobs
 */
public class ScoreMetricDTO {

  private String metricName;

  private ScoreMetricType metricType;

  @Min(1)
  @Max(3)
  private short metricScoreWeight;

  private int metricScoreFailValue;

  private int metricScoreOptimalValue;

  public ScoreMetricDTO() {}

  public ScoreMetricDTO(String metricName, ScoreMetricType metricType, short metricScoreWeight, int metricScoreFailValue, int metricScoreOptimalValue) {
    this.metricName = metricName;
    this.metricType = metricType;
    this.metricScoreWeight = metricScoreWeight;
    this.metricScoreFailValue = metricScoreFailValue;
    this.metricScoreOptimalValue = metricScoreOptimalValue;
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

  public short getMetricScoreWeight() {
    return metricScoreWeight;
  }

  public void setMetricScoreWeight(short metricScoreWeight) {
    this.metricScoreWeight = metricScoreWeight;
  }

  public int getMetricScoreFailValue() { return metricScoreFailValue; }

  public void setMetricScoreFailValue(int metricScoreFailValue) { this.metricScoreFailValue = metricScoreFailValue; }

  public int getMetricScoreOptimalValue() { return metricScoreOptimalValue; }

  public void setMetricScoreOptimalValue(int metricScoreOptimalValue) { this.metricScoreOptimalValue = metricScoreOptimalValue; }
}
