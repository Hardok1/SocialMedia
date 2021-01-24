package pl.edu.pwsztar.SocialMedia.service;

import org.springframework.http.HttpEntity;

import java.util.concurrent.CompletableFuture;

public interface PushNotificationService {
    public CompletableFuture<String> send(HttpEntity<String> entity);
}
