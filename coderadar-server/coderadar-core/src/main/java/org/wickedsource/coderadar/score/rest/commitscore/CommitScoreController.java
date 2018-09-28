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
import org.wickedsource.coderadar.core.rest.validation.ResourceNotFoundException;
import org.wickedsource.coderadar.project.rest.ProjectVerifier;
import javax.validation.Valid;

import java.util.List;

@Controller
@RequestMapping(path = "/projects/{projectId}/score/perCommit")
public class CommitScoreController {

    private ProjectVerifier projectVerifier;
    private CommitRepository commitRepository;

    private CommitScoreService commitScoreService;

    @Autowired
    public CommitScoreController(ProjectVerifier projectVerifier, CommitRepository commitRepository, CommitScoreService commitScoreService) {
        this.projectVerifier = projectVerifier;
        this.commitRepository = commitRepository;
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

        List<String> scoreProfileNames = query.getProfiles();

        resource.addScoreValues(commitScoreService.getScoreValuesPerCommit(scoreProfileNames,projectId,commit.getSequenceNumber()));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
