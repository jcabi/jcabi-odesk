/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;

/**
 * Integration case for {@link RtTeams}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
public final class RtTeamsITCase {

    /**
     * Odesk we're working with.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @Rule
    public final transient OdeskRule rule = new OdeskRule();

    /**
     * RtTeams can list all teams.
     * @throws Exception If some problem inside
     */
    @Test
    public void listsAllTeamReferences() throws Exception {
        final Teams teams = this.rule.odesk().teams();
        MatcherAssert.assertThat(
            teams.iterate(),
            Matchers.not(Matchers.emptyIterable())
        );
    }

}
