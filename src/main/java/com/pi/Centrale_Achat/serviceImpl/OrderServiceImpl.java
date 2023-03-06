package com.pi.Centrale_Achat.serviceImpl;
import com.pi.Centrale_Achat.entities.Order;
import com.pi.Centrale_Achat.entities.Product;
import com.pi.Centrale_Achat.entities.User;
import com.pi.Centrale_Achat.repositories.OrderRepo;
import com.pi.Centrale_Achat.repositories.ProductRepo;
import com.pi.Centrale_Achat.repositories.UserRepo;
import com.pi.Centrale_Achat.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orederRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;


    @Override
    public Order ajouter(Order order ,int id ,int idP) {
        User user = userRepo.findById(id).orElse(null);
        Product p = productRepo.findById(idP).orElse(null);
        if (order.getProducts()==null){
            if (p.getQte()>order.getQte()){
                p.setQte(p.getQte()-order.getQte());
                productRepo.save(p);
                order.setCode(UUID.randomUUID().toString());
                order.setUser(user);
                List<Product>products=new ArrayList<>();
                products.add(p);
                order.setProducts(products);
                orederRepo.save(order);
            }
            else {
                System.out.println("invalid qte");
            }
        }else {
            if(p.getQte()>order.getQte()){
                p.setQte(p.getQte()-order.getQte());
                order.setUser(user);
                order.getProducts().add(p);
                orederRepo.save(order);
            }
            else {
                System.out.println("invalid qte");
            }
        }
        return order;
    }
    @Override
    public void delete(int id) {
        orederRepo.deleteById(id);
    }
    @Override
    public List<Order> getAll() {
        return orederRepo.findAll();
    }
    @Override
    public int countCmdBetweenToDate(Date date1, Date date2) {
        return orederRepo.countOrdersByDatCmdBetween(date1,date2);
    }
    @Override
    public Order findOrderByDate(Date d) {

        return orederRepo.findOrderByDatCmd(d);
    }










}




