package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.Category;

import java.util.List;

public interface CategoryService {
    List<Category> show_All();
    Category saveCategorie(Category ca);
    Category modifierCategorie(int idP,Category ca);
    void delete(int idc);
}
