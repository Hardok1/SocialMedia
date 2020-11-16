package pl.edu.pwsztar.SocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.SocialMedia.model.Interest;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private String login;
    private String password;
    private String forename;
    private String surname;
    private String country;
    private String city;
    private Set<Interest> interests;
}
