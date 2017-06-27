package com.springframework.security.constraint;

import static java.lang.annotation.ElementType.FIELD;  
import static java.lang.annotation.ElementType.METHOD;  
import static java.lang.annotation.RetentionPolicy.RUNTIME;  
  
import java.lang.annotation.Retention;  
import java.lang.annotation.Target;  
  
import javax.validation.Constraint;  
import javax.validation.Payload;  

import com.springframework.security.constraint.impl.MemoValidator;
  
  
@Retention(RUNTIME)  
@Target({ FIELD, METHOD })  
@Constraint(validatedBy=MemoValidator.class)  
public @interface Memo {  
      
    String message() default "请输入正确的备注";  
      
    Class<?>[] groups() default {};  
      
    Class<? extends Payload>[] payload() default {};  
}  
