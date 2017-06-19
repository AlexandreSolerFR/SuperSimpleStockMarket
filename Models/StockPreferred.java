package Models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public  class StockPreferred extends Stock {
	protected BigDecimal fixedDividend;
	/**
	 * @return the fixedDividend
	 */
	public BigDecimal getFixedDividend() {
		return fixedDividend;
	}

	/**
	 * @param fixedDividend the fixedDividend to set
	 */
	public void setFixedDividend(BigDecimal fixedDividend) {
		if (fixedDividend != null && fixedDividend.compareTo(BigDecimal.ZERO) >= 0)
			this.fixedDividend = fixedDividend;
	}

	

	public StockPreferred(String symbol, BigDecimal lastDividend, BigDecimal parValue, BigDecimal fixedDividend) {
		super(symbol, lastDividend, parValue);
		if (fixedDividend == null || fixedDividend.compareTo(BigDecimal.ZERO) < 0)
			throw new IllegalArgumentException("Fixed dividend can not be null or negative");
		this.fixedDividend = fixedDividend;
	}

	@Override
	public BigDecimal getDividendYield(BigDecimal price) {
		BigDecimal yield =  BigDecimal.ZERO;
		if (price.compareTo(BigDecimal.ZERO) < 0)
			throw new IllegalArgumentException("To calculate dividend yield Price must be above 0"); 
		if (fixedDividend.compareTo(BigDecimal.ZERO) > 0 && parValue.compareTo(BigDecimal.ZERO) > 0) {
			yield = this.fixedDividend.multiply(this.parValue);
			yield = yield.divide(price, 5, RoundingMode.HALF_EVEN).stripTrailingZeros();
		}
		return yield;
	}
	

}
