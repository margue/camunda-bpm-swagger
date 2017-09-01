package org.camunda.bpm.swagger.maven.model;

import lombok.*;

@AllArgsConstructor
@Builder
@ToString
public class ParameterDescription {
    @Getter @Setter private String id;
    @Getter @Setter private String type;
    @Getter @Setter private String description;
}
