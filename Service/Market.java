package Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Stock;
import Models.Trade;

public class Market {
	
	protected Map<String, List<Trade>> Trades = null;
	protected Map<String, Stock> Stocks = null;
	
	private static Market instance = null;
	
	private Market() {
		Trades = new HashMap<String, List<Trade>>();
		Stocks = new HashMap<String, Stock>();
	}
	
	public static Market getInstance() {
		if (instance == null) {
			instance = new Market();
		}
		return instance;
	}

	/**
	 * @return the trades
	 */
	public Map<String, List<Trade>> getTrades() {
		return Trades;
	}

	/**
	 * @param trades the trades to set
	 */
	public void setTrades(Map<String, List<Trade>> trades) {
		Trades = trades;
	}
	
	
	/**
	 * @return the stocks
	 */
	public Map<String, Stock> getStocks() {
		return Stocks;
	}

	/**
	 * @param stocks the stocks to set
	 */
	public void setStocks(Map<String, Stock> stocks) {
		Stocks = stocks;
	}

	public List<Trade> getTradesByStockCode(String code) {
		if (this.Trades.containsKey(code))
			return this.Trades.get(code);
		return Collections.emptyList();
	}
	
	
	public boolean registerTrade(Trade t) {
		Stock s = t.getStock();
		if (s != null) {
			String code = s.getSymbol();
			if (this.Trades.containsKey(code)) {
				List<Trade> list = this.Trades.get(code);
				if (list == null) {
					list = new ArrayList<Trade>();
				}
				list.add(t);
				return true;	
			}
			else {
				List<Trade> list = new ArrayList<Trade>();
				list.add(t);
				this.Trades.put(code, list);
				return true;
			}
		}
		return false;
	}
	
	/** 
	 * Register the new Stock or update it if a stock with the same code Already exist.
	 * 
	 * @param s Stock, must not be NULL
	 * @return boolean success or failure
	 */
	
	public boolean registerStock(Stock s) {
		if (s != null) {
			String code = s.getSymbol();
			this.Stocks.put(code, s);
			return true;
		}
		return false;
	}
	
	/** 
	 * Remove the Stock if it exist.
	 * 
	 * @param s Stock, must not be NULL
	 * @return boolean success or failure
	 */
	
	public boolean deleteStock(Stock s) {
		if (s != null) {
			String code = s.getSymbol();
			if (this.Stocks.containsKey(code)) {
				this.Stocks.remove(code);
				return true;
			}
		}
		return false;
	}

}
