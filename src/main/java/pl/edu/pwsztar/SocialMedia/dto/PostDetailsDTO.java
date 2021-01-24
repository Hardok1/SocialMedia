package pl.edu.pwsztar.SocialMedia.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailsDTO {
    private long id;
    private String content;
    private String createdAt;
    private Set<CommentDTO> comments;
}
