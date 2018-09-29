package org.wickedsource.coderadar.score.domain.scoreprofile;

import javax.persistence.*;

/**
 * @author Kobs
 */
@Entity
@Table(name = "score_profile_metric")
@SequenceGenerator(
        name = "score_profile_metric_sequence",
        sequenceName = "seq_spme_id",
        allocationSize = 1
)
public class ScoreProfileMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_profile_metric_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "metric_type")
    @Enumerated(EnumType.STRING)
    private ScoreMetricType metricType;

    @Column(name = "score_weight")
    private short scoreMetricWeight;

    @Column(name = "fail_value")
    private int scoreFailValue;

    @Column(name = "optimal_value")
    private int scoreOptimalValue;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ScoreProfile profile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ScoreMetricType getMetricType() {
        return metricType;
    }

    public void setMetricType(ScoreMetricType metricType) {
        this.metricType = metricType;
    }

    public ScoreProfile getProfile() {
        return profile;
    }

    public void setProfile(ScoreProfile profile) {
        this.profile = profile;
    }

    public short getScoreMetricWeight() {
        return scoreMetricWeight;
    }

    public void setScoreMetricWeight(short scoreMetricWeight) {
        this.scoreMetricWeight = scoreMetricWeight;
    }

    public int getScoreFailValue() { return scoreFailValue; }

    public void setScoreFailValue(int scoreFailValue) { this.scoreFailValue = scoreFailValue; }

    public int getScoreOptimalValue() { return scoreOptimalValue; }

    public void setScoreOptimalValue(int scoreOptimalValue) { this.scoreOptimalValue = scoreOptimalValue; }


}
