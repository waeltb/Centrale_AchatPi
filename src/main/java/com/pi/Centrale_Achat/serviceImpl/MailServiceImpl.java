package com.pi.Centrale_Achat.serviceImpl;
import com.pi.Centrale_Achat.dto.Email;
import com.pi.Centrale_Achat.entities.Bill;
import com.pi.Centrale_Achat.entities.Product;
import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.BillRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MailServiceImpl {
    private final JavaMailSender mailSender;
    private final BillRepo billRepo;
    private final UserRepo userRepo;
    private final BillServiceImpl billService;

//    public void sendEmail(@AuthenticationPrincipal UserDetails userDetails, Email email, int idfacture ){
//        String currentUser = userDetails.getUsername();
//        User user1 = userRepo.findUserByUsername(currentUser);
//        Bill bill = billRepo.findById(idfacture).orElse(null);
//        String name= bill.getOrder().getUser().getUsername();
//        if(!(user1.getUsername()==name)){
//            System.out.println("erreur");
//        }
//        else {
//            Bill b = billRepo.findById(idfacture).get();
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom("achraf.elj@edu.isetcom.tn");
//            simpleMailMessage.setTo(b.getOrder().getUser().getEmail());
//            simpleMailMessage.setSubject(email.getSubjuct());
//            simpleMailMessage.setText("votre facture "+"\n code facture:"+b.getCode()+"\n le prix totale:"+
//                    b.getPrice()+"\n date facture :"+b.getDateFacture());
//            this.mailSender.send(simpleMailMessage);
//        }
//    }
public void sendEmail(@AuthenticationPrincipal UserDetails userDetails, Email email, int idfacture) throws MessagingException {
    String currentUser = userDetails.getUsername();
    User user1 = userRepo.findUserByUsername(currentUser);
    Bill bill = billRepo.findById(idfacture).orElse(null);
    String name = bill.getOrder().getUser().getUsername();
    if (!(user1.getUsername() == name)) {
        System.out.println("erreur");
    } else {
        Bill b = billRepo.findById(idfacture).get();
        String[] labels = {"Cher", "Ona Generer une Facture Pour Vous.", "Code Facture:", "Price:", "Date Facture:"};
        String[] values = {b.getOrder().getUser().getUsername(), "", b.getCode(), String.valueOf(b.getPrice()), b.getDateFacture().toString()};
        String[] colors = {"#333333", "#333333", "#1E90FF", "#008000", "#DC143C"};
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family: Arial, sans-serif;'>");

        for (int i = 0; i < labels.length; i++) {
            sb.append("<p style='color: " + colors[i] + "; font-size: 18px;'>" + labels[i] + " " + values[i] + "</p>");
        }

        sb.append("<p style='color: #333;'>Merci pour votre fidélité.</p>");
        sb.append("<p style='color: #333;'>Cordialement,</p>");
        sb.append("<p style='color: " + colors[0] + ";'>L'équipe de notre entreprise</p>");

        sb.append("</body></html>");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(b.getOrder().getUser().getEmail());
        helper.setSubject(email.getSubjuct());
        helper.setText(sb.toString(), true);
        mailSender.send(message);
    }
}


//    @Scheduled(fixedRate = 2000L)
//public void sendEmailToClients(){
//    List<User> users= userRepo.findAll();
//    for (User user : users){
//        String code = billService.generateCode();
//
//        String to = user.getEmail();
//        String subject = "Code Coupon";
//        String[] colors = {"#0072C6", "#5B9BD5", "#ED7D31", "#A5A5A5", "#FFC000", "#4472C4"};
//        int randomColorIndex = new Random().nextInt(colors.length);
//        String color = colors[randomColorIndex];
//        String body = "<html><body>"
//                + "<h2 style='color: " + color + ";'>Bonjour " + user.getUsername() + ",</h2>"
//                + "<p style='color: #333;'>Vous avez gagné un code promo sur tous vos achats: <strong style='color: " + color + ";'>" + code + "</strong></p>"
//                + "<p style='color: #333;'>Utilisez le code promo lors du paiement pour bénéficier de la réduction.</p>"
//                + "<table style='border-collapse: collapse; border: 1px solid " + color + ";'>"
//                + "<thead style='background-color: " + color + "; color: #FFF;'>"
//                + "</thead>"
//                + "<tbody>"
//                + "</tbody>"
//                + "</table>"
//                + "<p style='color: #333;'>Merci pour votre fidélité.</p>"
//                + "<p style='color: #333;'>Cordialement,</p>"
//                + "<p style='color: " + color + ";'>L'équipe de notre entreprise</p>"
//                + "</body></html>";
//
//        MimeMessage message = mailSender.createMimeMessage();
//        try {
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom("achraf.elj@edu.isetcom.tn");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(body, true);
//            mailSender.send(message);
//        } catch (MessagingException e) {
//        }
//    }
//}


//@Scheduled(fixedRate = 2000L) //cron 30 9 1 * ? le 1 de chaque mois à 9:30 a.m !!
//    public void sendd(){
//    Product product = billService.offre();
//        User u = billService.getOrderBill();
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("achraf.elj@edu.isetcom.tn");
//        simpleMailMessage.setTo(u.getEmail());
//        simpleMailMessage.setText("vous avez gangner "+product.getName());
//        simpleMailMessage.setSubject("Achat ganger");
//        this.mailSender.send(simpleMailMessage);
//    }





}
