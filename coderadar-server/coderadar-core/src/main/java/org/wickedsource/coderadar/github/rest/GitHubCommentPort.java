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
import java.util.List;

@Component
public class GitHubCommentPort {

    private Logger logger = LoggerFactory.getLogger(GitHubCommentPort.class);

    private GitHubHookRepository gitHubHookRepository;
    private CommitScoreService commitScoreService;
    private ScoreProfileRepository scoreProfileRepository;

    @Value("${github.username}")
    private String username;

    @Value("${github.githubtoken}")
    private String token;

    @Autowired
    public GitHubCommentPort(GitHubHookRepository gitHubHookRepository, CommitScoreService commitScoreService, ScoreProfileRepository scoreProfileRepository) {
        this.gitHubHookRepository = gitHubHookRepository;
        this.commitScoreService = commitScoreService;
        this.scoreProfileRepository = scoreProfileRepository;
    }

    /**
     *
     * @param commit
     * @throws IOException
     * @throws AuthenticationException
     */
    public void postCommentOnGitHub(Commit commit) throws IOException, AuthenticationException {

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

            commentContentBuilder.append("Coderadar Commit Score:").append("/n");
            commentContentBuilder.append(scoreProfile.getName()).append(": ");
            commentContentBuilder.append(commitScoreService.getScoreValueFromCommitAndProfile(commit, scoreProfile)).append("/n");
        }

        // make http post with initialized content
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String commentContent = commentContentBuilder.toString();
        String json = "{ \"body\": \"" + commentContent + "\" }\n";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", username);
        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials(username, token);
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

        CloseableHttpResponse response = client.execute(httpPost);
        client.close();

        System.out.println(response);
    }

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

    public void deleteCommitEntry(String commitName) {
        gitHubHookRepository.deleteGitHubHookByCommitHashName(commitName);
        logger.info(
                "deleted commit in github_repository_hook table id: {}",
                commitName);
    }
}
