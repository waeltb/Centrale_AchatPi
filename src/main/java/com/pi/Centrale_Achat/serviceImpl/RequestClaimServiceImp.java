package com.pi.Centrale_Achat.serviceImpl;
import com.pi.Centrale_Achat.entities.*;
import com.pi.Centrale_Achat.repositories.OrderRepo;
import com.pi.Centrale_Achat.repositories.RequestClaimRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.service.IRequestClaimService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RequestClaimServiceImp implements IRequestClaimService {
    private final RequestClaimRepo requestClaimRepository;
    private final SmsService smsService;

    private  final UserRepo userRepository;

    private final MessageService messageService;

    private final OrderRepo orederRepo;

    private final UserRepo userRepo;

    private static final int maxAlerts = 3;

    private final String[] badwords = {"bitch", "ass", "asshole", "cunt", "dick", "shit", "fuck"};

    @Override
    public RequestClaim addClaim(@AuthenticationPrincipal UserDetails userDetails,RequestClaim claim) {

//        String currentUser = userDetails.getUsername();
//        User user1 = userRepo.findUserByUsername(currentUser);
//       // log.info("Saving New Claim: {}", claim.getTypeClaim());
//        claim.setUser(user1);
//        return requestClaimRepository.save(claim);
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        claim.setUser(user1);

        return requestClaimRepository.save(claim);
    }



    @Override
    public RequestClaim updateRequestClaim(RequestClaim claim, int idRequestClaim) {
        claim.setIdRequestClaim(idRequestClaim);
        return requestClaimRepository.save(claim);
    }

    @Override
    public RequestClaim retrieveClaim(Integer idRequestClaim) {
        return requestClaimRepository.findById(idRequestClaim).orElse(null);
    }

    @Override
    public List<RequestClaim> retrieveAllClaims() {
        return requestClaimRepository.findAll();
    }

    @Override
    public void removeClaim(Integer idRequestClaim) {
        requestClaimRepository.deleteById(idRequestClaim);
    }

    @Override
    public HashMap<String, Integer> Claimmax() {
        int pending = 0;
        int in_progress = 0;
        int resolved = 0;
        int refused = 0;
        int nbr_claim = 0;

        for (RequestClaim r : requestClaimRepository.findAll()) {
            if (r.getStatus().toString().equals("resolved"))
                resolved = resolved + 1;

            else if (r.getStatus().toString().equals("refused")) {
                refused = refused + 1;
            } else if (r.getStatus().toString().equals("pending")) {
                pending = pending + 1;
            } else if (r.getStatus().toString().equals("in_progress")) {
                in_progress = in_progress + 1;
            }

        }


        nbr_claim = in_progress + pending + resolved + refused;
        HashMap<String, Integer> statique = new HashMap<String, Integer>();
        statique.put("reclamation resolved", resolved);
        statique.put("reclamation  in progress", in_progress);
        statique.put("reclamation  in pending", pending);
        statique.put("reclamation  in refused", refused);


        return statique;

    }


    @Scheduled(cron = "0 0 8 * * MON,WED")
    public void retrieveComplaintsINPROGRESS() {
        List<RequestClaim> requestClaims = this.requestClaimRepository.findByStatus(Status.in_progress);
        for (RequestClaim c : requestClaims) {
            if (c.getDateCreation().before(new Date())) {
                log.info("List of pending claims: " + c.getSummary() +
                        " : Customer : " + c.getUser().getUsername() +
                        " : Created-At : " + c.getDateCreation());
            }
        }

    }

    @Override
    public RequestClaim ajouterRequestClaimEtAffecterUser(RequestClaim claim, Integer id) {
        return null;
    }

   /* @Override
    public RequestClaim ajouterRequestClaimEtAffecterUser(RequestClaim claim, Integer id) {
       User user = userRepository.findById(id).orElse(null);
        claim.setUser(user);
      //  Order order = (Order) orederRepo.findOrdersByUserId(id);
       // claim.setOrder(order);
        requestClaimRepository.save(claim);
        messageService.addComplaint(claim);
        return claim;
    }*/

    @Override
    public RequestClaim ajouterRequestClaimEtAffecterOrder(@AuthenticationPrincipal UserDetails userDetails,RequestClaim claim, Integer id) {
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        claim.setUser(user1);
        Order order = orederRepo.findById(id).orElse(null);
        System.out.println(  (  order == null ?  "AAAAAAAAAAAAAAAAA": "BBBBBBBBBBBBBBBBBBBBBBBBBBB") );
        claim.setOrder(order);
        requestClaimRepository.save(claim);
        //messageService.addComplaint(claim);
        return claim;
    }





    public ResponseEntity<String> traiterReclamation(RequestClaim reclamation) {
        // Traiter la réclamation et mettre à jour la base de données
        RequestClaim responseEntity;
        try {
            // Traiter la réclamation
            if (reclamation.getTypeClaim().equals(TypeClaim.Product_Canceling)) {
                // Traiter la réclamation de Product_Canceling
                // ...
                reclamation.setStatus(Status.resolved);
            } else if (reclamation.getTypeClaim().equals(TypeClaim.Package_Not_Received)) {
                // Traiter la réclamation de Package_Not_Received
                // ...
                reclamation.setStatus(Status.in_progress);
            }
            else if (reclamation.getTypeClaim().equals(TypeClaim.Wrong_Product_Reference)) {
                // Traiter la réclamation de Wrong_Product_Reference
                // ...
                reclamation.setStatus(Status.in_progress);

            } else if (reclamation.getTypeClaim().equals(TypeClaim.Damaged_Product)) {
                // Traiter la réclamation de Damaged_Product
                // ...
                reclamation.setStatus(Status.refused);
            } else {
                // Réclamation invalide
                return new ResponseEntity<>("reclamation de type invalid!", HttpStatus.NO_CONTENT);
            }

            // Mettre à jour la réclamation dans la base de données
            RequestClaim updatedReclamation = requestClaimRepository.save(reclamation);

            // Renvoyer le statut de la réclamation à l'utilisateur
            // reclamation = RequestClaim.ok(updatedReclamation.getStatus().toString());
            return new ResponseEntity<>(updatedReclamation.getStatus().toString(),HttpStatus.OK);
        } catch (Exception e) {
            // reclamation = RequestClaim.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //     return new ResponseEntity<>("Reclamation traite!",HttpStatus.OK);
    }




    @Override
    public RequestClaim addComplaint(RequestClaim claim) {
        log.info("Saving New Claim: {}", claim.getTypeClaim());
        return requestClaimRepository.save(claim);
    }

    @Override
    public List<RequestClaim> findByTypeClaim(TypeClaim typeClaim) {
        List<RequestClaim> allClaims = requestClaimRepository.findAll();
        List<RequestClaim> target = new ArrayList<RequestClaim>();
        for (RequestClaim claim : allClaims) {
            if (claim.getTypeClaim().equals(typeClaim)) {
                target.add(claim);
            }
        }
        return target;
    }




    @Scheduled(fixedDelay = 60000)
    public void badwordsFilter() {
        boolean hasBadWords = false;
        System.out.println("In method Bad Words Filter ....");
        List<RequestClaim> requestClaims = requestClaimRepository.findAll();

        for (RequestClaim requestClaim : requestClaims) {
            List<String> Summary = new ArrayList<String>(Arrays.asList(requestClaim.getSummary().split(" ")));
            List<String> finalString = new ArrayList<String>();
            for (String word : Summary) {
                for (String badword : badwords) {
                    if (word.matches(".*" + badword + ".*")) {
                        hasBadWords = true;
                        word = "****";
                    }
                }
                finalString.add(word);
            }
            requestClaim.setSummary(String.join(" ", finalString));

            if (hasBadWords) {
                if (requestClaim.getUser().getNumberOfAlerts() < maxAlerts ) {
                    requestClaim.getUser().setNumberOfAlerts(requestClaim.getUser().getNumberOfAlerts() + 1);
                    SmsRequest smsAlert = new SmsRequest(requestClaim.getUser().getNumTel(),requestClaim.getUser().getUsername() + ", This sms is a warning for you not meeting the appropriate behaviour" +
                            " inside the app. Your recent post had offensive words. A matter that we condemn in the strongest of possible terms. We hope this act never repeats itself otherwise we will be in the obligation of taking punitive measures against you\"");
                    smsService.sendSms(smsAlert);
                    hasBadWords = false;
                }
                else {
                    requestClaim.getUser().setEnabled(false);
                }

            }
        }
    }




    @Scheduled(fixedDelay = 60000)
    @Override
    public void UpdateSystemReclamation() {
        List<RequestClaim> reclamations=requestClaimRepository.TraiterSystemReclamation(TypeClaim.Package_Not_Received);
        for (RequestClaim reclamation : reclamations) {
            reclamation.setStatus(Status.resolved);
            final String accountSid = "AC5316c1de9fd17409851f5a092e4004a9";
            final String authToken = "3cdbcd4feb505eef5fb205a1d220d6c4";
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(new com.twilio.type.PhoneNumber("+216" + reclamation.getUser().getNumTel()),
                            new com.twilio.type.PhoneNumber("+12764004451"),
                            "Bonjour " + reclamation.getUser().getNom() + " " + reclamation.getUser().getAddress() + " "
                                    + "Votre Reclmation N°" + reclamation.getUser() + " " + "a été traitée Merci pour votre confiance , votre package sera reçu bientot  !")
                    .create();
        }
        requestClaimRepository.saveAll(reclamations);
    }
}
