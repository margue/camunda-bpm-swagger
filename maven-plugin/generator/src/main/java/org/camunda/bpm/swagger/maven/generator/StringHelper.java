package org.camunda.bpm.swagger.maven.generator;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

public class StringHelper {

  public static String toFirstUpper(final String text) {
    if (text == null) {
      return null;
    }
    return text.substring(0, 1).toUpperCase() + text.substring(1);
  }

  public static String toFirstLower(final String text) {
    if (text == null) {
      return null;
    }
    return text.substring(0, 1).toLowerCase() + text.substring(1);
  }

  public static String camelize(final String value) {
    if (value == null || !value.contains("-")) {
      return value;
    }
    final StringTokenizer token = new StringTokenizer(value, "-");
    final StringBuilder str = new StringBuilder(token.nextToken());
    while (token.hasMoreTokens()) {
      final String s = token.nextToken();
      str.append(Character.toUpperCase(s.charAt(0))).append(s.substring(1));
    }
    return str.toString();
  }

  public static String splitCamelCase(final String s) {
    return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
  }

  public static String getFieldnameFromGetter(final String methodName) {
    return WordUtils.uncapitalize(StringUtils.removeStart(methodName, "get"));
  }

  /**
   * Returns a first sentence of the text if found.
   *
   * @param description
   *          text.
   * @return text until first dot or the entire text if no dot found.
   */
  public static String firstSentence(final String description) {
    if (description == null) {
      return null;
    }

    final int indexOf = description.indexOf(".");
    return indexOf > 0 ? description.substring(0, indexOf) : description;

  }
}
