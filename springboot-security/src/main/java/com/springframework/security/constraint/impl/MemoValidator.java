package com.springframework.security.constraint.impl;

import java.util.HashSet;  

import javax.validation.ConstraintValidator;  
import javax.validation.ConstraintValidatorContext;  

import com.springframework.security.constraint.Memo;
  
  
public class MemoValidator implements ConstraintValidator<Memo, String> {

	public void initialize(Memo arg0) {
	}

	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		 HashSet<String> memoSet = new HashSet<String>();  
	        memoSet.add("È¦Ñø");  
	        memoSet.add("É¢Ñø");  
	        return memoSet.contains(arg0);  
	}  
  
}  