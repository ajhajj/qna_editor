package com.redhat.rad.yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.microprofile.config.ConfigProvider;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.LineBreak;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import com.redhat.rad.yaml.model.Document;
import com.redhat.rad.yaml.model.KnowledgeQnA;
import com.redhat.rad.yaml.model.KnowledgeSeedExample;
import com.redhat.rad.yaml.model.KnowledgeSeedQandA;
import com.redhat.rad.yaml.model.SkillQnA;
import com.redhat.rad.yaml.model.SkillSeedExample;

public class YamlUtil 
  {
    public static void main(String[] args) 
      {
      //  KnowledgeQnA qnaDoc = null;
      //  String stubFile = null;

        //stubFile = getConfig("servlet.FileUploadServlet.qna.stubfile");
        String file = "domain: time_travel\n" + //
                    "version: 3\n" + //
                    "domain: time_travel\n" + //
                    "created_by: Grant Shipley\n" + //
                    "seed_examples:\n" + //
                    "  - context: |\n" + //
                    "      The DeLorean DMC-12 is a sports car manufactured by John DeLorean's DeLorean Motor Company\n" + //
                    "      for the American market from 1981 to 1983. The car features gull-wing doors and a stainless-steel body.\n" + //
                    "      It gained fame for its appearance as the time machine in the \"Back to the Future\" film trilogy.\n" + //
                    "    questions_and_answers:\n" + //
                    "      - question: |\n" + //
                    "          When was the DeLorean manufactured?\n" + //
                    "        answer: |\n" + //
                    "          The DeLorean was manufactured from 1981 to 1983.\n" + //
                    "      - question: |\n" + //
                    "          Who manufactured the DeLorean DMC-12?\n" + //
                    "        answer: |\n" + //
                    "          The DeLorean Motor Company manufactured the DeLorean DMC-12.";
        System.out.println(getVersion(file));            
/* 
        try 
          {
            qnaDoc = (KnowledgeQnA)parseYaml(KnowledgeQnA.class, stubFile);
          } 
        catch (FileNotFoundException ex) 
          {
            System.err.println(ex);
          }

        if (qnaDoc != null) 
          {
            System.out.println("Version: " + qnaDoc.getVersion());
            System.out.println(encodeYaml(qnaDoc));
          }
*/
      }

    /**
     * Lookup config values from application.properties.
     * 
     * @param key String key value
     * @return resolved value or empty String
     */
    protected static String getConfig(String key)
      {
        String value = "";

        try
          {
            if((key != null) && (key.trim().length() > 0))
              value = ConfigProvider.getConfig().getValue(key, String.class);
          }
        catch(NoSuchElementException ex)
          {
          }

        return(value);
      }
 
    public static KnowledgeQnA createEmptyKQnA() throws FileNotFoundException 
      {
        KnowledgeQnA qnaDoc = null;
        KnowledgeSeedExample seedEx = null;
        List<KnowledgeSeedExample> seedExList = new ArrayList<KnowledgeSeedExample>();
        List<KnowledgeSeedQandA> seedQandAList = null;
        Document doc = null;
        List<String> docPatterns = null;

        qnaDoc = new KnowledgeQnA();

        doc = new Document();
        docPatterns = new ArrayList<String>();
        docPatterns.add("");
        doc.setPatterns(docPatterns);
        qnaDoc.setDocument(doc);

        for(int i=0; i<5; i++)
          {
            seedEx = new KnowledgeSeedExample();
            seedQandAList = new ArrayList<KnowledgeSeedQandA>();
            for(int j=0; j<3; j++)
              seedQandAList.add(new KnowledgeSeedQandA());

            seedEx.setQuestions_and_answers(seedQandAList);
            seedExList.add(seedEx);
          }
        
          qnaDoc.setSeed_examples(seedExList);
    
        return (qnaDoc);
      }
  
      public static SkillQnA createEmptySQnA() throws FileNotFoundException 
      {
        SkillQnA qnaDoc = null;
        SkillSeedExample seedEx = null;
        List<SkillSeedExample> seedExList = new ArrayList<SkillSeedExample>();

        qnaDoc = new SkillQnA();

        for(int i=0; i<5; i++)
          {
            seedEx = new SkillSeedExample();
            seedExList.add(seedEx);
          }
        
          qnaDoc.setSeed_examples(seedExList);
    
        return (qnaDoc);
      }
  
    /**
     * Returns a <code>QnA</code> document object for the specified YAML file.
     * 
     * @param filePath - the fully qualified path to the YAML file.
     * @return <code>QnA</code> object populated from the YAML file.
     * @throws FileNotFoundException
     */
    private static Object parseYamlFromFile(Class<? extends Object> theRoot, String filePath) throws FileNotFoundException 
      {
        InputStream is = null;
        Yaml yaml = new Yaml(new Constructor(theRoot, new LoaderOptions()));

        is = new FileInputStream(filePath);
        return(yaml.load(is));
      }

    public static KnowledgeQnA parseKnowledgeYamlFromFile(String filePath) throws FileNotFoundException 
      {
        return((KnowledgeQnA)parseYamlFromFile(KnowledgeQnA.class, filePath));
      }
  
    public static KnowledgeQnA parseKnowledgeYamlFromString(String yamlString) throws FileNotFoundException 
      {
        return((KnowledgeQnA)parseYamlFromString(KnowledgeQnA.class, yamlString));
      }

    public static SkillQnA parseSkillYamlFromFile(String filePath) throws FileNotFoundException 
      {
        return((SkillQnA)parseYamlFromFile(SkillQnA.class, filePath));
      }

    public static SkillQnA parseSkillYamlFromString(String yamlString) throws FileNotFoundException 
      {
        return((SkillQnA)parseYamlFromString(SkillQnA.class, yamlString));
      }

    private static Object parseYamlFromString(Class<? extends Object> theRoot, String yamlString) throws FileNotFoundException 
      {
        Yaml yaml = new Yaml(new Constructor(theRoot, new LoaderOptions()));

        return(yaml.load(yamlString));
      }

    private static Object parseYamlFromInputStream(Class<? extends Object> theRoot, InputStream is) throws FileNotFoundException 
      {
        Yaml yaml = new Yaml(new Constructor(theRoot, new LoaderOptions()));

        return(yaml.load(is));
      }

    public static KnowledgeQnA parseKnowledgeYamlFromInputStream(InputStream is) throws FileNotFoundException 
      {
        return((KnowledgeQnA)parseYamlFromInputStream(KnowledgeQnA.class, is));
      }

    public static SkillQnA parseSkillYamlFromInputStream(InputStream is) throws FileNotFoundException 
      {
        return((SkillQnA)parseYamlFromInputStream(SkillQnA.class, is));
      }

    public static String encodeYaml(Object doc) 
      {
        Yaml yaml = null;
        StringWriter writer = new StringWriter();
        DumperOptions options = null;
        Representer representer = null;

        options = new DumperOptions();
        options.setIndent(2);
        options.setIndicatorIndent(2);
        options.setIndentWithIndicator(true);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setWidth(90);
        options.setDefaultScalarStyle(DumperOptions.ScalarStyle.PLAIN);
        options.setLineBreak(LineBreak.getPlatformLineBreak());
        options.setSplitLines(true);
        
        representer = new Representer(options);
        representer.addClassTag(doc.getClass(), Tag.MAP); 
        yaml = new Yaml(representer, options);
        yaml.dump(doc, writer);

        return(writer.toString());
      }
    
            
    private static String getVersion(String file)
      {
        String match = "version:";
        String value = null;
        int startIndex, endIndex;

        startIndex = (file.trim()).indexOf(match);
        if(startIndex != -1)
          {
            endIndex = (file.trim()).indexOf("\n", startIndex);
            if(endIndex != -1)
              value = file.substring(startIndex + match.length(), endIndex).trim();
          }

        return(value);
      }

    public static void pruneEmptyQnA(KnowledgeQnA qna)
      {
        KnowledgeSeedExample example = null;
        KnowledgeSeedQandA seedQna = null;
        List<KnowledgeSeedQandA> seedQnaList = null;
        List<KnowledgeSeedExample> exampleList = null;
        Iterator<KnowledgeSeedQandA> seedIt = null;
        Iterator<KnowledgeSeedExample> it = null;
        String value = null;
        boolean isEmpty;
        boolean isSeedEmpty;

        if(qna != null)
          {
            qna.getSeed_examples();
            exampleList = qna.getSeed_examples();
            it = exampleList.iterator();
            while(it.hasNext())
              {
                example = it.next();
                isEmpty = true;
                value = example.getContext();
                isEmpty = (isEmpty && "".equals(value));
                seedQnaList = example.getQuestions_and_answers();
                seedIt = seedQnaList.iterator();

                while(seedIt.hasNext())
                  {
                    seedQna = seedIt.next();
                    isSeedEmpty = true;
                    value = seedQna.getQuestion();
                    isSeedEmpty = (isSeedEmpty && "".equals(value));
                    value = seedQna.getAnswer();
                    isSeedEmpty = (isSeedEmpty && "".equals(value));

                    if(isSeedEmpty)
                      seedIt.remove();
                  }

                isEmpty = (isEmpty && (seedQnaList.size() == 0));
                if(isEmpty)
                  it.remove();
              }

          }
      }

    public static void pruneEmptyQnA(SkillQnA qna)
      {
        SkillSeedExample example = null;
        List<SkillSeedExample> exampleList = null;
        Iterator<SkillSeedExample> it = null;
        String value = null;
        boolean isEmpty;

        if(qna != null)
          {
            exampleList = qna.getSeed_examples();
            it = exampleList.iterator();
            while(it.hasNext())
              {
                example = it.next();
                isEmpty = true;
                value = example.getContext();
                isEmpty = (isEmpty && "".equals(value));
                value = example.getQuestion();
                isEmpty = (isEmpty && "".equals(value));
                value = example.getAnswer();
                isEmpty = (isEmpty && "".equals(value));
                if(isEmpty)
                  it.remove();
              }
          }
      }

    public static void padQnA(SkillQnA qna)
      {
        List<SkillSeedExample> exampleList = null;

        if(qna != null)
          {
            exampleList = qna.getSeed_examples();
            
            for(int i=exampleList.size(); i<5; i++)
              exampleList.add(new SkillSeedExample());
          }
      }

    public static void padQnA(KnowledgeQnA qna)
      {
        KnowledgeSeedExample example = null;
        List<KnowledgeSeedQandA> seedQnaList = null;
        List<KnowledgeSeedExample> exampleList = null;
        Iterator<KnowledgeSeedExample> it = null;

        if(qna != null)
          {
            exampleList = qna.getSeed_examples();
            it = exampleList.iterator();

            while(it.hasNext())
              {
                seedQnaList = it.next().getQuestions_and_answers();

                // add KnowledgeSeedQandA instances so that we pad up to 3
                for(int seedQnaCount=seedQnaList.size(); seedQnaCount<3; seedQnaCount++)
                  seedQnaList.add(new KnowledgeSeedQandA());
              }
            
            // add KnowledgeSeedExample instances so that we pad up to 5
            for(int i=exampleList.size(); i<5; i++)
              {
                seedQnaList = new ArrayList<KnowledgeSeedQandA>(3);
                for(int j=0; j<3; j++)
                  seedQnaList.add(new KnowledgeSeedQandA());

                example = new KnowledgeSeedExample();
                example.setQuestions_and_answers(seedQnaList);
                exampleList.add(example);
              }
              
          }
      }
  }
