package com.anakiou.service;


import com.anakiou.domain.Input;
import com.anakiou.domain.Output;
import com.anakiou.error.InputNotFoundException;
import com.anakiou.error.OutputNotFoundException;
import com.anakiou.error.PifaceNotReadyException;
import com.anakiou.repository.InputRepository;
import com.anakiou.repository.OutputRepository;
import com.pi4j.component.switches.SwitchStateChangeEvent;
import com.pi4j.device.piface.PiFace;
import com.pi4j.device.piface.PiFaceSwitch;
import com.pi4j.device.piface.impl.PiFaceDevice;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.spi.SpiChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

@Service
public class PifaceServiceImpl implements PifaceService {

    private final static Logger LOG = LoggerFactory.getLogger(PifaceServiceImpl.class);

    private final PiFace piface;

    private final InputRepository inputRepository;

    private final OutputRepository outputRepository;

    private final Initializer initializer;

    private final List<LongAdder> inputCounters;

    private final List<LongAdder> outputCounters;

    @Autowired
    public PifaceServiceImpl(InputRepository inputRepository, OutputRepository outputRepository, Initializer initializer) {
        this.inputRepository = inputRepository;
        this.outputRepository = outputRepository;
        this.initializer = initializer;
        this.piface = createPiface();
        setupEvents();
        inputCounters = new ArrayList<>(8);
        outputCounters = new ArrayList<>(8);
    }

    @PostConstruct
    public void init() {

        List<PinState> inputStates = new ArrayList<>(8);
        List<PinState> outputStates = new ArrayList<>(8);

        for (int i = 0; i < 8; i++) {
            inputStates.add(piface.getInputPin(i).getState());
            outputStates.add(piface.getOutputPin(i).getState());
            inputCounters.add(new LongAdder());
            outputCounters.add(new LongAdder());
        }

        LOG.info("Initializing INPUTS");

        initializer.initInputs(inputStates);

        LOG.info("Initializing OUTPUTS");

        initializer.initOutputs(outputStates);
    }

    @PreDestroy
    public void teardown() {
    }

    @Override
    public int getInputStatus(int inputNo) {

        checkInputNumber(inputNo);

        return piface.getInputPin(inputNo).getState().getValue();
    }

    @Override
    public int[] getInputStatus() {

        int[] status = new int[8];

        for (int i = 0; i < 8; i++) {
            status[i] = getInputStatus(i);
        }

        return status;
    }

    @Override
    public int[] getInputsChangeCount() {
        int[] sts = new int[8];

        for (int i = 0; i < 8; i++) {
            sts[i] = inputCounters.get(i).intValue();
        }
        return sts;
    }

    @Override
    public int getOutputStatus(int outputNo) {

        checkOutputNumber(outputNo);

        return piface.getOutputPin(outputNo).getState().getValue();
    }

    @Override
    public int[] getOutputStatus() {

        int[] status = new int[8];

        for (int i = 0; i < 8; i++) {
            status[i] = getOutputStatus(i);
        }

        return status;
    }

    @Override
    public int[] getOutputsChangeCount() {
        int[] sts = new int[8];

        for (int i = 0; i < 8; i++) {
            sts[i] = outputCounters.get(i).intValue();
        }
        return sts;
    }

    @Override
    public int setOutput(int no, boolean value) {

        int before = piface.getOutputPin(no).getState().getValue();

        synchronized (this) {
            piface.getOutputPin(no).setState(value);
        }

        int after = piface.getOutputPin(no).getState().getValue();

        if (before != after) {
            outputCounters.get(no).increment();
        }

        return after;
    }

    @Override
    public int[] setOutput(boolean[] values) {

        int[] results = new int[8];

        for (int i = 0; i < 8; i++) {
            results[i] = setOutput(i, values[i]);
        }

        return results;
    }

    @Override
    public String setInputName(int no, String name) {
        checkInputNumber(no);

        inputRepository.findOneByInputNumber(no).ifPresent(i -> {
            i.setName(name);
            i.setInputStatus(getInputStatus(no));
            inputRepository.save(i);
        });

        return name;
    }

    @Override
    public String setOutputName(int no, String name) {
        checkOutputNumber(no);

        outputRepository.findOneByOutputNumber(no).ifPresent(o -> {
            o.setName(name);
            o.setOutputStatus(getOutputStatus(no));
            outputRepository.save(o);
        });

        return name;
    }

    @Override
    public List<String> getInputNames() {
        return inputRepository.findNames();
    }

    @Override
    public List<String> getOutputNames() {
        return outputRepository.findNames();
    }

    @Override
    public List<Input> getInputs() {
        return inputRepository.findAll();
    }

    @Override
    public List<Output> getOutputs() {
        return outputRepository.findAll();
    }

    @Override
    public void updateIO() {
        outputRepository.findAll().forEach(o -> {
            o.setOutputStatus(getOutputStatus(o.getOutputNumber()));
            outputRepository.save(o);
        });

        inputRepository.findAll().forEach(i -> {
            i.setInputStatus(getInputStatus(i.getInputNumber()));
            inputRepository.save(i);
        });
    }

    @Override
    public int getInputChangeCount(int i) {
        return inputCounters.get(i).intValue();
    }

    @Override
    public int getOutputChangeCount(int i) {
        return outputCounters.get(i).intValue();
    }

    private void checkInputNumber(int no) {
        if (no < 0 || no > 7) {
            throw new InputNotFoundException(no);
        }
    }

    private void checkOutputNumber(int no) {
        if (no < 0 || no > 7) {
            throw new OutputNotFoundException(no);
        }
    }

    private PiFace createPiface() {
        if (piface != null) {

            LOG.warn("PIFACE is already initialized");

            return piface;
        }

        try {
            LOG.info("Initializing PIFACE");
            return new PiFaceDevice(PiFace.DEFAULT_ADDRESS, SpiChannel.CS0);
        } catch (IOException ex) {
            LOG.error("Could not start PIFACE device", ex);
        }

        throw new PifaceNotReadyException();
    }

    private void setupEvents() {
        piface.getSwitch(PiFaceSwitch.S1).addListener((SwitchStateChangeEvent e) -> {
            inputCounters.get(0).increment();
        });

        piface.getSwitch(PiFaceSwitch.S2).addListener((SwitchStateChangeEvent e) -> {
            inputCounters.get(1).increment();
        });

        piface.getSwitch(PiFaceSwitch.S3).addListener((SwitchStateChangeEvent e) -> {
            inputCounters.get(2).increment();
        });

        piface.getSwitch(PiFaceSwitch.S4).addListener((SwitchStateChangeEvent e) -> {
            inputCounters.get(3).increment();
        });

    }
}
