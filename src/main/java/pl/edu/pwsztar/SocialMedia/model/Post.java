package pl.edu.pwsztar.SocialMedia.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table
public class Post {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Column
    private String content;

    @NotNull
    @Column
    @Temporal(TemporalType.DATE)
    private Calendar created_at;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account original_poster;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Comment> comment;

}
