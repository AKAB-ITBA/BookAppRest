package org.example.bookapprest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import lombok.extern.slf4j.Slf4j;
import org.example.bookapprest.model.remoteconfig.RemoteConfig;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;

@Service
@Slf4j
public class RemoteConfigService {

    private static final String PROJECT_ID = "remote-config-test-app-5f2d4";

    private static final String REMOTE_CONFIG_URL = "https://firebaseremoteconfig.googleapis.com/v1/projects/" + PROJECT_ID + "/remoteConfig";

    private static final String CREDENTIALS_PATH = "src/main/resources/remote-config-test-app-5f2d4-firebase-adminsdk-fbsvc-fccddd1b7f.json";
    private GoogleCredentials credentials;

    public RemoteConfigService() {
        try {
            credentials = GoogleCredentials
                    .fromStream(new FileInputStream(CREDENTIALS_PATH))
                    .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.remoteconfig"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateRemoteConfig(RemoteConfig remoteConfig) throws IOException, InterruptedException {
        credentials.refreshIfExpired();
        String accessToken = credentials.getAccessToken().getTokenValue();
        ObjectMapper mapper = new ObjectMapper();

        String requestBody = mapper.writeValueAsString(remoteConfig);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REMOTE_CONFIG_URL))
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json; charset=utf-8")//charset=utf-8???
                .header("If-Match", "*")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        log.info("Response status: {}", response.statusCode());
        log.info("Response body: {}", response.body());


        if (response.statusCode() != 200) {
            throw new RuntimeException("Firebase Remote Config update failed: " + response.body());
        }
    }

    public String getRemoteConfig() throws IOException, InterruptedException {
        credentials.refreshIfExpired();
        String accessToken = credentials.getAccessToken().getTokenValue();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REMOTE_CONFIG_URL))
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch Remote Config: " + response.body());
        }

        return response.body();
    }
}
