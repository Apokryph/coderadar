package org.wickedsource.coderadar.github.domain;

import org.wickedsource.coderadar.commit.domain.Commit;

import javax.persistence.*;

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

    @OneToOne
    @JoinColumn(name = "commit_id")
    private Commit commit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}
