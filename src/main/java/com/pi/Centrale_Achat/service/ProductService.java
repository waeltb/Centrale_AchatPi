package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> show_AllProducts();
    List<Product> show_ProductsOf_Category(int idCategory);

    List<Product>show_User_Products();


    Product save(String name, float price, int qte, String description, int minStock, int idCategory, MultipartFile file, int idUser) throws IOException ;


    Product modifier(String name,  float price, String description, int minStock, MultipartFile file, int idUser,int idP) throws IOException ;
    Product updateQuantity(int qte,int idP);
    byte[] findByIdImage(int idP) throws IOException ;
    void delete(int idP);

}
