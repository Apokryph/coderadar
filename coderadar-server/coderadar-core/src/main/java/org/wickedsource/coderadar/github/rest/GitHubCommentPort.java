package org.wickedsource.coderadar.github.rest;

import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;
import org.wickedsource.coderadar.commit.domain.Commit;


import java.io.IOException;

@Component
public class GitHubCommentPort {

    public GitHubCommentPort() {
    }

    public void postCommentOnGitHub(String commitName, String fullRepositoryName) throws IOException, AuthenticationException {
        String url = "https://api.github.com/repos/" + fullRepositoryName + "/commits/" + commitName + "/comments?";
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        String json = "{ \"body\": \"Test Comment from GitHubCommentPort.\" }\n";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("User-Agent", "c0deradar");
        UsernamePasswordCredentials creds
                = new UsernamePasswordCredentials("c0deradar", "e04666f53416d91d88d30345dea7761bb18dd41d");
        httpPost.addHeader(new BasicScheme().authenticate(creds, httpPost, null));

        CloseableHttpResponse response = client.execute(httpPost);
        client.close();
        System.out.println(response);
    }
}
