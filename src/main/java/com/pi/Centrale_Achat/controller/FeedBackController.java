package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.Feedback;
import com.pi.Centrale_Achat.entities.SatisfactoryStatus;
import com.pi.Centrale_Achat.service.IFeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feedback")
public class FeedBackController {



    private final IFeedbackService feedbackService;





    @PostMapping("/addFeedback")
    public  Feedback addFeedback(@RequestBody Feedback feedback) {
        return feedbackService.addFeedback(feedback);
    }
    @PutMapping("/updateFeedback/{id}")
    public Feedback updateQuiz(@RequestBody Feedback feedback,@RequestParam("id") int id){
        return feedbackService.updateFeedback(feedback, id);
    }
    @GetMapping("/getallFeedback")
    public List<Feedback> retrieveAllFeedback() {
        return feedbackService.retrieveAllFeedback();
    }
    @GetMapping("/getfeedback/{id}")
    public Feedback retrieveFeedback(@RequestParam("id") int id) {
        return feedbackService.retrieveFeedback(id);
    }
    @DeleteMapping("/deleteFeedback/{id}")
    public void deleteFeedback( @RequestParam("id") int id) {
        feedbackService.deleteFeedback(id);
    }
    @DeleteMapping("/deleteallfeedback")
    public void deleteAllFeedback() {
        feedbackService.deleteAllFeedback();
    }


   @GetMapping("/countUsersBySatisfactoryStatus")
    public Map<SatisfactoryStatus, Integer> countUsersBySatisfactoryStatus(){
        return feedbackService.countUsersBySatisfactoryStatus();
    }


}
