package org.wickedsource.coderadar.score.rest.scoreprofile;

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

  private ScoreProfileRepository scoreProfileRepository;

  @Autowired
  public ScoreProfileController(
      ProjectVerifier projectVerifier, ScoreProfileRepository scoreProfileRepository) {
    this.projectVerifier = projectVerifier;
    this.scoreProfileRepository = scoreProfileRepository;
  }

  @RequestMapping(method = RequestMethod.POST, produces = "application/hal+json")
  public ResponseEntity<ScoreProfileResource> createScoreProfile(
      @RequestBody @Valid ScoreProfileResource scoreProfileResource,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    ScoreProfile profile = assembler.updateEntity(scoreProfileResource, new ScoreProfile());
    profile = scoreProfileRepository.save(profile);
    return new ResponseEntity<>(assembler.toResource(profile), HttpStatus.CREATED);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.POST,
    produces = "application/hal+json"
  )
  public ResponseEntity<ScoreProfileResource> updateScoreProfile(
      @Valid @RequestBody ScoreProfileResource scoreProfileResource,
      @PathVariable Long profileId,
      @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    ScoreProfile profile = scoreProfileRepository.findOne(profileId);
    if (profile == null) {
      throw new ResourceNotFoundException();
    }
    profile = assembler.updateEntity(scoreProfileResource, profile);
    profile = scoreProfileRepository.save(profile);
    return new ResponseEntity<>(assembler.toResource(profile), HttpStatus.OK);
  }

  @RequestMapping(
    path = "/{profileId}",
    method = RequestMethod.GET,
    produces = "application/hal+json"
  )
  public ResponseEntity<ScoreProfileResource> getScoreProfile(
      @PathVariable Long profileId, @PathVariable Long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    ScoreProfile profile = scoreProfileRepository.findOne(profileId);
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
  public ResponseEntity<String> deleteScoreProfile(
      @PathVariable Long profileId, @PathVariable Long projectId) {
    projectVerifier.checkProjectExistsOrThrowException(projectId);
    scoreProfileRepository.delete(profileId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
  public ResponseEntity<PagedResources<ScoreProfileResource>> listScoreProfiles(
      @PageableDefault Pageable pageable,
      PagedResourcesAssembler pagedResourcesAssembler,
      @PathVariable long projectId) {
    Project project = projectVerifier.loadProjectOrThrowException(projectId);
    Page<ScoreProfile> profilesPage =
        scoreProfileRepository.findByProjectId(projectId, pageable);
    ScoreProfileResourceAssembler assembler = new ScoreProfileResourceAssembler(project);
    PagedResources<ScoreProfileResource> pagedResources =
        pagedResourcesAssembler.toResource(profilesPage, assembler);
    return new ResponseEntity<>(pagedResources, HttpStatus.OK);
  }
}
