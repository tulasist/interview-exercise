package com.acme.mytrader.price;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PriceSourceImpl implements PriceSource {
    private List<PriceListener> priceListenerList = new ArrayList<>();
    private static final List<String> SECURITIES = Arrays
            .asList("IBM", "Google");

    private static final double START_RANGE = 1.00;
    private static final double END_RANGE = 100.00;

    public PriceSourceImpl() {
    }

    @Override
    public void addPriceListener(PriceListener listener) {
        this.priceListenerList.add(listener);
    }

    @Override
    public void removePriceListener(PriceListener listener) {
        this.priceListenerList.remove(listener);
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String security = SECURITIES.get(random.nextInt(SECURITIES.size()));
            double price = START_RANGE + (END_RANGE - START_RANGE) * random.nextDouble();
            priceListenerList.forEach(
                    priceListener -> priceListener.priceUpdate(security, price));
        }
    }

    public List<PriceListener> getPriceListenerList() {
        return priceListenerList;
    }

    public static List<String> getSECURITIES() {
        return SECURITIES;
    }

    public static double getStartRange() {
        return START_RANGE;
    }

    public static double getEndRange() {
        return END_RANGE;
    }
}
