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

import net.sourceforge.jabm.util.SummaryStats;
import net.sourceforge.jasa.agent.herdmentality.AbstractHerdingAgent;
import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.market.Order;
import org.springframework.beans.factory.annotation.Required;

import java.util.Iterator;

/**
 *
 * @author Wilhelm Eklund
 * @author rajith
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 */
public class OrderBookSimpleAverageReport extends AbstractOrderBookReport {
	
	protected int nbrBids;
	protected int nbrAsks;
	protected SummaryStats priceStat;
	protected SummaryStats quantityStat;
	
	public OrderBookSimpleAverageReport(Market auction) {
		super(auction);
		priceStat = new SummaryStats();
		quantityStat = new SummaryStats();
		// TODO Auto-generated constructor stub
	}

    public OrderBookSimpleAverageReport () {
        priceStat = new SummaryStats();
        quantityStat = new SummaryStats();
    }

    @Required
    public void setAuction(Market auction){
        super.setAuction(auction);
    }

	public void reset(){
		this.priceStat.reset();
		this.quantityStat.reset();
	}
	
	@Override
	public void onEndOfDay() {
		//System.out.println("__OrderBookSimpleBidAskReport.onEndOfDay(2)");
		int bNbr = 0; 
		int aNbr = 0; 
		Iterator<Order> iBid = orderBook.bidIterator();
		Iterator<Order> iAsk = orderBook.askIterator();
		Order now;
		double rankpc;
		while(iBid.hasNext()){
			now = iBid.next();
			if(this.isHerdTop){
				rankpc = ((AbstractHerdingAgent)now.getAgent()).getRankingPercent();
				//high rankPercent --> bad fitness
				if(rankpc>0.05){
					//go to next iteration of while loop, do not add this order to statistics
					continue;
				}
			}
			if(now != null){
				bNbr++;
				priceStat.newData(now.getPrice());
				quantityStat.newData(now.getQuantity());
			}
		}
		while(iAsk.hasNext()){
			now = iAsk.next();
			if(now != null){
				aNbr++;
				priceStat.newData(now.getPrice());
				quantityStat.newData(now.getQuantity());
			}
		}
		this.nbrAsks = aNbr;
		this.nbrBids = bNbr;
	}
	
	public boolean isBid(){
		return this.nbrBids > this.nbrAsks;
	}
	
	public double getValue(){
		return this.priceStat.getMean();
	}
	
	public int getQuantity(){
		return (int)this.quantityStat.getMean();
	}
	
	public SummaryStats getPricestat(){
		return priceStat;
	}
}
