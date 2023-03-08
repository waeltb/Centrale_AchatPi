package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepo  extends JpaRepository<Rating,Integer> {

    @Query("SELECT AVG(r.score) FROM Rating r WHERE r.product.id = :IdP")
    int getAverageRatingForProduct(@Param("IdP") int IdP);
}
