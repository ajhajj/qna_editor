package com.redhat.rag.yaml.model;

public class KnowledgeSeedQandA {
    private String question = "";
    private String answer = "";

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
