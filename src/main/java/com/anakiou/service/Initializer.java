package com.anakiou.service;


import com.pi4j.io.gpio.PinState;

import java.util.List;

public interface Initializer {

    void initInputs(List<PinState> pinStateList);

    void initOutputs(List<PinState> pinStateList);

}
