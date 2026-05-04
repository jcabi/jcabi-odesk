/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * Integration case for {@link RtTeams}.
 * @since 0.1
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
final class RtTeamsITCase {

    /**
     * Odesk we're working with.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @RegisterExtension
    private final transient OdeskRule rule = new OdeskRule();

    /**
     * RtTeams can list all teams.
     * @throws Exception If some problem inside
     */
    @Test
    void listsAllTeamReferences() throws Exception {
        MatcherAssert.assertThat(
            this.rule.odesk().teams().iterate(),
            Matchers.not(Matchers.emptyIterable())
        );
    }
}
