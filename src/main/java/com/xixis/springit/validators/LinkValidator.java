package com.xixis.springit.validators;

import org.springframework.validation.BindingResult;


public class LinkValidator {

  private BindingResult bindingResult;

  public LinkValidator(BindingResult bindingResult) {
    this.bindingResult = bindingResult;
  }

  public String validateCreateEditLink(){

    String error = "";
    if (this.bindingResult.hasErrors()) {

      if (bindingResult.getFieldError("title") != null) {
        error += (bindingResult.getFieldError("title").getDefaultMessage());
      }

      if (bindingResult.getFieldError("url") != null) {
        error += bindingResult.getFieldError("url").getDefaultMessage();
      }
    }

     return error;
  }

}
