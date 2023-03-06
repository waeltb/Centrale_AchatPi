package com.pi.Centrale_Achat.controller;
import com.pi.Centrale_Achat.entities.Order;
import com.pi.Centrale_Achat.serviceImpl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class ControllerOrder {
    private final OrderServiceImpl orderService;

    @PostMapping("/add/{id}/{idP}")
    public Order add(@RequestBody Order o, @PathVariable("id") int id, @PathVariable("idP") int idP) {
        return orderService.ajouter(o, id, idP);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        orderService.delete(id);
    }

    @GetMapping("/count/{d1}/{d2}")
    public int countCmd(@PathVariable("d1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date d1,
                        @PathVariable("d2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date d2) {
        return orderService.countCmdBetweenToDate(d1, d2);
    }
    @GetMapping("/get/{d}")
    public Order getOrderByDate(@PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM-dd") Date d) {
        return orderService.findOrderByDate(d);
    }
    @GetMapping("/getAll")
    public List<Order> getAll() {
        return orderService.getAll();
    }

}
