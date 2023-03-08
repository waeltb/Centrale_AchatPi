package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Feedback;
import com.pi.Centrale_Achat.entities.SatisfactoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface FeedbackRepo extends JpaRepository<Feedback,Integer> {

    List<Feedback> findByTheme(String theme);
    Integer countBySatisfactoryStatus(SatisfactoryStatus satisfactoryStatus);
}
