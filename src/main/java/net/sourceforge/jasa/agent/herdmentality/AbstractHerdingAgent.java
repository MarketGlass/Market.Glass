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

package net.sourceforge.jasa.agent.herdmentality;

import net.sourceforge.jabm.EventScheduler;
import net.sourceforge.jabm.util.SummaryStats;
import net.sourceforge.jasa.agent.AbstractTradingAgent;
import net.sourceforge.jasa.market.Market;
import net.sourceforge.jasa.market.herding.MarketHerding;
import net.sourceforge.jasa.report.herding.AbstractOrderBookReport;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Wilhelm Eklund
 * @author rajith
 * W. Eklund, "Agent-based modeling of herd mentality in the stock market,"
 * 2011.
 * Agents of this type have a fixed volume, and they trade units equal to their
 * volume in each round of the market.
 *
 *
 */

public abstract class AbstractHerdingAgent extends AbstractTradingAgent implements HerdingAgent {
	
	protected AbstractOrderBookReport orderBookReport;

    /**
     * Field to keep track of how this agents fitness has been on average
     */
    protected SummaryStats fitnessStats = new SummaryStats();

    /**
     * Field to keep track of how this agents fortune has been on average
     */
    protected SummaryStats fortuneStats = new SummaryStats();

    /**
     * Total fortune last time 'fitness' was calculated
     */
    protected double oldFortune = 0;
    protected double fitness = 0;
    protected int daysUntilUpdateFitness = 20;
    protected int updateFitnessInterval = 20;
	
	public AbstractHerdingAgent(EventScheduler scheduler, boolean top) {
		super(scheduler);
		if(top){
			this.orderBookReport = ((MarketHerding)scheduler).getHerdTopReport();
		} else {
			this.orderBookReport = ((MarketHerding)scheduler).getHerdAllReport();
		}
	}

    public AbstractHerdingAgent (){
        super(null);
    }

	public AbstractOrderBookReport getOrderBookReport(){
		return this.orderBookReport;
	}
	
	/*
	 * The following three methods are required for herding agents but not used in the real implementation
	 */
	@Override
	public boolean alignment() {
		return this.orderBookReport.isBid();
	}

	@Override
	public double cohesion() {
		return this.orderBookReport.getValue();
	}

	@Override
	public double separation() {
		return this.orderBookReport.getPricestat().getStdDev();
	}


	@Override
	public boolean isHerdTop() {
		return this.orderBookReport.isHerdTop();
	}

    public double getRankingPercent(){
        Market market = getMarket();
        if(market.getDay()<1){
            return 0.5;
        }
        int ranknbr = getRankingNbr();
        int totnbr = ((MarketHerding)(getMarket())).getFitnessList().size();
        double rankpc = ((double)ranknbr)/((double)totnbr);
        return rankpc;
    }

    /**
     * Get current fitness of the agent.
     */
    public double getFitness(){
        return this.fitness;
    }

    /**
     * Get the average fitness of this agent
     */
    public double getFitnessAverage(){
        return this.fitnessStats.getMean();
    }

    /**
     * getRanking -- get how good this agents fitness is compared to the other agents.
     */
    public int getRankingNbr(){
        Market market = (getMarket());
        List fitList = ((MarketHerding)market).getFitnessList();
        Iterator it = fitList.iterator();
        AbstractTradingAgent currAgent = this;
        int counter = 0;
        do{
            if(it.hasNext()){
                currAgent = (AbstractTradingAgent)it.next();
                //tprint("agent[" + counter + "]: " + currAgent);
            }
            counter++;
            if(counter>fitList.size()){
                // if agent is not in the collection
                return -1;
            }
        } while(currAgent!=this);
        return counter;
    }

    /*
 * Update the fitness of the agent.
 */
    public double updateFitness(){
        if(getMarket().getDay() > 1) // f�r att slippa NullPointerException eftersom auction inte �r initierad �nnu
        {
            // delta_payoff / (olfPayoff * updateFitnessInterval) ~ dp/dt
            double delta_payoff = (getFortune() - this.oldFortune);
            this.fitness = delta_payoff / (0.1 + this.oldFortune * this.updateFitnessInterval);
            this.daysUntilUpdateFitness = this.updateFitnessInterval;
            fitnessStats.newData(this.fitness);

            // remove itself from market fitnessCollection and add again to get new placing
            //List fitList = ((MarketFacade)getMarket()).getFitnessList();
            //tprint("##### Time:" + getMarket().getDay());
            //tprint("  [1] nbr " + getRankingNbr() + " of " + fitList.size());
            //fitList.remove(this);
            //fitList.add(this);
            //Collections.sort(fitList, ascendingFitnessComparator);

            //tprint("  [2] nbr " + getRankingNbr() + " of " + fitList.size());
            //tprint("  Fortune:" + this.getFortune() + "  Fitness:" + this.getFitness() + "\n  " + this);
        }
        return this.fitness;
    }

    /**
     * Determine the collected fortune for the agent
     * @return funds + stocks*currentPrice
     */
    public double getFortune(){
        return getFunds() + getStock()*getMarket().getCurrentPrice();
    }

    /**
     * Get the average fortune of this agent
     */
    public double getFortuneAverage(){
        return this.fortuneStats.getMean();
    }

    /*
 * Check if it is time to update the fitness of this agent
 */
    public void checkFitnessUpdate(){
        if(this.daysUntilUpdateFitness < 1){
            updateFitness();
        } else {
            this.daysUntilUpdateFitness--;
        }
    }

    /**
     * Determine whether or not this trader is active. Inactive traders do not
     * place shouts in the market, but do carry on learning through their
     * strategy.
     *
     * @return true if the trader is active.
     */
    public abstract boolean active();

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }
}
