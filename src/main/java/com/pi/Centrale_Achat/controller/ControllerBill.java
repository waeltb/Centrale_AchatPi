package com.pi.Centrale_Achat.controller;
import com.pi.Centrale_Achat.entities.Bill;
import com.pi.Centrale_Achat.serviceImpl.BillServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("bill")
@RequiredArgsConstructor
public class ControllerBill {
    private final BillServiceImpl billService;

    @GetMapping("/gett")
    public Bill gett(){
        return billService.topFacture();
    }
    @GetMapping("/getAll")
    public List<Bill> getAll(){
        return billService.getAll();
    }

    @PostMapping("add/{id}")
    public Bill add(@RequestBody Bill b , @PathVariable("id")int id){
        return billService.addFacture(b, id);

    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id")int id){
        billService.delete(id);
    }



}
