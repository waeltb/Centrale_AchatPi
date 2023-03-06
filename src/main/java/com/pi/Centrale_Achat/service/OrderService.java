package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.Order;

import java.util.Date;
import java.util.List;

public interface OrderService {
    Order ajouter(Order order,int id,int idP);
    void delete(int id);
    List<Order>getAll();
    int countCmdBetweenToDate(Date date1,Date date2);
    Order findOrderByDate(Date d);
}
