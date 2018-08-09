package com.example.code.validator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.BDDMockito.given;
@RunWith(SpringJUnit4ClassRunner.class)
public class ValidatorTest {

    @MockBean
    Validator validator;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateAlphaNumneric() {
        given(validator.validateAlphaNumneric("12a3")).willReturn(true);
    }

    @Test
    public void validateInteger() {
        given(validator.validateInteger("123")).willReturn(true);
    }

    @Test
    public void validateEnum() {
        given(validator.validateEnum("B")).willReturn(true);
    }

    @Test
    public void isValidDateFormat() {
        given(validator.validateEnum("08/11/2018")).willReturn(true);
    }
}