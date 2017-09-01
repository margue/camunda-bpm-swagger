package org.camunda.bpm.engine.rest.dto.swagger;

import io.swagger.annotations.ApiModel;
import javax.annotation.Generated;
import org.camunda.bpm.engine.rest.dto.authorization.AuthorizationCheckResultDto;
import org.camunda.bpm.engine.rest.util.AuthorizationUtil;

@Generated("org.camunda.bpm.swagger.maven.GenerateSwaggerServicesMojo")
@ApiModel
public class AuthorizationCheckResultDtoSwagger
    extends AuthorizationCheckResultDto
{

    public AuthorizationCheckResultDtoSwagger() {
        super();
    }

    public AuthorizationCheckResultDtoSwagger(boolean booleanArg, AuthorizationUtil authorizationUtil, String string) {
        super(booleanArg, authorizationUtil, string);
    }
}
