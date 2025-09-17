package com.copanote.SpringTest.validation;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Set;

@Slf4j
public class BeanValidationTest {

    @Test
    void beanValidtaion() {
        //검증기 생성
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Item item = new Item();
        item.setItemName("    ");
        item.setPrice(0);
        item.setQuantity(10000);

        //검증 실행 . 결과가 비어있으면 검증 오류가 없는 것이다.
        Set<ConstraintViolation<Item>> validate = validator.validate(item);
        for (ConstraintViolation<Item> violation : validate) {
            log.info("violation={}", violation);
            log.info("violation.message={}", violation.getMessage());

        }


    }


}
