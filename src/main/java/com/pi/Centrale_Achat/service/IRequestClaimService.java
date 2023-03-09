package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.RequestClaim;
import com.pi.Centrale_Achat.entities.TypeClaim;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;

public interface IRequestClaimService {

    RequestClaim addClaim(UserDetails userDetails,RequestClaim claim);

    RequestClaim ajouterRequestClaimEtAffecterOrder(UserDetails userDetails, RequestClaim claim, Integer id);

    public RequestClaim updateRequestClaim(RequestClaim claim, int idRequestClaim);

    RequestClaim retrieveClaim(Integer idRequestClaim);

    List<RequestClaim> retrieveAllClaims();

    void removeClaim(Integer idRequestClaim);

    //Map<Status, Integer> countReclamationsByStatus();


    HashMap<String, Integer> Claimmax();

    // public List<RequestClaim> findClaimsByType(TypeClaim typeClaim);


    void retrieveComplaintsINPROGRESS();

    RequestClaim ajouterRequestClaimEtAffecterUser(RequestClaim claim, Integer id);

    RequestClaim addComplaint(RequestClaim claim);

    List<RequestClaim> findByTypeClaim(TypeClaim typeClaim);

    void UpdateSystemReclamation();


    //    RequestClaim traiterReclamation(RequestClaim reclamation);
    ResponseEntity<String> traiterReclamation(RequestClaim reclamation);


}
