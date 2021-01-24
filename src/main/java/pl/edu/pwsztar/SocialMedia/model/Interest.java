package pl.edu.pwsztar.SocialMedia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table
public class Interest {
    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Column
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "interest")
    private Set<Account> accounts;
}
