package com.redhat.rad.yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.redhat.rad.yaml.model.QnA;

public class YamlUtil {
  public static void main(String[] args) {
    QnA qnaDoc = null;

    try {
      qnaDoc = parseYaml("/home/ajhajj/Demos/YAML/qna.yaml");
      // System.out.println(("3".equals(qnaDoc.getVersion()))?"Knowledge":"Skill");
    } catch (FileNotFoundException ex) {
      System.err.println(ex);
    }

    if (qnaDoc != null) {
      System.out.println("Version: " + qnaDoc.getVersion());
      System.out.println(encodeYaml(qnaDoc));
    }
  }

  /**
   * Returns a <code>QnA</code> document object for the specified YAML file.
   * 
   * @param filePath - the fully qualified path to the YAML file.
   * @return <code>QnA</code> object populated from the YAML file.
   * @throws FileNotFoundException
   */
  public static QnA parseYaml(String filePath) throws FileNotFoundException {
    InputStream is = null;
    QnA qnaDoc = null;
    Yaml yaml = new Yaml(new Constructor(QnA.class, new LoaderOptions()));

    is = new FileInputStream(filePath);
    qnaDoc = yaml.load(is);

    return (qnaDoc);
  }

  public static QnA parseYaml(InputStream is) throws FileNotFoundException {
    QnA qnaDoc = null;
    Yaml yaml = new Yaml(new Constructor(QnA.class, new LoaderOptions()));

    qnaDoc = yaml.load(is);

    return (qnaDoc);
  }

  public static String encodeYaml(QnA doc) {
    Yaml yaml = new Yaml();
    StringWriter writer = new StringWriter();

    yaml.dump(doc, writer);

    return (writer.toString());
  }
}
