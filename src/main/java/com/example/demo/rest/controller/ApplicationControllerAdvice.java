package com.example.demo.rest.controller;

import com.example.demo.exception.PedidoNaoEncontradoExeption;
import com.example.demo.exception.RegraNegocioException;
import com.example.demo.rest.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException ex){
        String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(PedidoNaoEncontradoExeption.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors habdlePedidoNotFoundException(PedidoNaoEncontradoExeption ex){
        return new ApiErrors(ex.getMessage());
    }
}
