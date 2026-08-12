package com.oxeschool.api.exceptions.customs.curso;

public class ModuloJaExisteException extends RuntimeException{
    public  ModuloJaExisteException() {super("Já existe um modulo com esse nome nesse curso");}
}
