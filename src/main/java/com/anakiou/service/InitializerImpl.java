package com.anakiou.service;

import com.anakiou.domain.Input;
import com.anakiou.domain.Output;
import com.anakiou.repository.InputRepository;
import com.anakiou.repository.OutputRepository;
import com.pi4j.io.gpio.PinState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitializerImpl implements Initializer {

    private final InputRepository inputRepository;

    private final OutputRepository outputRepository;

    @Autowired
    public InitializerImpl(InputRepository inputRepository, OutputRepository outputRepository) {
        this.inputRepository = inputRepository;
        this.outputRepository = outputRepository;
    }

    @Override
    public void initInputs(List<PinState> pinStateList) {

        for (int i = 0; i < 8; i++) {
            Input input = inputRepository.findOneByInputNumber(i).orElse(new Input());

            if (input.getId() == null) {
                input.setName("Input " + i);
                input.setInputNumber(i);
                input.setInputStatus(-1);
                inputRepository.save(input);
            }
        }
    }

    @Override
    public void initOutputs(List<PinState> pinStateList) {

        for (int i = 0; i < 8; i++) {
            Output output = outputRepository.findOneByOutputNumber(i).orElse(new Output());

            if (output.getId() == null) {
                output.setName("Output " + i);
                output.setOutputNumber(i);
                output.setOutputStatus(-1);
                outputRepository.save(output);
            }
        }
    }
}
