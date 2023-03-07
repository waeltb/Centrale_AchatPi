package com.pi.Centrale_Achat.serviceImpl;
import com.pi.Centrale_Achat.entities.*;
import com.pi.Centrale_Achat.repositories.*;
import com.pi.Centrale_Achat.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.Date;
import java.util.List;
;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final UserRepo userRepo;
    private final StockRepo mvStockRepo;
    private final OrderRepo orderRepo;
    private final CategoryRepo categoryRepo;
//    @Autowired
//    private AlertService alertService;
//    @Autowired
//    private NotifierClient notifierClient;
    @Autowired
    private TenderRepo tenderRepo;
    @Override
    public List<Product> show_AllProducts() {
        return productRepo.findAll();
    }
    @Override
    public List<Product> show_ProductsOf_Category(int idCategory) {
        Category category = categoryRepo.findById(idCategory).get();
        return category.getProducts();
    }
    @Override
    public List<Product> show_User_Products() {
        int authUser = 1;
        User fakeUser = userRepo.findById(authUser).get();
        return fakeUser.getProducts();
    }

    @Override
    public Product save(@AuthenticationPrincipal UserDetails userDetails, String name, float price, int qte, String description, int minStock, int idCategory, MultipartFile file) throws IOException {
        String currentUser = userDetails.getUsername();
        User user1 = userRepo.findUserByUsername(currentUser);
        Product p = new Product();
        p.setImage(file.getOriginalFilename());
        Files.copy(file.getInputStream(), Paths.get("src/main/resources/imageProduct/" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        p.setDescription(description);
        p.setName(name);
        p.setPrice(price);
        p.setMinStock(minStock);
        p.setCategory(categoryRepo.findById(idCategory).orElse(null));
        p.setQte(qte);
        p.setUser(user1);
        productRepo.save(p);
        MvStock mvStk = new MvStock();
        mvStk.setProduct(p);
        mvStk.setDateMvt(new Date());
        mvStk.setTypeMvt(TypeMvStock.entree);
        mvStk.setQuantiteMvt(qte);
        mvStockRepo.save(mvStk);
        return p;      }
    @Override
    public Product modifier(String name, float price, String description, int minStock, MultipartFile file, int idUser, int idP) throws IOException {

        Product p = productRepo.findById(idP).orElse(null);
        Files.copy(file.getInputStream(), Paths.get("src/main/resources/imageProduct/" + file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setMinStock(minStock);
        p.setImage(file.getOriginalFilename());
        p.setUser(userRepo.findById(idUser).orElse(null));
        productRepo.save(p);


        return p;

    }
    @Override
    public Product updateQuantity(int qte, int idP) {
        Product p = productRepo.findById(idP).orElse(null);
        p.setQte(qte);
        productRepo.save(p);
        MvStock mvStock = new MvStock();
        mvStock.setProduct(p);
        mvStock.setQuantiteMvt(qte);
        mvStock.setTypeMvt(TypeMvStock.entree);
        mvStock.setDateMvt(new Date());
        mvStockRepo.save(mvStock);
        return p;
    }
    @Override
    public byte[] findByIdImage(int idP) throws IOException {

        Product p = productRepo.findById(idP).orElse(null);
        String photoName = p.getImage();

        // var imgFile = new ClassPathResource("imageProduct/" + photoName);

        ClassPathResource resource = new ClassPathResource("imageProduct/c1.PNG");
        InputStream inputStream = resource.getInputStream();

        // byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        byte[] bytes = StreamUtils.copyToByteArray(inputStream);
        return bytes;
    }
    @Override
    public void delete(int idP) {
        productRepo.deleteById(idP);
    }
}