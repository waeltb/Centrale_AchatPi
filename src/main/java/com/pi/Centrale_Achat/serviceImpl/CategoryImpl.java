package com.pi.Centrale_Achat.serviceImpl;

import com.pi.Centrale_Achat.entities.Category;

import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.CategoryRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {
    private final CategoryRepo categoryRepo ;


    private  final UserRepo userRepo;



    @Override
    public List<Category> show_All(@AuthenticationPrincipal UserDetails userDetails) {
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        return categoryRepo.findByUser(user1);
    }

    @Override
    public Category saveCategorie(@AuthenticationPrincipal UserDetails userDetails, Category ca) {
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        ca.setUser(user1);

        return categoryRepo.save(ca);
    }

    @Override

    public Category modifierCategorie(@AuthenticationPrincipal UserDetails userDetails, Category ca, int id) {
        Category cat = categoryRepo.findById(id).orElse(null);
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        Category category = new Category();

        if (cat.getUser().getId()==user1.getId()){
            cat.setNameCategory(ca.getNameCategory());
        categoryRepo.save(cat);
        }
        return cat;

    }

    @Override
    public void delete(@AuthenticationPrincipal UserDetails userDetails,int idc) {
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        List<Category>categories = categoryRepo.findAll();
        for (Category category : categories){
            if (category.getUser().getId()==user1.getId()){
                categoryRepo.deleteById(idc);
            }
        }

    }
}
