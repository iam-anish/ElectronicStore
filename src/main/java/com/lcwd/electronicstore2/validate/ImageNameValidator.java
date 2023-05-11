package com.lcwd.electronicstore2.validate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<imageNameValidate,String> {

    private Logger  logger = LoggerFactory.getLogger(ImageNameValidator.class);

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        logger.info("Message from isValid : {}",s);

        //logic
        if(s.isBlank()){
            return false;
        }
        else {
            return true;
        }

    }
}
