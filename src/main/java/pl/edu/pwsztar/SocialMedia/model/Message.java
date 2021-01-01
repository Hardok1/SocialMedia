package pl.edu.pwsztar.SocialMedia.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.util.Calendar;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table
public class Message {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn
    private Chat chat;

    @NotBlank
    @Column
    private String content;

    @NotNull
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account sender;
}
