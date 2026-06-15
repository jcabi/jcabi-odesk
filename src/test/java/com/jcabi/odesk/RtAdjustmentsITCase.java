/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import java.math.BigDecimal;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * Integration case for {@link RtAdjustments}.
 * @since 0.1
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
final class RtAdjustmentsITCase {

    /**
     * Team number.
     */
    private static final String TEAM =
        System.getProperty("failsafe.odesk.team");

    /**
     * Odesk we're working with.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @RegisterExtension
    private final transient OdeskRule rule = new OdeskRule();

    /**
     * RtAdjustments can list all items.
     * @throws Exception If some problem inside
     */
    @Test
    @Disabled
    void listsAllAdjustments() throws Exception {
        MatcherAssert.assertThat(
            this.rule.odesk()
                .teams()
                .team(RtAdjustmentsITCase.TEAM)
                .adjustments()
                .iterate(),
            Matchers.notNullValue()
        );
    }

    /**
     * RtAdjustments can make a bonus payment.
     * @throws Exception If some problem inside
     */
    @Test
    @Disabled
    @SuppressWarnings("PMD.UnnecessaryLocalRule")
    void makesBonusPayment() throws Exception {
        final String reference = this.rule.odesk()
            .teams()
            .team(RtAdjustmentsITCase.TEAM)
            .adjustments()
            .add("13369359", BigDecimal.TEN, "advance payment", "for tests");
        MatcherAssert.assertThat(reference, Matchers.notNullValue());
    }
}
