package Models;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;

public class Trade {
	
	protected Stock stock;
	protected Indicator indicator;
	protected BigDecimal price;
	protected BigInteger quantity;
	protected Instant timeStamp;
	
	private Trade(Stock stock, Indicator indicator, BigDecimal price, BigInteger quantity, Instant timeStamp) {
		if (stock == null)
			throw new IllegalArgumentException("stock can not be null");
		if (indicator == null)
			throw new IllegalArgumentException("indicator can not be null");
		if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
			throw new IllegalArgumentException("price can not be null or inferior or equal to 0");
		if (quantity == null || quantity.compareTo(BigInteger.ZERO) <= 0)
			throw new IllegalArgumentException("quantity can not be null or inferior or equal to 0");
		if (timeStamp == null)
			throw new IllegalArgumentException("timeStamp can not be null");
		
		this.stock = stock;
		this.indicator = indicator;
		this.price = price;
		this.quantity = quantity;
		this.timeStamp = timeStamp;
	}
	
	public static Trade registerTrade(Stock stock, Indicator indicator, BigDecimal price, BigInteger quantity, Instant timeStamp) {
		return new Trade(stock, indicator, price, quantity, timeStamp);
	}

	/**
	 * @return the stock
	 */
	public Stock getStock() {
		return stock;
	}

	/**
	 * @return the indicator
	 */
	public Indicator getIndicator() {
		return indicator;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @return the quantity
	 */
	public BigInteger getQuantity() {
		return quantity;
	}

	/**
	 * @return the timeStamp
	 */
	public Instant getTimeStamp() {
		return timeStamp;
	}
	
	

}
