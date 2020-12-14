package pl.edu.pwsztar.SocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.edu.pwsztar.SocialMedia.model.Account;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicAccountInfo {
    private Long id;
    private String forename;
    private String surname;
    private String country;
    private String city;

    public PublicAccountInfo(Account account){
        this.id = account.getId();
        this.forename = account.getForename();
        this.surname = account.getSurname();
        this.country = account.getCountry();
        this.city = account.getCity();
    }
}
