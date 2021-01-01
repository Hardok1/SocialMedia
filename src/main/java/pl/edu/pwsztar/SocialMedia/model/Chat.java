package pl.edu.pwsztar.SocialMedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class Chat {

    @Id
    @GeneratedValue
    Long id;

    @OneToMany
    List<Message> messages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account member1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account member2;
}
