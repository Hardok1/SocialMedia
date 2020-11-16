package pl.edu.pwsztar.SocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountCredentialsDTO {
    private String login;
    private String password;
}
