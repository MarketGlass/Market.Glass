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

package org.investovator.agentsimulation.multiasset.report.statistics;

import net.sourceforge.jabm.event.SimEvent;
import net.sourceforge.jabm.util.Resetable;
import net.sourceforge.jasa.event.OrderPlacedEvent;
import net.sourceforge.jasa.market.Order;
import net.sourceforge.jasa.market.auctioneer.ContinuousDoubleAuctioneer;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;
import java.util.*;

/**
 * @author rajith
 * @version ${Revision}
 */
public class TopOrdersStatReport implements MultiStatReport, Serializable, Cloneable, Resetable {

    protected List<Number> topBuy;
    protected List<Number> topSell;

    protected int size;

    @Override
    public void eventOccurred(SimEvent event) {
        if(event instanceof OrderPlacedEvent){
            Order order = ((OrderPlacedEvent) event).getOrder();
            ContinuousDoubleAuctioneer auctioneer = (ContinuousDoubleAuctioneer) ((OrderPlacedEvent) event)
                    .getAuction().getAuctioneer();

            if (order.isBid()) {
                topSell = addToList(auctioneer.getUnmatchedBids());
            } else {
                topBuy = addToList(auctioneer.getUnmatchedAsks());
            }
        }
    }

    @Override
    public Map<Object, Number> getVariableBindings() {
        return null;  //Do nothing
    }

    @Override
    public Map<String, List<Number>> getStatValues() {
        Map<String, List<Number>> values = new HashMap<>();
        values.put("BUY", topBuy);
        values.put("SELL", topSell);
        return values;
    }

    @Override
    public String getName() {
        return "top order report";
    }

    @Override
    public void reset() {
        topBuy.clear();
        topSell.clear();
    }

    protected List<Number> addToList(List<Order> list) {

        List <Number> priceList = new ArrayList<>();
        for (Order order : list){
            priceList.add(order.getPrice());
        }
        Collections.sort(priceList, Collections.reverseOrder());
        return priceList.size() > (size - 1) ?  priceList.subList(0, size) : priceList;
    }

    @Required
    public void setTopSize(int size){
        this.size = size;
    }
}
