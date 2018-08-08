package org.wickedsource.coderadar.score.domain.scoreprofile;

import javax.persistence.*;

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
    private float scoreMetricWeight;

    @Column(name = "min_range")
    private long scoreMetricMinRange;

    @Column(name = "max_range")
    private long scoreMetricMaxRange;

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

    public float getScoreMetricWeight() {
        return scoreMetricWeight;
    }

    public void setScoreMetricWeight(float scoreMetricWeight) {
        this.scoreMetricWeight = scoreMetricWeight;
    }

    public long getScoreMetricMinRange() { return scoreMetricMinRange; }

    public void setScoreMetricMinRange(long scoreMetricMinRange) { this.scoreMetricMinRange = scoreMetricMinRange; }

    public long getScoreMetricMaxRange() { return scoreMetricMaxRange; }

    public void setScoreMetricMaxRange(long scoreMetricMaxRange) { this.scoreMetricMaxRange = scoreMetricMaxRange; }
}
