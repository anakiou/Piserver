package com.anakiou.service;

import com.anakiou.domain.EventLog;
import com.anakiou.repository.EventLogRepository;
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

    @Autowired
    public AuditAspect(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @Before("within(com.anakiou.service.*.*) && " + "execution(* setOutput(..))")
    public void preProcessControl(JoinPoint joinPoint){

        Object[] args = joinPoint.getArgs();

        if(args == null || args.length < 2){
            return;
        }

        int no = (int) args[0];
        boolean value = (boolean) args[1];

        EventLog e = new EventLog();
        e.setOutputValue(value);
        e.setPinNumber(no);
        e.setMsg(OUTPUT_CONTROL);

    }
}
