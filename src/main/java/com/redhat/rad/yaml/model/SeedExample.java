package com.redhat.rad.yaml.model;

import java.util.List;

public class SeedExample {
    private String context;
    private List<SeedQandA> questions_and_answers;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<SeedQandA> getQuestions_and_answers() {
        return questions_and_answers;
    }

    public void setQuestions_and_answers(List<SeedQandA> questions_and_answers) {
        this.questions_and_answers = questions_and_answers;
    }


}
