package com.redhat.rad.yaml.model;

import java.util.List;

public class QnA {
    private String version;
    private String domain;
    private String created_by;
    private List<SeedExample> seed_examples;
    private String document_outline;
    private Document document;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getDocument_outline() {
        return document_outline;
    }

    public void setDocument_outline(String document_outline) {
        this.document_outline = document_outline;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public List<SeedExample> getSeed_examples() {
        return seed_examples;
    }

    public void setSeed_examples(List<SeedExample> seed_examples) {
        this.seed_examples = seed_examples;
    }

}
