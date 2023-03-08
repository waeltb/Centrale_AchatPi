package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.Feedback;
import com.pi.Centrale_Achat.entities.SatisfactoryStatus;

import java.util.List;
import java.util.Map;

public interface IFeedbackService {

    public Feedback addFeedback(Feedback feedback);
    public Feedback updateFeedback(Feedback feedback, int id);
    public Feedback retrieveFeedback(int id);
    public List<Feedback> retrieveAllFeedback();
    public void deleteFeedback(int id);
    public void deleteAllFeedback();

    public List<Feedback> findByTheme(String Theme);

    public Map<SatisfactoryStatus, Integer> countUsersBySatisfactoryStatus();

}
