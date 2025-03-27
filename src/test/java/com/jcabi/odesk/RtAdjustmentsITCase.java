/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import java.math.BigDecimal;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration case for {@link RtAdjustments}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
public final class RtAdjustmentsITCase {

    /**
     * Team number.
     */
    private static final String TEAM =
        System.getProperty("failsafe.odesk.team");

    /**
     * Odesk we're working with.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @Rule
    public final transient OdeskRule rule = new OdeskRule();

    /**
     * RtAdjustments can list all items.
     * @throws Exception If some problem inside
     */
    @Test
    @org.junit.Ignore
    public void listsAllAdjustments() throws Exception {
        final Adjustments adjustments = this.rule.odesk()
            .teams()
            .team(RtAdjustmentsITCase.TEAM)
            .adjustments();
        MatcherAssert.assertThat(
            adjustments.iterate(),
            Matchers.notNullValue()
        );
    }

    /**
     * RtAdjustments can make a bonus payment.
     * @throws Exception If some problem inside
     */
    @Test
    @org.junit.Ignore
    public void makesBonusPayment() throws Exception {
        final Adjustments adjustments = this.rule.odesk()
            .teams()
            .team(RtAdjustmentsITCase.TEAM)
            .adjustments();
        adjustments.add(
            "13369359", BigDecimal.TEN,
            "advance payment",
            "please, keep this money for the future, I'm testing :)"
        );
    }

}
