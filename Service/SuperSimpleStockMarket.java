package Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Models.Indicator;
import Models.Stock;
import Models.Trade;

public class SuperSimpleStockMarket {
	
	protected Market market;
	
	/**
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}

	protected void initializeMarket() {
		Stock tea = Stock.createCommonStock("TEA", BigDecimal.ZERO, BigDecimal.valueOf(100));
		Stock pop = Stock.createCommonStock("POP", BigDecimal.valueOf(8), BigDecimal.valueOf(100));
		Stock ale = Stock.createCommonStock("ALE", BigDecimal.valueOf(23), BigDecimal.valueOf(60));
		Stock joe = Stock.createCommonStock("JOE", BigDecimal.valueOf(13), BigDecimal.valueOf(250));
		Stock gin = Stock.createPreferredStock("GIN", BigDecimal.valueOf(8), BigDecimal.valueOf(100), BigDecimal.valueOf(0.02));
		market.registerStock(tea);
		market.registerStock(pop);
		market.registerStock(ale);
		market.registerStock(joe);
		market.registerStock(gin);
	}
	
	public SuperSimpleStockMarket() {
		this.market = Market.getInstance();
		this.initializeMarket();
	}
	
	public SuperSimpleStockMarket(Market m) {
		this.market = m;
	}
	
	public BigDecimal getDividendYield(String code, BigDecimal price) {
		Stock s = market.getStocks().get(code);
		if (s != null) {
			return s.getDividendYield(price);
		}
		return BigDecimal.ZERO;
	}
	
	public BigDecimal getPERatio(String code, BigDecimal price) {
		Stock s = market.getStocks().get(code);
		if (s != null) {
			return s.getPERatio(price);
		}
		return BigDecimal.ZERO;
	}
	
	public Stock getStockByCode(String code) {
		if (code != null && !code.isEmpty())
			return market.getStocks().get(code);
		return null;
	}
	
	public boolean recordTrade(Stock stock, Indicator indicator, BigDecimal price, BigInteger quantity, Instant timeStamp) {
		Trade t = Trade.registerTrade(stock, indicator, price, quantity, timeStamp);
		return market.registerTrade(t);
	}

	public BigDecimal CalculateVolumeWeightedStockPrice(String symbol) {
		BigDecimal VWSP = BigDecimal.ZERO;
		List<Trade> trades = this.market.getTradesByStockCode(symbol);
		trades = this.getTradesFromDuration(trades, 15);
		BigDecimal totalUp = BigDecimal.ZERO;
		BigDecimal totalQty = BigDecimal.ZERO;
		for (Trade trade : trades) {
			BigDecimal qty = new BigDecimal(trade.getQuantity());
			totalQty = totalQty.add(qty);
			qty = trade.getPrice().multiply(qty);
			totalUp = totalUp.add(qty);
		}
		if (totalQty.compareTo(BigDecimal.ZERO) > 0) {
			VWSP = totalUp.divide(totalQty, 5, RoundingMode.HALF_EVEN).stripTrailingZeros();
		}
		return VWSP;
	}
	
	public BigDecimal getAllShareIndex() {
		Map<String, Stock> stocks = market.getStocks();
		List<BigDecimal> lastTradePrices = new ArrayList<>();
		for (Stock stock : stocks.values()) {
			if (stock != null) {
				List<Trade> trades = market.getTradesByStockCode(stock.getSymbol());
				Trade lastTrade = this.getLastTrade(trades);
				if (lastTrade != null)
					lastTradePrices.add(lastTrade.getPrice());
			}
		}
		return this.getGeometricMean(lastTradePrices);
	}
	
	protected BigDecimal getGeometricMean(List<BigDecimal> lastTradePrices) {
		int nbStockPrices = 0;
		BigDecimal subTotal = BigDecimal.ONE;
		for (BigDecimal price : lastTradePrices) {
			nbStockPrices++;
			subTotal = subTotal.multiply(price);
		}
		return BigDecimal.valueOf(Math.pow(subTotal.doubleValue(), 1.0 / nbStockPrices)).setScale(5, RoundingMode.HALF_EVEN);
	}

	protected Trade getLastTrade(List<Trade> trades) {
		if (trades != null && trades.size() > 0) {
			Collections.sort(trades, (a, b) -> b.getTimeStamp().compareTo(a.getTimeStamp()));
			return trades.get(0);
		}
		return null;
	}

	/** 
	 * 
	 * @param trades List of trades must not be NULL
	 * @param minutes number of minutes > 0
	 * @return List of trades within the 
	 */
	protected List<Trade> getTradesFromDuration(List<Trade> trades, int minutes) {
		if (minutes > 0) { 
			List<Trade> tradesToReturn = trades.stream()
												.filter(t -> t.getTimeStamp().isAfter(Instant.now().minusSeconds(minutes * 60)))
												.collect(Collectors.toList());
			return tradesToReturn;
		}
		return Collections.emptyList();
	}

}
