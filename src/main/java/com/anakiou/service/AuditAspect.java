package com.anakiou.service;

import com.anakiou.domain.EventLog;
import com.anakiou.domain.Output;
import com.anakiou.repository.EventLogRepository;
import com.anakiou.repository.OutputRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAspect {

    private final static String OUTPUT_CONTROL = "Output manipulated";

    private final EventLogRepository eventLogRepository;

    private final OutputRepository outputRepository;

    @Autowired
    public AuditAspect(EventLogRepository eventLogRepository, OutputRepository outputRepository) {
        this.eventLogRepository = eventLogRepository;
        this.outputRepository = outputRepository;
    }

    @Before("execution(* setOutput(..))")
    public void preProcessControl(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        if (args == null || args.length < 2) {
            return;
        }

        int no = (int) args[0];
        boolean value = (boolean) args[1];

        Output output = outputRepository.findOneByOutputNumber(no).orElse(new Output());

        EventLog e = new EventLog();
        e.setIoName(output.getName());
        e.setIoNumber(no);
        e.setIoValue(value ? 1 : 0);
        e.setMsg(OUTPUT_CONTROL);

        eventLogRepository.save(e);
    }
}
