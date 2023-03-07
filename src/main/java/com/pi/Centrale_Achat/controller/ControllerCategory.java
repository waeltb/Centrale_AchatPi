package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.Category;
import com.pi.Centrale_Achat.serviceImpl.CategoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class ControllerCategory {
    private final CategoryImpl categoryservice;

    @GetMapping("/")
    public ResponseEntity<?> index(){

        return ResponseEntity.ok("hiiiiiiiiii");
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Category>> index_Category(){

        return ResponseEntity.ok(categoryservice.show_All());
    }
    @PostMapping("/saveCategorie")
    public ResponseEntity<?> saveCategorie(@RequestBody Category ca){
        categoryservice.saveCategorie(ca);
        return new ResponseEntity<>("Category ajouter avec success", HttpStatus.OK);
    }
    @PostMapping("/modifierCategorie/{idP}")
    public ResponseEntity<?>  modifierCategorie(@PathVariable int idP,@RequestParam Category ca){
        categoryservice.modifierCategorie(idP,ca);
        return new ResponseEntity<>("Category modifier avec success", HttpStatus.OK);

    }
    @DeleteMapping("/delete/{idc}")
    public ResponseEntity<?> delete(@PathVariable int idc){
        categoryservice.delete(idc);
        return new ResponseEntity<>("Category supprimer avec success", HttpStatus.OK);
    }

}
