package pl.edu.pwsztar.SocialMedia.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pwsztar.SocialMedia.dto.AccountDTO;

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
    private Calendar createdAt;

    @NotBlank
    @Column
    private String country;

    @NotBlank
    @Column
    private String city;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "originalPoster")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userA")
    private Set<Relationship> relationship;

    public Account(@NotBlank AccountDTO accountDTO){
        this.login = accountDTO.getLogin();
        this.password = accountDTO.getPassword();
        this.forename = accountDTO.getForename();
        this.surname = accountDTO.getSurname();
        this.country = accountDTO.getCountry();
        this.city = accountDTO.getCity();
    }
}
