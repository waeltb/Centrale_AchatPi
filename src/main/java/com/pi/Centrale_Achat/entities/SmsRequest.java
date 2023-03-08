package com.pi.Centrale_Achat.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;


import javax.persistence.*;



import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;

    String phoneNumber;
    String message;

    public SmsRequest(String phoneNumber, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "SmsRequest{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
