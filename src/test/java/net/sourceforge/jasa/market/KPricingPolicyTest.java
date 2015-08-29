/*
 * JASA Java Auction Simulator API
 * Copyright (C) 2013 Steve Phelps
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package net.sourceforge.jasa.market;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sourceforge.jabm.Population;
import net.sourceforge.jabm.SpringSimulationController;
import net.sourceforge.jabm.init.BasicAgentInitialiser;
import net.sourceforge.jabm.mixing.RandomRobinAgentMixer;
import net.sourceforge.jabm.util.MathUtil;
import net.sourceforge.jasa.agent.MockTrader;
import net.sourceforge.jasa.agent.strategy.TruthTellingStrategy;
import net.sourceforge.jasa.market.auctioneer.ClearingHouseAuctioneer;
import net.sourceforge.jasa.market.rules.DiscriminatoryPricingPolicy;
import net.sourceforge.jasa.sim.PRNGTestSeeds;
import cern.jet.random.engine.MersenneTwister64;
import cern.jet.random.engine.RandomEngine;

/**
 * @author Steve Phelps
 * @version $Revision: 1.10 $
 */

public class KPricingPolicyTest extends TestCase {

	/**
	 * @uml.property name="auctioneer"
	 * @uml.associationEnd
	 */
	ClearingHouseAuctioneer auctioneer;

	/**
	 * @uml.property name="market"
	 * @uml.associationEnd
	 */
	MarketSimulation auction;

	/**
	 * @uml.property name="agents"
	 * @uml.associationEnd multiplicity="(0 -1)"
	 */
	MockTrader[] agents;
	
	RandomEngine prng;

	public KPricingPolicyTest(String name) {
		super(name);
	}

	public void setUp() {
		prng = new MersenneTwister64(PRNGTestSeeds.UNIT_TEST_SEED);
		auction = new MarketSimulation();
		auction.setSimulationController(new SpringSimulationController());
		auction.setPopulation(new Population());
		auction.setAgentMixer(new RandomRobinAgentMixer(prng));
		auction.setAgentInitialiser(new BasicAgentInitialiser());
		auctioneer = new ClearingHouseAuctioneer(auction);
		auction.setAuctioneer(auctioneer);
		auction.setPopulation(new Population());

		agents = new MockTrader[4];

		agents[0] = new MockTrader(this, 0, 0, 200, auction);
		agents[1] = new MockTrader(this, 0, 0, 150, auction);

		agents[2] = new MockTrader(this, 0, 0, 100, auction);
		agents[3] = new MockTrader(this, 0, 0, 50, auction);

		for (int i = 0; i < agents.length; i++) {
			TruthTellingStrategy strategy = new TruthTellingStrategy(agents[i]);
			agents[i].setStrategy(strategy);
			strategy.setBuy(i < 2);
			auction.register(agents[i]);
		}

	}

	/**
	 * Test that truthful agents transact at mid equilibrium price in a k=0.5 CH
	 * with uniform clearing.
	 */
//	public void testUniformPolicyEquilibriumPrice() {
//
//		EquilibriumReport eqStats = new EquilibriumReport(auction);
//		auctioneer.setPricingPolicy(new UniformPricingPolicy(0.5));
//		auction.setMaximumRounds(1);
//		auction.addReport(eqStats);
//		auction.run();
//
//		eqStats.calculate();
//		double ep = eqStats.calculateMidEquilibriumPrice();
//
//		for (int i = 0; i < agents.length; i++) {
//			assertTrue(MathUtil.approxEqual(ep, agents[i].lastWinningPrice));
//		}
//	}
	//TODO

	public void testPayAsBid() {

		auctioneer.setPricingPolicy(new DiscriminatoryPricingPolicy(1));
		auction.setMaximumRounds(1);
		auction.run();

		for (int i = 0; i < agents.length; i++) {
			if (agents[i].lastOrderFilled() && agents[i].isBuyer(auction)) {
				assertTrue(MathUtil.approxEqual(agents[i].lastWinningPrice, agents[i]
				    .getValuation(auction)));
			}
		}
	}

	public void testPayAsAsk() {

		auctioneer.setPricingPolicy(new DiscriminatoryPricingPolicy(0));
		auction.setMaximumRounds(1);
		auction.run();

		for (int i = 0; i < agents.length; i++) {
			if (agents[i].isSeller(auction)) {
				assertTrue(MathUtil.approxEqual(agents[i].lastWinningPrice, agents[i]
				    .getValuation(auction)));
			}
		}
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

	public static Test suite() {
		return new TestSuite(KPricingPolicyTest.class);
	}
}
