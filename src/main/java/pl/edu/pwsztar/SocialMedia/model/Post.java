package pl.edu.pwsztar.SocialMedia.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor

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
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account originalPoster;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private Set<Comment> comment;

}
