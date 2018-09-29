package org.wickedsource.coderadar.github.rest;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;
import org.wickedsource.coderadar.github.domain.GitHubHookRepository;
import org.wickedsource.coderadar.score.rest.commitscore.CommitScoreService;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfile;
import org.wickedsource.coderadar.score.domain.scoreprofile.ScoreProfileRepository;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;


/**
 * Class to send a GitHub Comment with calculated scores for all score profiles on a specific Commit.
 * You can find required information about Coderadar GitHub user in local.application.properties.
 * Sometimes you get Authentication error while sending post. Therefore you have to refresh the "github.token".
 *
 * @author Kobs
 */
@Component
public class GitHubCommentPort {

    private Logger logger = LoggerFactory.getLogger(GitHubCommentPort.class);

    private GitHubHookRepository gitHubHookRepository;
    private CommitScoreService commitScoreService;
    private ScoreProfileRepository scoreProfileRepository;

    @Value("${github.username}")
    private String username;

    @Value("${github.token}")
    private String token;

    @Autowired
    public GitHubCommentPort(GitHubHookRepository gitHubHookRepository, CommitScoreService commitScoreService, ScoreProfileRepository scoreProfileRepository) {
        this.gitHubHookRepository = gitHubHookRepository;
        this.commitScoreService = commitScoreService;
        this.scoreProfileRepository = scoreProfileRepository;
    }

    /**
     * Method to send post request to GitHub under provided commit.
     *
     * @see <a href="https://developer.github.com/v3/repos/comments/#get-a-single-commit-comment</a>
     * @param commit The commit you want Coderadar to send a GitHub comment to.
     * @throws AuthenticationException If you get this error try to refresh your token in local.application.properties
     */
    public void postCommentOnGitHub(Commit commit) {

        // required information for sending commit comment
        String fullRepositoryName = gitHubHookRepository.findGitHubHookByCommitHashName(commit.getName()).getRepositoryFullName();
        String url = "https://api.github.com/repos/" + fullRepositoryName + "/commits/" + commit.getName() + "/comments?";

        // prepare comment content
        StringBuilder commentContentBuilder = new StringBuilder();
        List<ScoreProfile> scoreProfiles = scoreProfileRepository.findByProjectId(commit.getProject().getId());

        if (scoreProfiles == null || scoreProfiles.isEmpty()) {
            throw new IllegalStateException(
                    String.format(
                            "could not find scoreProfiles in project %s",
                            commit.getProject().getName()));
        }

        // add commit commitscore for every profile to comment content
        for (ScoreProfile scoreProfile : scoreProfiles) {

            commentContentBuilder.append(scoreProfile.getName()).append(": ");
            commentContentBuilder.append(commitScoreService.getScoreValueFromCommitAndProfile(commit, scoreProfile)).append(", ");
        }

        // make http post with initialized content
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String commentContent = commentContentBuilder.toString();
        String json = "{ \"body\": \"" + commentContent + "\" }\n";
        StringEntity entity = null;

        try {
            entity = new StringEntity(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", username);
        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(username, token);

        // try to authenticate with github username and token
        try {
            httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            logger.error(
                    "Authentication problem. Please refresh your github token.");
        }

        //try to send http post
        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(
                    "Posting comment on github didn't work.");
        }

        // delete entry so coderadar is not posting again
        deleteCommitEntry(commit.getName());

        logger.info(
                "Sent post request to github on commit {}. Got following response: {}",
                commit.getName(),
                response);
    }

    /**
     * Method to check if there is an entry stored for this commit in github_repository_hook table.
     *
     * @param commitName name of the commit you want to check for
     * @return true, if commit entry with the same name is stored
     */
    public boolean isCommitInHook(String commitName) {
        if (gitHubHookRepository.existsByCommitHashName(commitName)) {
            logger.info(
                    "commit {} is in github_repository_hook table",
                    commitName);
            return true;
        } else {
            logger.info(
                    "commit {} is not in github_repository_hook",
                    commitName);
            return false;
        }
    }

    /**
     * Method to delete an entry with the commit name from github_repository_hook.
     * @param commitName name of the commit you want to delete
     */
    private void deleteCommitEntry(String commitName) {
        gitHubHookRepository.deleteGitHubHookByCommitHashName(commitName);
        logger.info(
                "deleted commit in github_repository_hook table id: {}",
                commitName);
    }
}
