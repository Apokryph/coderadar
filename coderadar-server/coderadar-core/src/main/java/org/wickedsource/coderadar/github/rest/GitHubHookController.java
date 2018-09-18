package org.wickedsource.coderadar.github.rest;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.wickedsource.coderadar.github.domain.GitHubCommitDTO;
import org.wickedsource.coderadar.github.domain.GitHubHook;
import org.wickedsource.coderadar.github.domain.GitHubHookDTO;
import org.wickedsource.coderadar.github.domain.GitHubHookRepository;

import java.io.IOException;
import java.util.ArrayList;


@Controller
@Transactional
@RequestMapping(path="/events/githubrepo")
public class GitHubHookController {

    private GitHubHookRepository gitHubHookRepository;

    public GitHubHookController(GitHubHookRepository gitHubHookRepository) {
        this.gitHubHookRepository = gitHubHookRepository;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> storeCommitsFromHook(@RequestBody String payload) {

        // unmarshalling json payload string to dto object
        ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        GitHubHookDTO gitHubHook = new GitHubHookDTO();

        try {
            gitHubHook = om.readValue(payload, GitHubHookDTO.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // storing commit information to database
        ArrayList<GitHubCommitDTO> commits = gitHubHook.getCommits();

        for(int i = 0; i < commits.size(); i++) {
            System.out.println("id: " + commits.get(i).getId() + ", repo: " + gitHubHook.getRepository().getFull_name());
            GitHubHook hook = new GitHubHook(commits.get(i).getId(), gitHubHook.getRepository().getFull_name());
            gitHubHookRepository.save(hook);
        }
        return new ResponseEntity<>("{ \"message\": \"Successfully received payload.\" }\n", HttpStatus.OK);
    }
}
