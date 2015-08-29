package org.investovator.agentsimulation.exchange;

import net.sourceforge.jasa.market.AuctionException;
import net.sourceforge.jasa.market.MarketSimulation;
import net.sourceforge.jasa.market.Order;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public class Exchange {


    static Logger logger = Logger.getLogger(Exchange.class);

    private HashMap<String, MarketSimulation> stocks = new HashMap<String, MarketSimulation>();

    public void setStocks(List<MarketSimulation> stocks){

        Iterator<MarketSimulation> iterator = stocks.iterator();

        while (iterator.hasNext()) {
            MarketSimulation next =  iterator.next();
            this.stocks.put(next.getStockID() ,next);
        }

    }

    public HashMap<String, MarketSimulation> getStocksListed(){
        return stocks;
    }

    public void placeOrder(String stockID, Order order){

        MarketSimulation simulation = stocks.get(stockID);

        try {
            simulation.placeOrder(order);
        } catch (AuctionException e) {
            logger.warn(e.getMessage());
            e.printStackTrace();
        }

    }

}
