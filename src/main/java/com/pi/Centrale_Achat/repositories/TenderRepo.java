package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Bill;
import com.pi.Centrale_Achat.entities.Tender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenderRepo extends JpaRepository<Tender,Integer> {
}
