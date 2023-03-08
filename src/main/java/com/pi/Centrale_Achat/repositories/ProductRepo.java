package com.pi.Centrale_Achat.repositories;

import com.pi.Centrale_Achat.entities.Product;
import com.pi.Centrale_Achat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product>findByUser(User user);
}
