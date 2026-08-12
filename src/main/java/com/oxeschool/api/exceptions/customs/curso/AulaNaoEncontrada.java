package com.oxeschool.api.exceptions.customs.curso;

public class AulaNaoEncontrada extends RuntimeException {
    public AulaNaoEncontrada() {
        super("Aula não encontrada");
    }
}
