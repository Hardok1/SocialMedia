package pl.edu.pwsztar.SocialMedia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pwsztar.SocialMedia.dto.InterestDTO;
import pl.edu.pwsztar.SocialMedia.model.Interest;
import pl.edu.pwsztar.SocialMedia.repository.InterestRepository;
import pl.edu.pwsztar.SocialMedia.service.InterestService;

import java.util.ArrayList;
import java.util.List;

@Service
public class InterestServiceImpl implements InterestService {

    final InterestRepository interestRepository;

    @Autowired
    public InterestServiceImpl(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    @Override
    public List<InterestDTO> getAllInterests() {
        List<InterestDTO> interests = new ArrayList<>();
        for (Interest interest : interestRepository.findAll()) {
            interests.add(new InterestDTO(interest.getId(), interest.getName()));
        }
        return interests;
    }
}
