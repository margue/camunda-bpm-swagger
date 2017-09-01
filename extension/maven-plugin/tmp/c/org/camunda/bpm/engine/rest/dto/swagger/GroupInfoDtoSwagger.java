package org.camunda.bpm.engine.rest.dto.swagger;

import java.util.List;
import java.util.Set;
import io.swagger.annotations.ApiModel;
import javax.annotation.Generated;
import org.camunda.bpm.engine.rest.dto.task.GroupInfoDto;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@ApiModel
public class GroupInfoDtoSwagger
    extends GroupInfoDto
{

    public GroupInfoDtoSwagger() {
        super();
    }

    public GroupInfoDtoSwagger(List list, Set set) {
        super(list, set);
    }
}
