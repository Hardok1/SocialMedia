package pl.edu.pwsztar.SocialMedia.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table
public class Relationship {
    @Id
    @GeneratedValue
    Long id;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_a")
    private Account userA;

    @ManyToOne
    @JoinColumn(name = "user_b")
    private Account userB;
}
