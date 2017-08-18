package org.camunda.bpm.extension.swagger.generator.fn;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.JavaFileObject;

import org.apache.commons.lang3.StringUtils;

import com.helger.jcodemodel.AbstractCodeWriter;
import com.helger.jcodemodel.JPackage;

/**
 * @see "https://github.com/mbenson/annotation-processing-support/blob/master/src/main/java/mbenson/annotationprocessing/CodeModelProcessorBase.java"
 */
public class CodeWriter extends AbstractCodeWriter {

  private static final String JAVA_FILE_EXTENSION = ".java";
  private ProcessingEnvironment env;

  public CodeWriter(ProcessingEnvironment env) {
    super(Charset.forName("UTF-8"), System.getProperty("line.separator"));
    this.env = env;
  }

  @Override
  public OutputStream openBinary(JPackage pkg, String filename) throws IOException {

    String fqcn = StringUtils.join(pkg.name(), ".", StringUtils.removeEnd(filename, JAVA_FILE_EXTENSION));
    JavaFileObject sourceFile = env.getFiler().createSourceFile(fqcn);
    return sourceFile.openOutputStream();
  }

  @Override
  public void close() throws IOException {

  }

}
