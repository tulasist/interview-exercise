package com.acme.mytrader.strategy;

import com.acme.mytrader.dto.StockDTO;
import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.execution.ExecutionServiceImpl;
import com.acme.mytrader.price.PriceListenerImpl;
import com.acme.mytrader.price.PriceSource;
import com.acme.mytrader.price.PriceSourceImpl;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * <pre>
 * User Story: As a trader I want to be able to monitor stock prices such
 * that when they breach a trigger level orders can be executed automatically
 * </pre>
 */
public class TradingStrategy {
    private final ExecutionService executionService;
    private final PriceSource priceSource;

    public static void main(String[] args) throws InterruptedException {
        StockDTO ibm = new StockDTO("IBM", 90.00, 10);
        StockDTO google = new StockDTO("microsoft", 80.00, 20);

        new TradingStrategy(new ExecutionServiceImpl(1), new PriceSourceImpl())
                           .stockBuy(asList(ibm, google));
    }

    public TradingStrategy(ExecutionService executionService, PriceSource priceSource) {
        this.executionService = executionService;
        this.priceSource = priceSource;
    }

    public void stockBuy(List<StockDTO> request) throws InterruptedException {

        request.stream().map(
                p -> new PriceListenerImpl(p.getSecurity(), p.getPriceThreshold(), p.getQuantity(),
                        executionService, false)).forEach(priceSource::addPriceListener);
        Thread thread = new Thread(priceSource);
        thread.start();
        thread.join();
        request.stream().map(
                p -> new PriceListenerImpl(p.getSecurity(), p.getPriceThreshold(), p.getQuantity(),
                        executionService, false)).forEach(priceSource::removePriceListener);
    }
}
