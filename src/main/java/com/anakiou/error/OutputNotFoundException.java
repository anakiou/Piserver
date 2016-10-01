package com.anakiou.error;

public class OutputNotFoundException extends RuntimeException {

    public OutputNotFoundException(){
        super();
    }

    public OutputNotFoundException(int no){
        super(String.format("Output ( %d ) not found", no));
    }

    public OutputNotFoundException(String name){
        super(String.format("Output ( %s ) not found", name));
    }
}