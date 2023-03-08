package com.pi.Centrale_Achat.serviceImpl;

import com.pi.Centrale_Achat.entities.Feedback;
import com.pi.Centrale_Achat.entities.Question;
import com.pi.Centrale_Achat.repositories.FeedbackRepo;
import com.pi.Centrale_Achat.repositories.QuestionRepository;
import com.pi.Centrale_Achat.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements IQuestionService{

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    FeedbackRepo feedbackRepo;



    @Override
    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }
    @Override
    public Question updateQuestion(Question question, int id) {
        question.setId(id);
        return questionRepository.save(question);
    }
    @Override
    public Question retrieveQuestion(int id) {

        return questionRepository.findById(id).get();
    }
    @Override
    public List<Question> retrieveAllQuestion() {
        List<Question> liste=(List<Question>) questionRepository.findAll();
        return liste;
    }
    @Override
    public void deleteQuestion(int id) {
        questionRepository.deleteById(id);
    }
    @Override
    public void deleteAllQuestion() {
        questionRepository.deleteAll();
    }

    @Override
    public void addandsignquestion(Question question, int id) {
        Feedback feedback=feedbackRepo.findById(id).orElse(new Feedback());
        question.setFeedback(feedback);
        questionRepository.save(question);
    }

}
