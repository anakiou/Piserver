package com.anakiou.error;

public class PifaceNotReadyException extends RuntimeException {

    public PifaceNotReadyException(){
        super("The PIFACE board is not found");
    }
}
