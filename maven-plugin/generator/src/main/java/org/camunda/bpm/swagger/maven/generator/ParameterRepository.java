package org.camunda.bpm.swagger.maven.generator;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.tuple.Pair;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ParameterRepository {

  private static Map<Class<?>, Pair<Class<?>, String>> predefinedParameters = new HashMap<>();

  static {
    predefinedParameters.put(UriInfo.class, Pair.of(UriInfo.class, "uriInfo"));
    predefinedParameters.put(ObjectMapper.class, Pair.of(ObjectMapper.class, "objectMapper"));
  }

  public static Optional<Pair<Class<?>, String>> lookup(final Parameter param) {
    final Pair<Class<?>, String> pair = predefinedParameters.get(param.getType());
    if (pair != null) {
      return Optional.of(pair);
    }
    return Optional.empty();
  }

  public static boolean isPresent(final Class<?> predefinedParameter) {
    return predefinedParameters.containsKey(predefinedParameter);
  }
}
