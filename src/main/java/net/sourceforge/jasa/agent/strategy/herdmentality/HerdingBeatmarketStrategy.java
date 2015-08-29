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
import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.report.herding.AbstractOrderBookReport;

/**
 *
 * @author Wilhelm Eklund
 * @author rajith
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 */
public class HerdingBeatmarketStrategy extends AbstractHerdingStrategy {

	public HerdingBeatmarketStrategy() {
		// TODO Auto-generated constructor stub
	}

	public HerdingBeatmarketStrategy(AbstractTradingAgent agent, AbstractOrderBookReport orderBookReport) {
		super(agent, orderBookReport);
	}

	@Override
	public int determineQuantity(Market auction) {
		return 1;
	}

	@Override
	public boolean isBid() {
		// if market wants to buy, it is time to SELL to them!
		return !(((HerdingAgent)agent).alignment());
	}

}
