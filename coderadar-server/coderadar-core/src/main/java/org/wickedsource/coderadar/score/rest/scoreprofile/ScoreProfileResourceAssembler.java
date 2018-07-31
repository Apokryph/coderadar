package org.wickedsource.coderadar.score.rest.scoreprofile;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectController;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreMetricDTO;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileMetric;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ScoreProfileResourceAssembler
    extends ResourceAssemblerSupport<ScoreProfile, ScoreProfileResource> {

  private Project project;

  public ScoreProfileResourceAssembler(Project project) {
    super(ScoreProfileController.class, ScoreProfileResource.class);
    this.project = project;
  }

  @Override
  public ScoreProfileResource toResource(ScoreProfile entity) {
    ScoreProfileResource resource = new ScoreProfileResource();
    resource.setProfileName(entity.getName());
    for (ScoreProfileMetric metric : entity.getMetrics()) {
      ScoreMetricDTO metricDTO = new ScoreMetricDTO();
      metricDTO.setMetricName(metric.getName());
      metricDTO.setMetricType(metric.getMetricType());
      metricDTO.setMetricScoreWeight(metric.getScoreMetricWeight());
      resource.addMetric(metricDTO);
    }
    resource.add(
        linkTo(
                methodOn(ScoreProfileController.class)
                    .getScoreProfile(entity.getId(), project.getId()))
            .withRel("self"));
    resource.add(
        linkTo(methodOn(ProjectController.class).getProject(project.getId())).withRel("project"));
    return resource;
  }

  public ScoreProfile updateEntity(ScoreProfileResource resource, ScoreProfile profile) {
    profile.setName(resource.getProfileName());
    profile.setProject(project);
    profile.getMetrics().clear();
    for (ScoreMetricDTO metricDTO : resource.getMetrics()) {
      ScoreProfileMetric metric = new ScoreProfileMetric();
      metric.setName(metricDTO.getMetricName());
      metric.setMetricType(metricDTO.getMetricType());
      metric.setScoreMetricWeight(metricDTO.getMetricScoreWeight());
      metric.setProfile(profile);
      profile.getMetrics().add(metric);
    }
    return profile;
  }
}
