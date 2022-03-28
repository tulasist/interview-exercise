package com.acme.mytrader.execution;

public class ExecutionServiceImpl implements ExecutionService {
    private final Integer executionId;

    public ExecutionServiceImpl(Integer executionId) {
        this.executionId=executionId;
    }

    @Override
    public void buy(String security, double price, int volume) {
        System.out.println("Buying Price: " + price +" Volumes are : "+volume +" Securities are : "+security);
    }

    @Override
    public void sell(String security, double price, int volume) {

    }

    public Integer getExecutionId() {
        return executionId;
    }
}
