package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.Rating;

import java.util.List;

public interface IRatingService {
    List<Rating> retrieveAllRating();

    int addRating (Rating r, int idP);

    Rating updateRating (Rating r);

    Rating retrieveRating(Integer idRating);


    void deleteRating(Integer idRating);
}
