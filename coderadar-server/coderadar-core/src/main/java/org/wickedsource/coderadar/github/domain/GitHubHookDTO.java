package org.wickedsource.coderadar.github.domain;

import java.util.ArrayList;

/**
 * @author Kobs
 */
public class GitHubHookDTO {

    private ArrayList<GitHubCommitDTO> commits;

    private GitHubRepositoryDTO repository;

    public ArrayList<GitHubCommitDTO> getCommits() {
        return commits;
    }

    public void setCommits(ArrayList<GitHubCommitDTO> commits) {
        this.commits = commits;
    }

    public GitHubRepositoryDTO getRepository() { return repository; }

    public void setRepository(GitHubRepositoryDTO repository) { this.repository = repository; }
}
