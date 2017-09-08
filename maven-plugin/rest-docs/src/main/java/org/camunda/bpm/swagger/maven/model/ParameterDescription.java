package org.camunda.bpm.swagger.maven.model;

import lombok.*;

@AllArgsConstructor
@Builder
@ToString
public class ParameterDescription {
    @Getter @Setter @NonNull private String id;
    @Getter @Setter private String type;
    @Getter @Setter @NonNull private String description;
    @Getter @Setter private Boolean required;
}
