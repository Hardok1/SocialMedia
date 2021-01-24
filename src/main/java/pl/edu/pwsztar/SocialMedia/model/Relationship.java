package pl.edu.pwsztar.SocialMedia.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table
public class Relationship {
    @Id
    @GeneratedValue
    Long id;

    private String status;

    @ManyToOne
    @JoinColumn
    private Account userA;

    @ManyToOne
    @JoinColumn
    private Account userB;
}
