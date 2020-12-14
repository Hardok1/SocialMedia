package pl.edu.pwsztar.SocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private long id;
    private String content;
    private Calendar createdAt;
}
