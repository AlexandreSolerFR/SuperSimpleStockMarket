package Models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Stock {

	protected String symbol;
	protected BigDecimal lastDividend;
	protected BigDecimal parValue;
	
	protected  Stock(String symbol2, BigDecimal lastDividend2, BigDecimal parValue2) {
		if (symbol2 == null || symbol2.isEmpty())
			throw new IllegalArgumentException("Symbol can not be null or empty");
		if (lastDividend2 == null || lastDividend2.compareTo(BigDecimal.ZERO) < 0)
			throw new IllegalArgumentException("LastDividend can not be null or negative");
		if (parValue2 == null || parValue2.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("Par value can not be null or inferior or equal to 0");
		symbol = symbol2;
		lastDividend = lastDividend2;
		parValue = parValue2;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		if (symbol != null && !symbol.isEmpty())
			this.symbol = symbol;
	}

	/**
	 * @return the lastDividend
	 */
	public BigDecimal getLastDividend() {
		return lastDividend;
	}

	/**
	 * @param lastDividend the lastDividend to set
	 */
	public void setLastDividend(BigDecimal lastDividend) {
		if (lastDividend != null && lastDividend.compareTo(BigDecimal.ZERO) >= 0)
			this.lastDividend = lastDividend;
	}

	/**
	 * @return the parValue
	 */
	public BigDecimal getParValue() {
		return parValue;
	}
	
	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(BigDecimal parValue) {
		if (parValue != null && parValue.compareTo(BigDecimal.ZERO) > 0)
			this.parValue = parValue;
	}
	
	public abstract BigDecimal getDividendYield(BigDecimal price);

	public static Stock createCommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
		return new StockCommon(symbol, lastDividend, parValue);
	}
	
	public static Stock createPreferredStock(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal fixedDividend) {
		return new StockPreferred(symbol, lastDividend, parValue, fixedDividend);
	}
	
	public BigDecimal getPERatio(BigDecimal price) {
		BigDecimal PERatio =  BigDecimal.ZERO;
		if (this.lastDividend.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("To calculate P/E Ration Last Dividend must be above 0"); 
		if (price.compareTo(BigDecimal.ZERO) > 0) {
			PERatio = price.divide(this.getLastDividend(), 5, RoundingMode.HALF_EVEN).stripTrailingZeros();
		}
		return PERatio;
	}
	
}
