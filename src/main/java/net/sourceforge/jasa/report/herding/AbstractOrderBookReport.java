/*
 * investovator, Stock Market Gaming Framework
 *     Copyright (C) 2013  investovator
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.sourceforge.jasa.report.herding;

import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jabm.util.Parameterizable;
import net.sourceforge.jabm.util.Resetable;
import net.sourceforge.jabm.util.SummaryStats;
import net.sourceforge.jasa.event.EndOfDayEvent;
import net.sourceforge.jasa.market.FourHeapOrderBook;
import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.market.OrderBook;
import net.sourceforge.jasa.market.auctioneer.AbstractAuctioneer;
import net.sourceforge.jasa.report.AbstractAuctionReport;
import org.apache.log4j.Logger;

/**
 * <p>
 * An abstract implementation of AuctionReport that provides functionality
 * common to all reports.
 * </p>
 * 
 * @author Steve Phelps
 * @version $Revision: 1.8 $
 */

public abstract class AbstractOrderBookReport
        extends AbstractAuctionReport implements Resetable, Parameterizable {

	static Logger logger = Logger.getLogger(AbstractOrderBookReport.class);

	protected FourHeapOrderBook orderBook;
	protected boolean isHerdTop = false;

	public AbstractOrderBookReport(Market auction) {
		this.auction = auction;
		this.orderBook = (FourHeapOrderBook)((AbstractAuctioneer)
                auction.getAuctioneer()).getOrderBook();
	}

    public AbstractOrderBookReport() {
        //TODO
    }

	public void setAuction(Market auction) {
		this.auction = auction;
		this.orderBook = (FourHeapOrderBook)((AbstractAuctioneer)
                auction.getAuctioneer()).getOrderBook();
		logger.debug("Set market to " + auction);
	}

	public OrderBook getOrderBook() {
		return this.orderBook;
	}
	
	@Override
	public void eventOccurred(SimEvent event) {
		super.eventOccurred(event);
		if (event instanceof EndOfDayEvent) {
			//System.out.println("__AbstractOrderBookReport.eventOccured at time=" + auction.getDay() + ": \n" + event);
			onEndOfDay();
		}
	}
	
	public void produceUserOutput() {
	}
	
	// abstract methods actually used by all OrderBookReports
	public abstract void onEndOfDay();
	
	public abstract boolean isBid();
	
	public abstract double getValue();
	
	public abstract int getQuantity();
	
	public abstract SummaryStats getPricestat();
	
	public boolean isHerdTop(){
		return this.isHerdTop;
	}
}
