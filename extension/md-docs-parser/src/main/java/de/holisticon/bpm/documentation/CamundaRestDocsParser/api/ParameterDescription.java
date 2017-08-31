package de.holisticon.bpm.documentation.CamundaRestDocsParser.api;

import lombok.*;

@AllArgsConstructor
@Builder
@ToString
public class ParameterDescription {
    @Getter @Setter private String id;
    @Getter @Setter private String type;
    @Getter @Setter private String description;
}
