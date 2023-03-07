package com.pi.Centrale_Achat.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nom;
    String prenom;
    String username;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date dateNaissance;
    String password;
    String email;
    String verificationCode;
    boolean verified=false;
    String address;
    String image;
    String numTel;
    private String resetpasswordcode;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private Set<Role> roles = new HashSet<>();
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST})
    List<Order>orders;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST})
    List<Product>products;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST})
    List<Category>categories;
    @JsonIgnore
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST})
    List<Tender>tenders;
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST})
    List<RequestClaim>requestClaims;
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    List<OperatorScore>operatorScores;


}
