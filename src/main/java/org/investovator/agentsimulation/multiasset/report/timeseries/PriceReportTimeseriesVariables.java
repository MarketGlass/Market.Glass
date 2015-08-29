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
import net.sourceforge.jabm.report.XYReportVariables;

import java.io.Serializable;
import java.util.*;

/**
 * @author Amila Surendra
 * @version $Revision
 */
public abstract class PriceReportTimeseriesVariables implements Serializable,
        XYReportVariables {

    protected ArrayList<Number> price = new ArrayList<Number>();

    protected ArrayList<Number> time = new ArrayList<Number>();

    public static final String PRICE_VAR = "price";

    public Map<Object, ArrayList<Number>> getTimeseriesVariableBindings() {
        LinkedHashMap<Object,  ArrayList<Number>> result =
                new LinkedHashMap<Object,  ArrayList<Number>>();
        result.put(getName() + ".t", time);
        result.put(getName() + "." + PRICE_VAR, price);
        return result;
    }

    @Override
    public Map<Object, Number> getVariableBindings() {
        LinkedHashMap<Object, Number> result =
                new LinkedHashMap<Object, Number>();
        result.put(getName() + ".t", time.get(time.size()-1));
        result.put(getName() + "." + PRICE_VAR, price.get(price.size()-1));
        return result;
    }

    @Override
    public void compute(SimEvent ev) {
        eventOccurred(ev);
    }

    @Override
    public void dispose(SimEvent event) {
        // Do nothing
    }

    @Override
    public void initialise(SimEvent event) {
        this.price = new ArrayList<Number>();
        this.time = new ArrayList<Number>();
    }

    @Override
    public Number getX(int seriesIndex) {
        return this.time.get(seriesIndex);
    }

    @Override
    public Number getY(int seriesIndex) {
        return this.price.get(seriesIndex);
    }

    public int getNumberOfSeries() {
        return 1;
    }


    @Override
    public List<Object> getyVariableNames() {
        LinkedList<Object> result = new LinkedList<Object>();
        result.add(getName() + "." + PRICE_VAR);
        return result;
    }

    @Override
    public String getxVariableName() {
        return getName() + ".t";
    }

    @Override
    public abstract String getName();


}
