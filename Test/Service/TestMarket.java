package Test.Service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.List;

import org.junit.Test;

import Models.Indicator;
import Models.Stock;
import Models.Trade;
import Service.Market;

public class TestMarket {
	
	@Test
	public void testRegisterStock() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Market m = Market.getInstance();
		boolean result = m.registerStock(s);
		assertTrue(result);
		assertTrue(m.getStocks().size() == 1);
	}
	
	@Test
	public void testDeleteStock() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Market m = Market.getInstance();
		boolean result = m.registerStock(s);
		assertTrue(result);
		result = m.deleteStock(s);
		assertTrue(result);
		assertTrue(m.getStocks().size() == 0);
		
	}
	
	@Test
	public void testRegisterTrade() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now());
		Market m = Market.getInstance();
		boolean result = m.registerTrade(t);
		assertTrue(result);
		
	}
	
	@Test
	public void testGetTradeByStockCode() {
		Stock s = Stock.createCommonStock("TEST1", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now());
		Market m = Market.getInstance();
		boolean result = m.registerTrade(t);
		assertTrue(result);
		List<Trade> trades = m.getTradesByStockCode(s.getSymbol());
		assertTrue(trades.size() == 1);
		assertEquals(trades.get(0), t);
	}
	
	@Test
	public void testGetTradeByStockCodeFalse() {
		Stock s = Stock.createCommonStock("TEST2", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		Trade t = Trade.registerTrade(s, Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now());
		Market m = Market.getInstance();
		boolean result = m.registerTrade(t);
		assertTrue(result);
		List<Trade> trades = m.getTradesByStockCode("FALSE");
		assertTrue(trades.size() == 0);
	}


}
