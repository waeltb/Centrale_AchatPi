package com.pi.Centrale_Achat.controller;


import com.pi.Centrale_Achat.entities.Product;
import com.pi.Centrale_Achat.repositories.ProductRepo;
import com.pi.Centrale_Achat.serviceImpl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ControllerProduct {
    private final ProductServiceImpl productService;
    private final ProductRepo productRepo;

    @GetMapping("/showAll")
    public ResponseEntity< List<Product>>showAll_Products(){
        List<Product> products=productService.show_AllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/productsOfCategory/{idCategory}")
    public ResponseEntity< List<Product>>show_ProductsOf_Category(@PathVariable int idCategory){
        List<Product> products=productService.show_ProductsOf_Category(idCategory);
        return ResponseEntity.ok(products);
    }
    @GetMapping("/userProducts")
    public ResponseEntity< List<Product>>showUser_Products(){
        List<Product> products=productService.show_User_Products();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/ajouterProduit/{idCategory}/{iduser}")
    public ResponseEntity<?> save(@RequestParam("name") String name, @RequestParam("price") float price,
                                  @RequestParam("qte") int qte, @RequestParam("description") String description, @RequestParam("minStock") int minStock,
                                  @PathVariable("idCategory") int idCategory, @RequestParam("file") MultipartFile file, @PathVariable("iduser") int iduser) throws IOException {
        productService.save(name, price, qte, description, minStock, idCategory, file, iduser);

        return new ResponseEntity<>("ajouter avec success", HttpStatus.OK);
    }
    @PostMapping("/modifier/{iduser}/{idP}")
    public ResponseEntity<?> modifier(@RequestParam("name") String name, @RequestParam("price") float price,
                                      @RequestParam("description") String description, @RequestParam("minStock") int minStock,
                                      @RequestParam("file") MultipartFile file, @PathVariable("iduser") int iduser, @PathVariable("idP") int idP) throws IOException {
        productService.modifier(name, price,  description, minStock,  file, iduser,idP);

        return new ResponseEntity<>("modifier avec success", HttpStatus.OK);
    }
    @PostMapping("/updateQuantity/{idP}")
    public ResponseEntity<?> updateQuantity(@RequestBody int qte,@PathVariable("idP") int idP){
        productService.updateQuantity(qte,idP);
        return new ResponseEntity<>("Quantity modifier avec success", HttpStatus.OK);
    }
    @GetMapping(path="/findByIdImage/{id}" ,produces = MediaType.IMAGE_JPEG_VALUE)
    public  ResponseEntity<byte[]>  findByIdImage(@PathVariable(name ="id") Integer id) throws IOException {

        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(productService.findByIdImage(id));
    }

    @DeleteMapping("/idP")
    public ResponseEntity<?> delete(int idP){
        productService.delete(idP);
        return new ResponseEntity<>("produit supprimer avec success", HttpStatus.OK);
    }

}


