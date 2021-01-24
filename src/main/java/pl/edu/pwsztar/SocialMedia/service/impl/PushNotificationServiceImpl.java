package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.edu.pwsztar.SocialMedia.service.PushNotificationService;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
    private static final String FIREBASE_SERVER_KEY = "AAAAL8G4hP8:APA91bE2Z6n-BhSobbvWKcbikPzKQAnNRwGytHcqalih9Ybw7Ov3cBanV0kQBuQUqplTtY2DikyiAMCTHubxrq7kD1DCKxgPJuupxSoxOYxokLI9xuxq1xcG-sE_ZugGpxRyAfPCWTXz";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

    @Async
    public CompletableFuture<String> send(HttpEntity<String> entity) {

        RestTemplate restTemplate = new RestTemplate();

        /**
         https://fcm.googleapis.com/fcm/send
         Content-Type:application/json
         Authorization:key=FIREBASE_SERVER_KEY*/

        ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
        interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
        restTemplate.setInterceptors(interceptors);

        String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

        return CompletableFuture.completedFuture(firebaseResponse);
    }
}
