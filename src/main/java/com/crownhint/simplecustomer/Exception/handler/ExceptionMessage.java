package com.crownhint.simplecustomer.Exception.handler;

import com.crownhint.simplecustomer.Exception.exceptions.SimpleCustomerException;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
@Slf4j
public class ExceptionMessage {

    public static String makeMessage(SimpleCustomerException ex) {
        String stackTrace = null;
        Package[] packages = Thread.currentThread().getContextClassLoader().getDefinedPackages();
        log.info("Current thread packages list: -> {}", Arrays.toString(packages) );
        for (StackTraceElement stack : Thread.currentThread().getStackTrace()) {
            stackTrace = stack.toString() + ".";
        }
        String  errorLog = LocalDateTime.now() + " ERROR " + Thread.currentThread().getId() + " --- [   "
                + stackTrace + " " + ex.getMessage();
        log.info("Error log: -> {} ", errorLog);
        return errorLog;
    }
}
