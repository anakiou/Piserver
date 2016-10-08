package com.anakiou.service;

import com.anakiou.domain.Input;
import com.anakiou.domain.Output;

import java.util.List;

public interface PifaceService {

    int getInputStatus(int inputNo);

    int[] getInputStatus();

    int[] getInputsChangeCount();

    int getOutputStatus(int outputNo);

    int[] getOutputStatus();

    int[] getOutputsChangeCount();

    int setOutput(int no, boolean value);

    int[] setOutput(boolean[] values);

    String setInputName(int no, String name);

    String setOutputName(int no, String name);

    List<String> getInputNames();

    List<String> getOutputNames();

    List<Input> getInputs();

    List<Output> getOutputs();

    void updateIO();

    int getInputChangeCount(int i);

    int getOutputChangeCount(int i);
}
