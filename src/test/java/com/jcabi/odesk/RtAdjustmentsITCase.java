/**
 * Copyright (c) 2012-2014, jcabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.odesk;

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
            "13369359", new Cash.S("10.0"),
            "advance payment",
            "please, keep this money for the future, I'm testing :)"
        );
    }

}
