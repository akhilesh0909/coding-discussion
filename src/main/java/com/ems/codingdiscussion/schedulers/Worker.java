package com.ems.codingdiscussion.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Scope("prototype")
public class Worker implements Runnable{

    @Autowired
    private AnswerApprovalTask answerApprovalTask;

    @Override
    public void run() {
        try {
            answerApprovalTask.markAnswerAsApproved();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
