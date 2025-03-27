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
 * Integration case for {@link RtOdesk}.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
public final class RtOdeskITCase {

    /**
     * Odesk we're working with.
     * @checkstyle VisibilityModifier (3 lines)
     */
    @Rule
    public final transient OdeskRule rule = new OdeskRule();

    /**
     * RtOdesk can authenticate itself.
     * @throws Exception If some problem inside
     */
    @Test
    public void authenticatesItself() throws Exception {
        final Odesk odesk = this.rule.odesk();
        MatcherAssert.assertThat(
            odesk,
            Matchers.notNullValue()
        );
    }

}
