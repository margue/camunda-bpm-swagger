package org.camunda.bpm.engine.rest.dto.swagger;

import io.swagger.annotations.ApiModel;
import javax.annotation.Generated;
import org.camunda.bpm.engine.rest.dto.runtime.ProcessInstanceDto;
import org.camunda.bpm.engine.runtime.ProcessInstance;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@ApiModel
public class ProcessInstanceDtoSwagger
    extends ProcessInstanceDto
{

    public ProcessInstanceDtoSwagger() {
        super();
    }

    public ProcessInstanceDtoSwagger(ProcessInstance processInstance) {
        super(processInstance);
    }
}
