package pl.edu.pwsztar.SocialMedia.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.SocialMedia.service.PushNotificationService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class FcmController {

    @Autowired
    PushNotificationService pushNotificationService;

    @RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> send(String topic, String content) throws JSONException {

        JSONObject body = new JSONObject();
        body.put("to", "/topics/" + topic);
        body.put("priority", "high");

//        JSONObject notification = new JSONObject();
//        notification.put("title", "New message");
//        notification.put("body", content);

        JSONObject data = new JSONObject();
        data.put("content", content);

//        body.put("notification", notification);
        body.put("data", data);

        HttpEntity<String> request = new HttpEntity<>(body.toString());
        System.out.println("firebase request: " + body);

        CompletableFuture<String> pushNotification = pushNotificationService.send(request);
        CompletableFuture.allOf(pushNotification).join();

        try {
            String firebaseResponse = pushNotification.get();
            System.out.println("firebase response: " + firebaseResponse);

            return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Push Notification ERROR!", HttpStatus.BAD_REQUEST);
    }
}