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
public class Account {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column
    private String login;

    @NotBlank
    @Column
    private String password;

    @NotBlank
    @Column
    private String forename;

    @NotBlank
    @Column
    private String surname;

    @NotNull
    @Column
    @Temporal(TemporalType.DATE)
    private Calendar created_at;

    @NotBlank
    @Column
    private String country;

    @NotBlank
    @Column
    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "original_poster")
    private Set<Post> posts;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
    private Set<Comment> comments;

    @ManyToMany
    @JoinTable(name = "account_interest",
            joinColumns = {
                @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
            },
            inverseJoinColumns = {
                @JoinColumn(name = "interest_id", referencedColumnName = "id", nullable = false)
            }
    )
    private Set<Interest> interest;

    @OneToMany(mappedBy = "user_a")
    private Set<Relationship> relationship;
}
