package com.pi.Centrale_Achat.service;

import com.pi.Centrale_Achat.entities.Question;

import java.util.List;

public interface IQuestionService {

    public Question addQuestion(Question question);
    public Question updateQuestion(Question question, int id);
    public Question retrieveQuestion(int id);
    public List<Question> retrieveAllQuestion();
    public void deleteQuestion(int id);
    public void deleteAllQuestion();
    public void addandsignquestion(Question question, int id);
}
