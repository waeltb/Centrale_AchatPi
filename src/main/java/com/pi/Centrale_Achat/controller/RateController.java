package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.Rating;
import com.pi.Centrale_Achat.repositories.ProductRepo;
import com.pi.Centrale_Achat.repositories.RateRepo;
import com.pi.Centrale_Achat.service.IRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rate")
public class RateController {

     private final ProductRepo productRepo;
     private final RateRepo rateRepo;

    private final  IRatingService iRatingService;

    @GetMapping("/retrieveAllRating")
    public List<Rating> retrieveAllRating() {
        List<Rating> listRating = iRatingService.retrieveAllRating();
        return listRating;
    }

    @GetMapping("/retrieveRating/{idRating}")
    public Rating retrieveRating(@PathVariable("idRating") Integer idRating) {
        return iRatingService.retrieveRating(idRating);
    }
    @DeleteMapping("/deleteRating/{idRating}")
    public void deleteRating(@PathVariable("idRating") Integer idRating) {
        iRatingService.deleteRating(idRating);
    }

    @PutMapping("/updateRating")
    public Rating updateRating(@RequestBody Rating r) {
        Rating rating= iRatingService.updateRating(r);
        return rating;
    }
    @PostMapping("/addRating/{idP}")
    public int addRating(@RequestBody Rating r, @PathVariable("idP") int idP)
    {
        return iRatingService.addRating(r,idP);
    }
}
