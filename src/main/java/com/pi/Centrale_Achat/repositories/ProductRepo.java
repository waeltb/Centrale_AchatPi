package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
