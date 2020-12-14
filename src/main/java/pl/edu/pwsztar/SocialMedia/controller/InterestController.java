package pl.edu.pwsztar.SocialMedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pwsztar.SocialMedia.dto.InterestDTO;
import pl.edu.pwsztar.SocialMedia.service.InterestService;

import java.util.List;

@RestController
@RequestMapping("/interest/")
public class InterestController {
    private final InterestService interestService;

    @Autowired
    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    @GetMapping("getAll")
    public ResponseEntity<List<InterestDTO>> getAll() {
        return new ResponseEntity<>(interestService.getAllInterests(), HttpStatus.OK);
    }
}
