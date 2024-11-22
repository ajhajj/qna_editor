package com.redhat.rad.yaml.model;

import java.util.List;

public class KnowledgeSeedExample {
    private String context = "";
    private List<KnowledgeSeedQandA> questions_and_answers;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<KnowledgeSeedQandA> getQuestions_and_answers() {
        return questions_and_answers;
    }

    public void setQuestions_and_answers(List<KnowledgeSeedQandA> questions_and_answers) {
        this.questions_and_answers = questions_and_answers;
    }


}
