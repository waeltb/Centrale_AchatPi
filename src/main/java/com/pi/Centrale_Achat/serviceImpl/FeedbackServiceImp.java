package com.pi.Centrale_Achat.serviceImpl;

import com.pi.Centrale_Achat.entities.Feedback;
import com.pi.Centrale_Achat.entities.SatisfactoryStatus;
import com.pi.Centrale_Achat.repositories.FeedbackRepo;
import com.pi.Centrale_Achat.repositories.ProductRepo;
import com.pi.Centrale_Achat.repositories.QuestionRepository;
import com.pi.Centrale_Achat.service.IFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FeedbackServiceImp implements IFeedbackService {

    @Autowired
    FeedbackRepo feedbackRepo;
    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ProductRepo productRepo;




    @Override
    public Feedback addFeedback(Feedback feedback) {
        return feedbackRepo.save(feedback);
    }

    @Override
            public Feedback updateFeedback(Feedback feedback, int id) {
        feedback.setId(id);
        return feedbackRepo.save(feedback);
    }

    @Override
    public Feedback retrieveFeedback(int id) {
        return feedbackRepo.findById(id).get();
    }

    @Override
    public List<Feedback> retrieveAllFeedback() {
        List<Feedback> liste = (List<Feedback>) feedbackRepo.findAll();
        return liste;
    }

    @Override
    public void deleteFeedback(int id) {
        feedbackRepo.deleteById(id);
    }

    @Override
    public void deleteAllFeedback() {
        feedbackRepo.deleteAll();
    }


    @Override
    public List<Feedback> findByTheme(String Theme) {
        return feedbackRepo.findByTheme(Theme);
    }




       public Map<SatisfactoryStatus, Integer> countUsersBySatisfactoryStatus() {
       Map<SatisfactoryStatus, Integer> counts = new HashMap<>();
        counts.put(SatisfactoryStatus.satisfied, feedbackRepo.countBySatisfactoryStatus(SatisfactoryStatus.satisfied));
        counts.put(SatisfactoryStatus.not_satisfied, feedbackRepo.countBySatisfactoryStatus(SatisfactoryStatus.not_satisfied));
      return counts;
   }



}
