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

package net.sourceforge.jasa.agent.strategy;

import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.market.Order;

/**
 * @author Wilhelm Eklund
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 * strategy where the quantity is fixed but the direction depends on weather
 * the market price is higher or lower than the agents own valuation
 *
 * @author rajith
 * @version $Revision$
 */
public class FixQVarDirStrategy extends FixedQuantityStrategyImpl {
	
	protected boolean isBuy;
	
	public FixQVarDirStrategy() {
		//super(agent);
	}
	
	public boolean modifyShout(Order shout) {
		if(shout==null){
			shout = new Order();
			isBuy = false;
		}else {
			isBuy = (getAgent().getValuation(auction) > auction.getCurrentPrice());
		}
		shout.setIsBid(isBuy);
        shout.setAgent(getAgent());
        shout.setQuantity(getQuantity());
		return true;
	}
	
	public boolean isBuy(Market market) {
		return isBuy;
	}
	
	public boolean isSell() {
		return !isBuy;
	}
	
	public void setBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}
	
}
