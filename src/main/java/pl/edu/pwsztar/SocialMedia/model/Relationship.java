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
    @JoinColumn
    private Account user_a;

    @ManyToOne
    @JoinColumn
    private Account user_b;
}
