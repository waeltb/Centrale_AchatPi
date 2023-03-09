package com.pi.Centrale_Achat.controller;


import com.pi.Centrale_Achat.dto.DiscountDto;
import com.pi.Centrale_Achat.entities.Product;

import com.pi.Centrale_Achat.entities.Tender;
import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.ProductRepo;
import com.pi.Centrale_Achat.repositories.TenderRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;

import com.pi.Centrale_Achat.repositories.ProductRepo;

import com.pi.Centrale_Achat.serviceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RestController

@RequestMapping("/api/product")

@RequiredArgsConstructor
public class ControllerProduct {
    private final ProductServiceImpl productService;
    private final ProductRepo productRepo;


    private final UserRepo userRepo;
    private final TenderRepo tenderRepo;



    @GetMapping("/showAll")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity< List<Product>>showAll_Products(){
        List<Product> products=productService.show_AllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/productsOfCategory/{idCategory}")
    @PreAuthorize("hasRole('CUSTOMER')")

    public ResponseEntity< List<Product>>show_ProductsOf_Category(@PathVariable int idCategory){
        List<Product> products=productService.show_ProductsOf_Category(idCategory);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/userProducts")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<?>showUser_Products(@AuthenticationPrincipal UserDetails userDetails){
        String currentUserName = userDetails.getUsername();
        User currentUser = userRepo.findUserByUsername(currentUserName);
        if (currentUser == null) {
            return new ResponseEntity<>(Collections.emptyList(),HttpStatus.NOT_FOUND) ;
        } else {
            return new ResponseEntity<>(productService.show_User_Products(userDetails),HttpStatus.OK) ;
        }
    }



    @PostMapping("/ajouterProduit/{idCategory}")
    @PreAuthorize("hasRole('SUPPLIER')")

    public ResponseEntity<?> save(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("name") String name, @RequestParam("price") float price,
                                  @RequestParam("qte") int qte, @RequestParam("description") String description, @RequestParam("minStock") int minStock,
                                  @PathVariable("idCategory") int idCategory, @RequestParam("file") MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepo.findUserByUsername(currentUserName);
        if (currentUser==null) {
            System.out.println("Vous devez se connecter");
        }
        productService.save(userDetails,name, price, qte, description, minStock, idCategory, file);

        return new ResponseEntity<>("ajouter avec success", HttpStatus.OK);
    }


    @PutMapping("/modifier/{idP}")
    @PreAuthorize("hasRole('SUPPLIER')")
    public ResponseEntity<?> modifier(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("name") String name, @RequestParam("price") float price,
                                      @RequestParam("description") String description, @RequestParam("minStock") int minStock,
                                      @RequestParam("file") MultipartFile file
            , @PathVariable("idP") int idP) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepo.findUserByUsername(currentUserName);
        if (currentUser==null) {
            System.out.println("Vous devez se connecter");
        }
        productService.modifier(userDetails,name, price,  description, minStock,  file,idP);

        return new ResponseEntity<>("modifier avec success", HttpStatus.OK);
    }

    @PutMapping("/updateQuantity/{idP}")
    @PreAuthorize("hasRole('SUPPLIER')or hasRole('OPERATOR')")
    public ResponseEntity<?> updateQuantity(@AuthenticationPrincipal UserDetails userDetails,@RequestParam int qte,@PathVariable("idP") int idP){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = userRepo.findUserByUsername(currentUserName);
        if (currentUser==null) {
            System.out.println("Vous devez se connecter");
        }
        productService.updateQuantity(userDetails,qte,idP);

        return new ResponseEntity<>("modifier avec success", HttpStatus.OK);
    }

    @GetMapping(path="/findByIdImage/{id}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    @PreAuthorize("hasRole('CUSTOMER')")
    public  ResponseEntity<byte[]>  findByIdImage(@PathVariable(name ="id") Integer id) throws IOException {

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(productService.findByIdImage(id));
    }
    @PreAuthorize("hasRole('SUPPLIER')or hasRole('OPERATOR')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal UserDetails userDetails,@PathVariable("id")int idP){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Product product = productRepo.findById(idP).orElse(null);
        if (product != null && (currentUsername.equals(product.getUser().getUsername()))) {
            productService.delete(userDetails,idP);
            return new ResponseEntity<>("produit supprimer avec success", HttpStatus.OK);
        } else {
            throw new AccessDeniedException("You are not authorized to delete this order");
        }

    }

    @PutMapping("/applyDiscount/{idProduct}")
    @PreAuthorize("hasRole('SUPPLIER')")

    public ResponseEntity<?> apply_discount(@AuthenticationPrincipal UserDetails userDetails ,@PathVariable int idProduct,@RequestBody DiscountDto discountDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        Product product = productRepo.findById(idProduct).orElse(null);
        if (product != null && (currentUsername.equals(product.getUser().getUsername()))) {
            return new ResponseEntity<>( productService.apply_discount(userDetails,idProduct,discountDto), HttpStatus.OK);}
     else
    {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);

    }}

    @PostMapping("/provideTender/{idTender}")
    @PreAuthorize("hasRole('OPERATOR')")
    public ResponseEntity<?> provide_Tender(@AuthenticationPrincipal UserDetails userDetails ,@RequestParam String name,@RequestParam float price, @RequestParam int qte
            ,@RequestParam String description,@RequestParam int minStock, @RequestParam MultipartFile file,@PathVariable int idTender)throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            productService.provide_Tender(userDetails,name, price, qte, description, minStock,  file,idTender);
            return ResponseEntity.ok("tender ajouté avec success");




    }

}


