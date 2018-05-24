package com.github.xionghuicoder.microservice.common.validator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author chengjinhui
 * @version 1.0.0
 * @date 2018/5/23 上午11:46
 * @description
 */
public class SimpleBean {

  @NotNull
  private int id;

  @NotBlank
  @Size(min = 2, max = 8,message = "be care the name length ，it should between 2 and 8 !")
  private String name;

  @NotNull(message = "score should be not null")
  @DoubleVal
  private Double score;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }
}
