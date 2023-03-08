package com.pi.Centrale_Achat.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idrating")
    private Integer idrating; // Clé primaire
    private Double score;
    @ManyToOne(fetch = FetchType.LAZY) /*(avec paresse): Quand on récupère une activite de la base de
    données, aucun rate lié à cette équipe ne sera récupéré, jusqu’à ce que nous
    faisons un appel explicite dans le code  */
    @JsonIgnore
    private Product product;
    @JsonIgnore
    @ManyToOne
    User user;

}
