package com.test.controller.handler;

import com.test.controller.ShopController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jose on 06/04/2017.
 */
@ControllerAdvice
public class ShopExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ShopController.ShopNotFoundException.class)
    public String handleShopNotFound() {
        return "ShopNotFoundException";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(ShopController.InvalidShopException.class)
    public String handleInvalidShopNameException() {
        // Nothing to do
        return "InvalidShopException";
    }

}
