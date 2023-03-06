package com.pi.Centrale_Achat.entities;
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
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int RefDelivery;
    float coastDelivery;
    float totalCoast;
    String adress;
    StatusDelivery statusDelivery;
    Date startDate;
    Date endDate;
    @Enumerated(EnumType.STRING)
    TypeDelivery typeDelivery;
    @OneToOne
    Order order;
}
