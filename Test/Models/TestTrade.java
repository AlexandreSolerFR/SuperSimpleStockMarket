package Test.Models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import org.junit.Test;

import Models.Indicator;
import Models.Stock;
import Models.Trade;

public class TestTrade {

	@Test
	public void testRegisterTrade() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now().minusSeconds(1));
		assertEquals(t.getStock(), s);
		assertEquals(t.getIndicator(), Indicator.BUY);
		assertEquals(t.getPrice(), BigDecimal.valueOf(500));
		assertEquals(t.getQuantity(), BigInteger.valueOf(100));
		assertTrue(t.getTimeStamp().isBefore(Instant.now()));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorIndicatorNull() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, null, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorPriceNull() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.SELL, null, BigInteger.valueOf(100), Instant.now());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorPriceNegative() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.SELL, BigDecimal.valueOf(-2), BigInteger.valueOf(100), Instant.now());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorQtyNull() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.SELL, BigDecimal.valueOf(500), null, Instant.now());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorQtyZero() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.SELL, BigDecimal.valueOf(500), BigInteger.valueOf(0), Instant.now());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorInstantNull() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.SELL, BigDecimal.valueOf(500), BigInteger.valueOf(100), null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorStockNull() {
		Trade t = Trade.registerTrade(null, Indicator.SELL, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now());
	}
	
}
