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

package org.investovator.agentsimulation.multiasset.report.timeseries;

import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jasa.event.OrderPlacedEvent;

/**
 * @author rajith
 * @version ${Revision}
 */
public class MidPriceReportTimeSeriesVariables extends PriceReportTimeseriesVariables {

    public static final String NAME = "mid";

    @Override
    public String getName() {
        return NAME;
    }

    public double getPrice(OrderPlacedEvent event) {
        return event.getAuction().getQuote().getMidPoint();
    }

    @Override
    public void eventOccurred(SimEvent event) {
        if(event instanceof OrderPlacedEvent){
            this.price.add(getPrice((OrderPlacedEvent)event));
            this.time.add( ((OrderPlacedEvent) event).getTime());
        }
    }
}