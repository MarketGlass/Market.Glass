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

package org.investovator.agentsimulation.api.utils;

import net.sourceforge.jasa.agent.strategy.TruthTellingStrategy;
import net.sourceforge.jasa.market.Account;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author rajith
 * @version $Revision$
 */
public class HumanAgent {

    private String userName;
    private HashMap <String, HollowTradingAgent> hollowAgents;

    private Account account;

    public HumanAgent(String userName, double initFunds){
        this.userName = userName;
        this.account = new Account(userName, initFunds);
        this.hollowAgents = new HashMap<String, HollowTradingAgent>();
    }

    public String getUserName () {
        return userName;
    }

    public HollowTradingAgent getHollowTradingAgent(String stockId){
        return hollowAgents.get(stockId);
    }

    public Collection<HollowTradingAgent> getHollowTradingAgents(){
        return hollowAgents.values();
    }

    public boolean isHollowAgentAvailable(String stockId){
        return hollowAgents.containsKey(stockId);
    }

    public void addHollowAgentToStock(String stockId){
        HollowTradingAgent agent = new HollowTradingAgent(account, userName);
        agent.setStrategy(new TruthTellingStrategy());
        hollowAgents.put(stockId, agent);
    }

    public Account getAccount(){
        return account;
    }

    public HashMap<String, Integer> getAssets(){
        HashMap<String, Integer> assets = new HashMap<String, Integer>();
        for (String stockId : hollowAgents.keySet()){
           assets.put(stockId, hollowAgents.get(stockId).getStock());
        }
        return assets;
    }
}
