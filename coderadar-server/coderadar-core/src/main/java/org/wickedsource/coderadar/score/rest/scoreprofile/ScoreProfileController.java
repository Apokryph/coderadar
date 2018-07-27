package org.wickedsource.coderadar.score.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.project.domain.Project;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileRepository;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;

import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping(path = "/projects/{projectId}/scoreprofiles")
public class ScoreProfileController {

  private ProjectVerifier projectVerifier;

  private ScoreProfileRepository qualityProfileRepository;

  @Autowired
  public ScoreProfileController(
      ProjectVerifier projectVerifier, ScoreProfileRepository qualityProfileRepository) {
    this.projectVerifier = projectVerifier;
    this.qualityProfileRepository = qualityProfileRepository;
  }

  @RequestMapping(method = RequestMethod.POST, produces = "application/hal+json")
  public ResponseEntity<ScoreProfileResource> createQualityProfile(
      @RequestBody @Valid ScoreProfileResource qualityProfileResource,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    ScoreProfile profile = assembler.updateEntity(qualityProfileResource, new ScoreProfile());
    profile = qualityProfileRepository.save(profile);
    return new ResponseEntity<>(assembler.toResource(profile), HttpStatus.CREATED);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.POST,
    produces = "application/hal+json"
  )
  public ResponseEntity<ScoreProfileResource> updateQualityProfile(
      @Valid @RequestBody ScoreProfileResource qualityProfileResource,
      @PathVariable Long profileId,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    ScoreProfile profile = qualityProfileRepository.findOne(profileId);
    if (profile == null) {
      throw new ResourceNotFoundException();
    }
    profile = assembler.updateEntity(qualityProfileResource, profile);
    profile = qualityProfileRepository.save(profile);
    return new ResponseEntity<>(assembler.toResource(profile), HttpStatus.OK);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.GET,
    produces = "application/hal+json"
  )
  public ResponseEntity<ScoreProfileResource> getQualityProfile(
      @PathVariable Long profileId, @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    ScoreProfile profile = qualityProfileRepository.findOne(profileId);
    if (profile == null) {
      throw new ResourceNotFoundException();
    }
    return new ResponseEntity<>(assembler.toResource(profile), HttpStatus.OK);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.DELETE,
    produces = "application/hal+json"
  )
  public ResponseEntity<String> deleteQualityProfile(
      @PathVariable Long profileId, @PathVariable Long projectId) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    qualityProfileRepository.delete(profileId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<PagedResources<ScoreProfileResource>> listQualityProfiles(
      @PageableDefault Pageable pageable,
      PagedResourcesAssembler pagedResourcesAssembler,
      @PathVariable long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    Page<ScoreProfile> profilesPage =
        qualityProfileRepository.findByProjectId(projectId, pageable);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    PagedResources<ScoreProfileResource> pagedResources =
        pagedResourcesAssembler.toResource(profilesPage, assembler);
    return new ResponseEntity<>(pagedResources, HttpStatus.OK);
  }
}
