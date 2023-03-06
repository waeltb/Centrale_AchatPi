package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.Bill;

import java.util.List;


public interface BillService {
    Bill addFacture(Bill b,int id);
    float calculeFacture(int idCmd);
    List<Bill>getAll();
    void delete(int id);
    Bill topFacture();
}
