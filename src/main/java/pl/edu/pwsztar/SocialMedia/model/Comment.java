package pl.edu.pwsztar.SocialMedia.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table
public class Comment {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Column
    private String content;

    @NotNull
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
