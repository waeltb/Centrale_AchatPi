package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.RequestClaim;
import com.pi.Centrale_Achat.entities.TypeClaim;
import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.service.IRequestClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/RequestClaim")
public class RequestClaimController {


    private final IRequestClaimService RequestClaimService;

    private final UserRepo userRepo;

    @PostMapping("/addClaim")
    @PreAuthorize("hasRole('CUSTOMER')")
    public RequestClaim addClaim(@AuthenticationPrincipal UserDetails userDetails, @RequestBody RequestClaim claim) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepo.findUserByUsername(currentUserName);
        if (currentUser==null) {
            System.out.println("Vous devez se connecter");
        }
        return RequestClaimService.addClaim(userDetails,claim);
    }

    @PostMapping("/addNAffectOrderPOURreclamation/{idOrder}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public RequestClaim ajouterRequestClaimEtAffecterOrder(@AuthenticationPrincipal UserDetails userDetails,@RequestBody RequestClaim claim,@PathVariable("idOrder") Integer id)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepo.findUserByUsername(currentUserName);
        if (currentUser==null) {
            System.out.println("Vous devez se connecter");
        }
        return RequestClaimService.ajouterRequestClaimEtAffecterOrder(userDetails,claim,id);
    }

 /*   @PutMapping("/addNAffectUSER/{idUser}")
    public RequestClaim ajouterRequestClaimEtAffecterUser(@RequestBody RequestClaim claim,@PathVariable("idUser") Integer id)
    {  // claim.setTypeClaim(findClaimsByType(typeClaim));
        return RequestClaimService.ajouterRequestClaimEtAffecterUser(claim,id);
    }*/

    @PutMapping("/add")
    RequestClaim addComplaint(@RequestBody RequestClaim  claim){
        //  claim.setSummary(BadWordFilter.getCensoredText(claim.getSummary()));
        return RequestClaimService.addComplaint(claim);
    }

    @PutMapping("/UpdateClaim/{idRequestClaim}")
    public RequestClaim updateRequestClaim(@RequestBody RequestClaim claim, @PathVariable("idRequestClaim") int idRequestClaim){
        return RequestClaimService.updateRequestClaim(claim, idRequestClaim);
    }

    @GetMapping("/get/{idRequestClaim}")
    RequestClaim retrieveClaim(@PathVariable("idRequestClaim") Integer idRequestClaim) {
        return RequestClaimService.retrieveClaim(idRequestClaim);
    }

    @GetMapping("/all")
    public List<RequestClaim> retrieveAllClaims() {
        return RequestClaimService.retrieveAllClaims();
    }

    @DeleteMapping("/deleteClaim/{idRequestClaim}")
    void removeClaim(@PathVariable("idRequestClaim") Integer idRequestClaim) {
        RequestClaimService.removeClaim(idRequestClaim);
    }



    // méthode qui calcule le nombre de réclamations validées et en cours et refusee dans un système de gestion de réclamations.
    @GetMapping("/stat")
    @ResponseBody
    public HashMap<String, Integer> statistique() {
        return RequestClaimService.Claimmax();
    }

    @GetMapping("/RechercheClaimParType/{typeClaim}")
    public List<RequestClaim> findClaimsByType(@PathVariable ("typeClaim") TypeClaim typeClaim){
        return RequestClaimService.findByTypeClaim(typeClaim);
    }

    @Scheduled(fixedDelay = 60000)
    public void UpdateSystemReclamation(){
        RequestClaimService.UpdateSystemReclamation();
        String text = "\n ReclmationUpdated \n";
        log.info(text);
        text="";
    }

    @PostMapping("/reclamations")
    // @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> traiterReclamation(@RequestBody RequestClaim reclamation) {
        return RequestClaimService.traiterReclamation(reclamation);

    }

}
