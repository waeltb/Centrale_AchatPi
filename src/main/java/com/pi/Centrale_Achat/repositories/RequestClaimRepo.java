package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Bill;
import com.pi.Centrale_Achat.entities.RequestClaim;
import com.pi.Centrale_Achat.entities.Status;
import com.pi.Centrale_Achat.entities.TypeClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RequestClaimRepo extends JpaRepository<RequestClaim,Integer> {
    //public List<RequestClaim> findRequestClaimByTypeClaim(TypeClaim typeClaim);
    //  RequestClaim findByStatus(Status status);
    RequestClaim findByRef(int ref);

    List<RequestClaim> findByStatus(Status inprogress);

    List<RequestClaim> findByTypeClaim(TypeClaim typeClaim ); //spring data jpa




    @Query
            ("Select r from RequestClaim r  WHERE r.status='pending' AND r.typeClaim= ?1")
    List<RequestClaim> TraiterSystemReclamation(TypeClaim Package_Not_Received);
}
