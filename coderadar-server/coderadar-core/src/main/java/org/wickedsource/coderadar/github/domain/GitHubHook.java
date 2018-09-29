package org.wickedsource.coderadar.github.domain;

import javax.persistence.*;

/**
 * @author Kobs
 */
@Entity
@Table(name = "github_repository_hook")
@SequenceGenerator(
        name = "github_repository_hook_sequence",
        sequenceName = "seq_qrh_id",
        allocationSize = 1
)
public class GitHubHook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "github_repository_hook_sequence")
    @Column(name = "id")
    private Long id;

    @Column(name = "commit_name")
    private String commitHashName;

    @Column(name = "repository_full_name")
    private String repositoryFullName;

    public GitHubHook() {}

    public GitHubHook(String commitHashName, String repositoryFullName) {
        this.commitHashName = commitHashName;
        this.repositoryFullName = repositoryFullName;
    }

    public String getRepositoryFullName() {
        return repositoryFullName;
    }

    public void setRepositoryFullName(String repositoryFullName) {
        this.repositoryFullName = repositoryFullName;
    }

    public String getCommitHashName() {
        return commitHashName;
    }

    public void setCommitHashName(String commitHashName) {
        this.commitHashName = commitHashName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
