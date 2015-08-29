/*
 * investovator, Stock Market Gaming framework
 * Copyright (C) 2013  investovator
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

package net.sourceforge.jasa.agent.valuation.herdmentality;

import net.sourceforge.jabm.EventScheduler;
import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jasa.agent.TradingAgent;
import net.sourceforge.jasa.agent.herdmentality.HerdingAgent;
import net.sourceforge.jasa.agent.herdmentality.HerdingObject;
import net.sourceforge.jasa.agent.valuation.AbstractValuationPolicy;
import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.report.herding.AbstractOrderBookReport;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;

/**
 *
 * @author Wilhelm Eklund
 * @author rajith
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 */
public class HerdingValuer extends AbstractValuationPolicy implements Serializable, HerdingObject {
	
	//protected HerdingAgent agent;
    private AbstractOrderBookReport orderBookReport;
	
	public HerdingValuer(AbstractOrderBookReport orderBookReport) {
		this.setOrderBookReport(orderBookReport);
	}

    public HerdingValuer(){
        //TODO
    }
	
	public double determineValue(Market auction) {
		return orderBookReport.getValue();
	}
	
	public void consumeUnit(Market auction) {
		// Do nothing
	}
	
	public void eventOccurred(SimEvent event) {
		// Do nothing
	}
	
	public void reset() {
	}
	
	public void initialise() {
	    orderBookReport.reset();
	}
	
	public void setAgent(TradingAgent agent) {
		this.setOrderBookReport(((HerdingAgent)agent).getOrderBookReport());
	}
	
	public String toString() {
		return "(" + getClass() + " value:" + determineValue(null) + ")";
	}
	
	@Override
	public void subscribeToEvents(EventScheduler scheduler) {
		// Do nothing
	}
	
	public AbstractOrderBookReport getOrderBookReport(){
		return this.orderBookReport;
	}
	
	public boolean isHerdTop(){
		return orderBookReport.isHerdTop();
	}

    @Required
    public void setOrderBookReport(AbstractOrderBookReport orderBookReport) {
        this.orderBookReport = orderBookReport;
    }
}