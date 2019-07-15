package com.xixis.springit.validators;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;


public class UserValidator {

  private BindingResult bindingResult;

  public UserValidator(BindingResult bindingResult) {
    this.bindingResult = bindingResult;
  }

  public String validateRegisterUser(){

    StringBuilder error = new StringBuilder();
    if (this.bindingResult.hasErrors()) {
      List<ObjectError> errors = bindingResult.getAllErrors();
      for (ObjectError err : errors) {
        if(err == errors.get(errors.size()-1)){
          error.append(err.getDefaultMessage());
          break;
        }
        error.append(err.getDefaultMessage());
        error.append(" ,");
      }

    }

     return error.toString();
  }

}
