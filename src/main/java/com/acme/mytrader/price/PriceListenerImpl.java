package com.acme.mytrader.price;

import com.acme.mytrader.execution.ExecutionService;

public class PriceListenerImpl implements PriceListener {
    private final String security;
    private final Double levelPrice;
    private final Integer noOfQuantities;
    private final ExecutionService executionService;
    private boolean isServiceExecuted;

    public PriceListenerImpl(String security, Double levelPrice, Integer noOfQuantities, ExecutionService executionService, boolean isServiceExecuted) {
        this.security = security;
        this.levelPrice = levelPrice;
        this.noOfQuantities = noOfQuantities;
        this.executionService = executionService;
        this.isServiceExecuted = isServiceExecuted;
    }

    @Override
    public void priceUpdate(String security, double price) {
        //check is if service not executed and for same security and price less than the current level
        if (!isServiceExecuted && this.security.equals(security) && price < this.levelPrice) {
            executionService.buy(security, price, noOfQuantities);
            isServiceExecuted = true;
        }
    }

    public String getSecurity() {
        return security;
    }

    public Double getLevelPrice() {
        return levelPrice;
    }

    public Integer getNoOfQuantities() {
        return noOfQuantities;
    }

    public ExecutionService getExecutionService() {
        return executionService;
    }

    public boolean isServiceExecuted() {
        return isServiceExecuted;
    }
}
