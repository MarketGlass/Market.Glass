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

package net.sourceforge.jasa.agent.strategy.herdmentality;

import net.sourceforge.jasa.agent.AbstractTradingAgent;
import net.sourceforge.jasa.agent.herdmentality.HerdingAgent;
import net.sourceforge.jasa.agent.herdmentality.HerdingObject;
import net.sourceforge.jasa.agent.strategy.AbstractTradingStrategy;
import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.market.Order;
import net.sourceforge.jasa.report.herding.AbstractOrderBookReport;

/**
 *
 * @author Wilhelm Eklund
 * @author rajith
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 */
public abstract class AbstractHerdingStrategy extends AbstractTradingStrategy implements HerdingObject {
	
	protected AbstractOrderBookReport orderBookReport;
	
	public AbstractHerdingStrategy() {
		// TODO Auto-generated constructor stub
	}

	public AbstractHerdingStrategy(AbstractTradingAgent agent, AbstractOrderBookReport orderBookReport) {
		super(agent);
		this.orderBookReport = orderBookReport;
	}
	
	@Override
	abstract public int determineQuantity(Market auction);
		//return orderBookReport.getQuantity();
	
	abstract public boolean isBid();
		//return orderBookReport.isBid();
	
	
	public boolean modifyShout(Order shout){
		shout.setQuantity(determineQuantity(auction));
		
		shout.setIsBid(isBid());
		
		shout.setPrice(getAgent().getValuation(auction));
		
		return super.modifyShout(shout);
	}
	
	public boolean isHerdTop() {
		return this.orderBookReport.isHerdTop();
	}
	
	public AbstractOrderBookReport getOrderBookReport() {
		// TODO Auto-generated method stub
		return ((HerdingAgent)agent).getOrderBookReport();
	}

    public void setOrderBookReport(AbstractOrderBookReport orderBookReport){
        this.orderBookReport = orderBookReport;
    }
}
