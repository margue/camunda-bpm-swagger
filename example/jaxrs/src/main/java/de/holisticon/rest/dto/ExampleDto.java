package de.holisticon.rest.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Example DTO value", description = "This is a long description of Example DTO.")
public class ExampleDto {
  private String text;
  private String description;
  private Integer count;

  @ApiModelProperty(name="text", required=true, example = "Some text", dataType = "string")
  public String getText() {
    return text;
  }

  public void setText(final String text) {
    this.text = text;
  }

  @ApiModelProperty(name="description", required=false, example = "The description field.", dataType = "string")
  public String getDescription() {
    return description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  @ApiModelProperty(name="count", required=false, example = "12", dataType = "integer")
  public Integer getCount() {
    return count;
  }

  public void setCount(final Integer count) {
    this.count = count;
  }
}
