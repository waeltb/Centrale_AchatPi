package com.pi.Centrale_Achat.serviceImpl;

import com.pi.Centrale_Achat.entities.RequestClaim;
import com.pi.Centrale_Achat.entities.TypeClaim;
import com.pi.Centrale_Achat.repositories.RequestClaimRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final JavaMailSender javaMailSender;
    private  final UserRepo userRepository;
    private final RequestClaimRepo requestClaimRepository;

    public void sendSimpleMail(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("bugSmashers@esprit.tn");
        mailMessage.setTo(email);
        mailMessage.setText(message);
        mailMessage.setSubject(subject);
        javaMailSender.send(mailMessage);
        System.out.println("Mail Send...");
    }

    public void  addComplaint(RequestClaim claim) {
        if (claim.getTypeClaim() == TypeClaim.Wrong_Product_Reference) {

            this.sendSimpleMail(claim.getUser().getEmail(), "Alerte", "AlerteReclamation ! Votre reclamation est pris en compte , votre Produit sera changee dans les plus brefs delai ");
        }


    }


}
