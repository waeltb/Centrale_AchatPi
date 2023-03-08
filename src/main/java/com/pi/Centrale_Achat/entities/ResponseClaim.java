package com.pi.Centrale_Achat.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ResponseClaim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idResponseClaim;
    String comment;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern ="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    Date dateResponse;
    @JsonIgnore
    @ManyToOne
    RequestClaim requestClaim;

}
