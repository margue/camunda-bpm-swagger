package org.camunda.bpm.swagger.maven.generator.step;

import lombok.Getter;
import org.camunda.bpm.swagger.maven.generator.TypeHelper;
import org.camunda.bpm.swagger.maven.model.CamundaDto;
import org.camunda.bpm.swagger.maven.model.ModelRepository;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.AbstractJType;
import com.helger.jcodemodel.JCodeModel;

import java.util.Optional;

public class DtoStep {

  @Getter
  private Optional<CamundaDto> camundaDto = Optional.empty();

  private final Class<?> baseClazz;
  private final ModelRepository modelRepository;

  public DtoStep(final ModelRepository modelRepository, final Class<?> clazz) {
    this.modelRepository = modelRepository;
    this.baseClazz = clazz;
  }



  public boolean isDto() {
    return TypeHelper.isDto(baseClazz);
  }

  public AbstractJType getType(final JCodeModel jCodeModel) {
    if (isDto()) {
      return generateDto();
    } else {
      return jCodeModel._ref(baseClazz);
    }
  }

  private AbstractJClass generateDto() {
    final CamundaDto dto = new CamundaDto(modelRepository, baseClazz);
    this.camundaDto = Optional.of(dto);
    dto.generate();
    return dto.getDefinedClass();
  }
}
