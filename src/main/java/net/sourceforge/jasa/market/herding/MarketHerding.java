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

package net.sourceforge.jasa.market.herding;

import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.report.herding.OrderBookSimpleAverageReport;

import java.util.List;

/**
 *
 * @author Wilhelm Eklund
 * @author rajith
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 */
public interface MarketHerding extends Market {

    public OrderBookSimpleAverageReport getHerdAllReport();

    public OrderBookSimpleAverageReport getHerdTopReport();

    public List getFitnessList();

    public void sortFitnessList();

    public void setCanGoBankrupt(boolean b);
}
