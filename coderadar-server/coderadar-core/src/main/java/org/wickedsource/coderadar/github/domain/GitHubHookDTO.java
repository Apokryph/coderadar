package org.wickedsource.coderadar.github.domain;

import java.util.ArrayList;

public class GitHubHookDTO {

    private Long id;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private ArrayList<GitHubCommitDTO> commits;

    public ArrayList<GitHubCommitDTO> getCommits() {
        return commits;
    }

    public void setCommits(ArrayList<GitHubCommitDTO> commits) {
        this.commits = commits;
    }
}
