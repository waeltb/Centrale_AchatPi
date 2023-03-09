package com.pi.Centrale_Achat.controller;

import com.pi.Centrale_Achat.entities.Question;
import com.pi.Centrale_Achat.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    IQuestionService quesService;


    @PostMapping("/addQuestion")
    public Question addQuestion(@RequestBody Question question) {
        return quesService.addQuestion(question);

    }
    @PutMapping("/updateQuestion/{id}")
    public Question updateQuestion(@RequestBody Question question,@RequestParam("id") int id){
        return quesService.updateQuestion(question, id);
    }
    @GetMapping("/getQuestion/{id}")
    public Question retrieveQuestion(@PathVariable("id")int id){
        return quesService.retrieveQuestion(id);
    }
    @GetMapping("/getallQuestion")
    public List<Question> retrieveAllQuestion(){
        return quesService.retrieveAllQuestion();
    }
    @PostMapping("/signaddQuestion/{idFeedback}")
    public void addandsignquestion(@RequestBody Question question,@RequestParam("id") int idFeedback){
        quesService.addandsignquestion(question, idFeedback);
    }



}
