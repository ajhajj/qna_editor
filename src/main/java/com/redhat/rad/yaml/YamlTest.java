package com.redhat.rad.yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.redhat.rad.yaml.model.QnA;

public class YamlTest 
  {
    public static void main(String[] args)
      {
        QnA qnaDoc = null;

        try
          {
            qnaDoc = parseYaml("/home/ajhajj/Demos/YAML/qna.yaml");
          }
        catch(FileNotFoundException ex)
          {
            System.err.println(ex);
          }

        if(qnaDoc != null)
          {
            System.out.println("Version: " + qnaDoc.getVersion());
          }
      }
    
    /**
     * Returns a <code>QnA</code> document object for the specified YAML file.
     * 
     * @param filePath - the fully qualified path to the YAML file.
     * @return <code>QnA</code> object populated from the YAML file.
     * @throws FileNotFoundException
     */
    public static QnA parseYaml(String filePath) throws FileNotFoundException
      {
        InputStream is = null;
        QnA qnaDoc = null;
        Yaml yaml = new Yaml(new Constructor(QnA.class, new LoaderOptions()));

        is = new FileInputStream(filePath);
        qnaDoc = yaml.load(is);

        return(qnaDoc);
      }
  }
