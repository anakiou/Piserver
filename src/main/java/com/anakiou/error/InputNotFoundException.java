package com.anakiou.error;

public class InputNotFoundException extends RuntimeException {

    public InputNotFoundException(){
        super();
    }

    public InputNotFoundException(int no){
        super(String.format("Input ( %d ) not found", no));
    }

    public InputNotFoundException(String name){
        super(String.format("Input ( %s ) not found", name));
    }
}
