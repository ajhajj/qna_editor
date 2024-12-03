package com.redhat.rad.yaml.model;

import java.util.List;

public class SkillQnA 
  {
    private int version;
    private String task_description = "";
    private String created_by = "";
    private List<SkillSeedExample> seed_examples;

    public int getVersion() 
      {
        return version;
      }

    public void setVersion(int version) 
      {
        this.version = version;
      }

    public String getTask_description() 
      {
        return task_description;
      }

    public void setTask_description(String domain) 
      {
        this.task_description = domain;
      }

    public String getCreated_by() 
      {
        return created_by;
      }

    public void setCreated_by(String created_by) 
      {
        this.created_by = created_by;
      }

    public List<SkillSeedExample> getSeed_examples() 
      {
        return seed_examples;
      }

    public void setSeed_examples(List<SkillSeedExample> seed_examples) 
      {
        this.seed_examples = seed_examples;
      }
  }
