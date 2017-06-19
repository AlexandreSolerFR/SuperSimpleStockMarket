package Test.Models;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import Models.Stock;
import Models.StockCommon;
import Models.StockPreferred;


public class TestStock {
	
	@Test
	public void testBasicCommonStock() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		assertTrue("The stock should be of class StockCommon", s instanceof StockCommon);
		assertEquals(s.getLastDividend(), BigDecimal.valueOf(10));
		assertEquals(s.getParValue(), BigDecimal.valueOf(100));
		assertEquals(s.getDividendYield(BigDecimal.valueOf(500)), BigDecimal.valueOf(0.02).stripTrailingZeros());
		assertEquals(s.getPERatio(BigDecimal.valueOf(500)), BigDecimal.valueOf(50).stripTrailingZeros());
	}
	
	@Test
	public void testBasicPrefferedStock() {
		StockPreferred s = (StockPreferred) Stock.createPreferredStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(0.05));
		assertTrue("The stock should be of class StockPreffered", s instanceof StockPreferred);
		assertEquals(s.getLastDividend(), BigDecimal.valueOf(10));
		assertEquals(s.getParValue(), BigDecimal.valueOf(100));
		assertEquals(s.getFixedDividend(), BigDecimal.valueOf(0.05));
		assertEquals(s.getDividendYield(BigDecimal.valueOf(500)), BigDecimal.valueOf(0.01).stripTrailingZeros());
		assertEquals(s.getPERatio(BigDecimal.valueOf(500)), BigDecimal.valueOf(50).stripTrailingZeros());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorPERatio() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(0), BigDecimal.valueOf(100));
		s.getPERatio(BigDecimal.valueOf(500));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorYield() {
		Stock s = Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
		s.getDividendYield(BigDecimal.valueOf(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorSymbolNull() {
		Stock.createCommonStock(null, BigDecimal.valueOf(10), BigDecimal.valueOf(100));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorSymbolEmpty() {
		Stock.createCommonStock("", BigDecimal.valueOf(10), BigDecimal.valueOf(100));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorDividendNull() {
		Stock.createCommonStock("TEST", null, BigDecimal.valueOf(100));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorDividendNegative() {
		Stock.createCommonStock("TEST", BigDecimal.valueOf(-2), BigDecimal.valueOf(100));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorParValueNull() {
		Stock.createCommonStock("TEST", BigDecimal.valueOf(10), null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorParValueZero() {
		Stock.createCommonStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorFixedNull() {
		Stock.createPreferredStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100), null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testErrorFixedNegative() {
		Stock.createPreferredStock("TEST", BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(-2));
	}
	
	
	
	
	
	
	

}
