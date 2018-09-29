package org.wickedsource.coderadar.score.domain.scoreprofile;

import org.wickedsource.coderadar.project.domain.Project;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kobs
 */
@Entity
@Table(name = "score_profile")
@SequenceGenerator(
  name = "score_profile_sequence",
  sequenceName = "seq_scpr_id",
  allocationSize = 1
)
public class ScoreProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "score_profile_sequence")
  @Column(name = "id")
  private Long id;

  @OneToMany(
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    orphanRemoval = true,
    mappedBy = "profile"
  )
  private List<ScoreProfileMetric> metrics = new ArrayList<>();

  @Column(name = "name")
  private String name;

  @ManyToOne
  @JoinColumn(name = "project_id")
  private Project project;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<ScoreProfileMetric> getMetrics() {
    return metrics;
  }

  public void setMetrics(List<ScoreProfileMetric> metrics) {
    this.metrics = metrics;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }
}
