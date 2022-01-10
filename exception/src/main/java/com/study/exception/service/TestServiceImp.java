package com.study.exception.service;

import com.study.exception.exception.TestException;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImp implements TestService{
    @Override
    public Object test() {
        throw new TestException("test");
    }
}
