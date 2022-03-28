package com.acme.mytrader.strategy;

import com.acme.mytrader.execution.ExecutionService;
import com.acme.mytrader.price.PriceListener;
import com.acme.mytrader.price.PriceListenerImpl;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
public class PriceListenerTest {

    @Test
    public void testProperties() {
        ExecutionService executionService = Mockito.mock(ExecutionService.class);

        PriceListenerImpl priceListener = new PriceListenerImpl("IBM", 20.00, 10, executionService,
                false);

        assertThat(priceListener.getSecurity()).isEqualTo("IBM");
        assertThat(priceListener.getLevelPrice()).isEqualTo(20.00);
        assertThat(priceListener.getNoOfQuantities()).isEqualTo(10);
        assertThat(priceListener.isServiceExecuted()).isFalse();
    }

    @Test
    public void testBuyWithThreshold() {
        ExecutionService executionService = Mockito.mock(ExecutionService.class);
        ArgumentCaptor<String> acString = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Double> acDouble = ArgumentCaptor.forClass(Double.class);
        ArgumentCaptor<Integer> acInteger = ArgumentCaptor.forClass(Integer.class);

        PriceListenerImpl priceListener = new PriceListenerImpl("IBM", 50.00, 100, executionService,
                false);

        priceListener.priceUpdate("IBM", 25.00);

        verify(executionService, times(1))
                .buy(acString.capture(), acDouble.capture(), acInteger.capture());
        assertThat(acString.getValue()).isEqualTo("IBM");
        assertThat(acDouble.getValue()).isEqualTo(25.00);
        assertThat(acInteger.getValue()).isEqualTo(100);
        assertThat(priceListener.isServiceExecuted()).isTrue();
    }
}
