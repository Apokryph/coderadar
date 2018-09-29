package org.wickedsource.coderadar.score.rest.commitscore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.commit.domain.CommitRepository;
import org.wickedsource.coderadar.core.rest.validation.NoMetricValuesFoundException;
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileRepository;

import javax.validation.Valid;

/**
 * Controller for getting score per commit.
 * You need to provide commit name and score profile name into post body.
 *
 * @author Kobs
 */
@Controller
@RequestMapping(path = "/projects/{projectId}/score/perCommit")
public class CommitScoreController {

    private ProjectVerifier projectVerifier;
    private CommitRepository commitRepository;
    private ScoreProfileRepository scoreProfileRepository;
    private CommitScoreService commitScoreService;

    @Autowired
    public CommitScoreController(ProjectVerifier projectVerifier, CommitRepository commitRepository, ScoreProfileRepository scoreProfileRepository, CommitScoreService commitScoreService) {
        this.projectVerifier = projectVerifier;
        this.commitRepository = commitRepository;
        this.scoreProfileRepository = scoreProfileRepository;
        this.commitScoreService = commitScoreService;
    }

    @RequestMapping(
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "application/hal+json"
    )
    public ResponseEntity<CommitScoreOutputResource> queryScorePerCommit(@PathVariable Long projectId, @Valid @RequestBody CommitScoreQuery query) {
        projectVerifier.checkProjectExistsOrThrowException(projectId);

        Commit commit = commitRepository.findByName(query.getCommit());
        if (commit == null) {
            throw new ResourceNotFoundException();
        }
        CommitScoreOutputResource resource = new CommitScoreOutputResource();

        ScoreProfile profile = scoreProfileRepository.findByName(query.getProfile());
        if (profile == null) {
            throw new ResourceNotFoundException();
        }

        Long commitScoreValue = commitScoreService.getScoreValueFromCommitAndProfile(commit, profile);
        if (commitScoreValue == null) {
            throw new NoMetricValuesFoundException();
        }

        resource.setScoreValue(commitScoreValue);

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
