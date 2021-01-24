package pl.edu.pwsztar.SocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.SocialMedia.model.Account;
import pl.edu.pwsztar.SocialMedia.model.Post;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailsDTO {
    private Long id;
    private String forename;
    private String surname;
    private String country;
    private String city;
    private Set<String> interests;

    public AccountDetailsDTO(Account account) {
        this.id = account.getId();
        this.forename = account.getForename();
        this.surname = account.getSurname();
        this.country = account.getCountry();
        this.city = account.getCity();
    }
}
