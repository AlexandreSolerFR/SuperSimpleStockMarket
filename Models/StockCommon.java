package Models;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class StockCommon extends Stock {

	public StockCommon(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
		super(symbol, lastDividend, parValue);
	}

	@Override
	public BigDecimal getDividendYield(BigDecimal price) {
		BigDecimal yield =  BigDecimal.ZERO;
		if (price.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("To calculate dividend yield Price must be above 0"); 
		if (lastDividend.compareTo(BigDecimal.ZERO) > 0) {
			yield = this.lastDividend.divide(price, 5, RoundingMode.HALF_EVEN).stripTrailingZeros();
		}
		return yield;
	}

}
