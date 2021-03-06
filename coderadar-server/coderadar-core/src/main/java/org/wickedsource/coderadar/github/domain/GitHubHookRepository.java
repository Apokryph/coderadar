package org.wickedsource.coderadar.github.domain;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Kobs
 */
public interface GitHubHookRepository extends CrudRepository<GitHubHook, Long> {

    GitHubHook findGitHubHookByCommitHashName(String commitName);

    boolean existsByCommitHashName(String commitHashName);

    void deleteGitHubHookByCommitHashName(String commitHashName);
}
