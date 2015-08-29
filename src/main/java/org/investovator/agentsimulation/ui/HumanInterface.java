/*
 * investovator, Stock Market Gaming framework
 *    Copyright (C) 2013  investovator
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.investovator.agentsimulation.ui;

import net.sourceforge.jasa.agent.SimpleTradingAgent;
import net.sourceforge.jasa.agent.strategy.TruthTellingStrategy;
import net.sourceforge.jasa.market.Order;
import org.investovator.agentsimulation.exchange.Exchange;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: ishan
 * @version: ${Revision}
 */
public class HumanInterface {

    public JPanel panel1;
    private JTextField stockName;
    private JRadioButton buyRadioButton;
    private JRadioButton sellRadioButton;
    private JTextField quantityField;
    private JRadioButton limitRadioButton;
    private JRadioButton marketRadioButton;
    private JTextField priceField;
    private JButton sendButton;

    protected Exchange market = null;

    public HumanInterface(Exchange market ) {
        this.market=market;

        sendButton.addActionListener(new SendButtonActionListener(market));



    }

    class SendButtonActionListener implements ActionListener{
        private Exchange market;

        SendButtonActionListener(Exchange market) {
            this.market = market;

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Clicked");
            //get the stock
            String stockID=stockName.getText();
            //get the side
            boolean isBuy=false;
            if (buyRadioButton.isSelected()){
                isBuy=true;
            }
            double price=10;
            //get the order type
            boolean isMarketOrder=false;
            if(marketRadioButton.isSelected()){
                isMarketOrder=true;
            }
            else{
                price=Double.parseDouble(priceField.getText());
            }
            //get the quantityField
            int quantity=Integer.parseInt(quantityField.getText());

            //create the agent
            SimpleTradingAgent agent=new SimpleTradingAgent();
            agent.setStrategy(new TruthTellingStrategy());

            //create the order
            Order order=new Order(agent,quantity,price,isBuy);
            //order.setSecurityID(stockID);
            //send the order
            market.placeOrder( stockName.getText(),order);

        }
    }


}