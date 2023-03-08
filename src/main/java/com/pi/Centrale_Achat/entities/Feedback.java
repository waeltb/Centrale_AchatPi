package com.pi.Centrale_Achat.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    int questionCount;
    String theme;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern ="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    Date DateCreation;

    @Enumerated(EnumType.STRING)
    SatisfactoryStatus satisfactoryStatus;

    @Enumerated(EnumType.STRING)
    @Column
    private TypeQu typeQu;

    @OneToMany(mappedBy = "feedback", cascade= CascadeType.PERSIST)
    List<Question> questions;

    @JsonIgnore
    @ManyToOne
    User user;
}
