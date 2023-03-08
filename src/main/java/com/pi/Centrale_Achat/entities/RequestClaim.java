package com.pi.Centrale_Achat.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Data
public class RequestClaim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int idRequestClaim;
    @Enumerated(EnumType.STRING)
    TypeClaim typeClaim;
    int ref;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern ="yyyy-MM-dd",shape = JsonFormat.Shape.STRING)
    //  @DateTimeFormat(pattern = "yyyy-MM-dd")

    Date dateCreation;
    String Summary;
    @Enumerated(EnumType.STRING)
    Status status;
    @JsonIgnore
    @ManyToOne
    Order order;
    @OneToMany(mappedBy = "requestClaim",cascade = {CascadeType.PERSIST})
    List<ResponseClaim>responseClaims;

    @JsonIgnore
    @ManyToOne
    User user;

}
