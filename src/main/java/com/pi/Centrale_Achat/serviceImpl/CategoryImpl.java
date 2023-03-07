package com.pi.Centrale_Achat.serviceImpl;

import com.pi.Centrale_Achat.entities.Category;
import com.pi.Centrale_Achat.repositories.CategoryRepo;
import com.pi.Centrale_Achat.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class CategoryImpl implements CategoryService {
    private final CategoryRepo categoryRepo ;



    @Override
    public List<Category> show_All() {
        return categoryRepo.findAll();
    }

    @Override
    public Category saveCategorie(Category ca) {
        return categoryRepo.save(ca);
    }

    @Override
    public Category modifierCategorie(int idP, Category ca) {
        Category c = categoryRepo.findById(idP).orElse(null);
        c.setNameCategory(ca.getNameCategory());
        return c ;
    }

    @Override
    public void delete(int idc) {
        categoryRepo.deleteById(idc);

    }
}
