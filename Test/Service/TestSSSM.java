package Test.Service;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

import org.junit.Test;

import Models.Indicator;
import Service.Market;
import Service.SuperSimpleStockMarket;


public class TestSSSM {
	
	@Test
	public void testInitialize() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		Market m = api.getMarket();
		assertTrue(m.getStocks().size() == 5);
	}
	
	@Test
	public void testDividendYield() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		assertEquals(api.getDividendYield("JOE", BigDecimal.valueOf(500)), BigDecimal.valueOf(0.026));
	}
	
	@Test
	public void testPERatio() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		assertEquals(api.getPERatio("JOE", BigDecimal.valueOf(500)), BigDecimal.valueOf(38.46154));
	}
	
	@Test
	public void testgetStockByCode() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		assertEquals(api.getStockByCode("JOE").getSymbol(), "JOE");
	}
	
	@Test
	public void testRecordTrade() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		api.recordTrade(api.getStockByCode("TEA"), Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now().minusSeconds(30*60));
		assertTrue(api.getMarket().getTradesByStockCode("TEA").size() == 1);
	}
	
	@Test
	public void testCalculateVolumeWeightedStockPrice() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		api.recordTrade(api.getStockByCode("JOE"), Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now());
		api.recordTrade(api.getStockByCode("JOE"), Indicator.BUY, BigDecimal.valueOf(400), BigInteger.valueOf(200), Instant.now().minusSeconds(15));
		api.recordTrade(api.getStockByCode("JOE"), Indicator.BUY, BigDecimal.valueOf(450), BigInteger.valueOf(100), Instant.now().minusSeconds(30));
		api.recordTrade(api.getStockByCode("JOE"), Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now().minusSeconds(20 * 60));
		api.recordTrade(api.getStockByCode("JOE"), Indicator.BUY, BigDecimal.valueOf(500), BigInteger.valueOf(100), Instant.now().minusSeconds(25 * 60));
		assertEquals(api.CalculateVolumeWeightedStockPrice("JOE"), BigDecimal.valueOf(437.5));
	}
	
	@Test
	public void testgetAllShareIndex() {
		SuperSimpleStockMarket api = new SuperSimpleStockMarket();
		api.recordTrade(api.getStockByCode("TEA"), Indicator.BUY, BigDecimal.valueOf(300), BigInteger.valueOf(50), Instant.now());
		api.recordTrade(api.getStockByCode("POP"), Indicator.BUY, BigDecimal.valueOf(350), BigInteger.valueOf(50), Instant.now());
		api.recordTrade(api.getStockByCode("ALE"), Indicator.BUY, BigDecimal.valueOf(180), BigInteger.valueOf(50), Instant.now());
		api.recordTrade(api.getStockByCode("GIN"), Indicator.BUY, BigDecimal.valueOf(1000), BigInteger.valueOf(50), Instant.now());
		assertEquals(api.getAllShareIndex(), BigDecimal.valueOf(393.62834));
	}
	
	
	

}
