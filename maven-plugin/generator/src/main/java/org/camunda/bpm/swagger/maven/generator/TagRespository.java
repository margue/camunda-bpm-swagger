package org.camunda.bpm.swagger.maven.generator;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.rest.CaseDefinitionRestService;
import org.camunda.bpm.engine.rest.CaseExecutionRestService;
import org.camunda.bpm.engine.rest.CaseInstanceRestService;
import org.camunda.bpm.engine.rest.DecisionDefinitionRestService;
import org.camunda.bpm.engine.rest.DecisionRequirementsDefinitionRestService;
import org.camunda.bpm.engine.rest.JobDefinitionRestService;
import org.camunda.bpm.engine.rest.JobRestService;
import org.camunda.bpm.engine.rest.ProcessDefinitionRestService;
import org.camunda.bpm.engine.rest.ProcessInstanceRestService;
import org.camunda.bpm.swagger.maven.model.CamundaRestService;

/**
 * Repository for tags to classify the methods based on class services.
 * 
 * @author Simon Zambrovski
 *
 */
public class TagRespository {

  private static Map<Class<?>, String> tags = new HashMap<>();
  static {
    tags.put(CaseDefinitionRestService.class, "Case Definition");
    tags.put(CaseExecutionRestService.class, "Case Execution");
    tags.put(CaseInstanceRestService.class, "Case Instance");

    tags.put(DecisionDefinitionRestService.class, "Decision Definition");
    tags.put(DecisionRequirementsDefinitionRestService.class, "Decision Requirement Definition Execution");

    tags.put(JobDefinitionRestService.class, "Job Definition");
    tags.put(JobRestService.class, "Job Execution");

    tags.put(ProcessDefinitionRestService.class, "Process Definition");
    tags.put(ProcessInstanceRestService.class, "Process Instance");
  }

  public static String lookup(final CamundaRestService camundaRestService) {
    return tags.getOrDefault(camundaRestService.getServiceInterfaceClass(), getTag(camundaRestService));
  }

  public static String getTag(final CamundaRestService camundaRestService) {
    return StringHelper.splitCamelCase(camundaRestService.getSimpleName()).split(" ")[0];
  }

}
