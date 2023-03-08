package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;

public interface ProductService {
    List<Product> show_AllProducts();
    List<Product> show_ProductsOf_Category(int idCategory);

    List<Product>show_User_Products(UserDetails userDetails);



    Product save(UserDetails userDetails,String name, float price, int qte, String description, int minStock, int idCategory, MultipartFile file) throws IOException ;



    Product modifier(UserDetails userDetails,String name,  float price, String description, int minStock, MultipartFile file,int idP) throws IOException ;
    Product updateQuantity(UserDetails userDetailsint ,int qte,int idP);
    byte[] findByIdImage(int idP) throws IOException ;
    void delete(UserDetails userDetails,int idP);

}
