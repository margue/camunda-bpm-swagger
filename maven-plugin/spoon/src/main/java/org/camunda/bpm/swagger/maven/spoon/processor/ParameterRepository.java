package org.camunda.bpm.swagger.maven.spoon.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.tuple.Pair;

import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ParameterRepository {
  private static Map<String, Pair<Class<?>, String>> predefinedParameters = new HashMap<>();

  static {
    predefinedParameters.put(UriInfo.class.getName(), Pair.of(UriInfo.class, "uriInfo"));
    predefinedParameters.put(ObjectMapper.class.getName(), Pair.of(ObjectMapper.class, "objectMapper"));
  }

  public static Optional<Pair<Class<?>, String>> lookup(final Parameter param) {
    final Pair<Class<?>, String> pair = predefinedParameters.get(param.getType().getName());
    if (pair != null) {
      return Optional.of(pair);
    }
    return Optional.empty();
  }

  public static boolean isPresent(final String fullclassName) {
    return predefinedParameters.containsKey(fullclassName);
  }

  public static boolean generateImplicitParams(final String fullClassName) {
    return UriInfo.class.getName().equals(fullClassName);
  }

}
