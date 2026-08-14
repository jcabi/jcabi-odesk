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
 * Integration case for {@link RtOdesk}.
 * @since 0.1
 */
final class RtOdeskITCase {

    /**
     * Odesk we're working with.
     */
    @RegisterExtension
    private final transient OdeskRule rule = new OdeskRule();

    /**
     * RtOdesk can authenticate itself.
     */
    @Test
    void authenticatesItself() {
        MatcherAssert.assertThat(
            this.rule.odesk(),
            Matchers.notNullValue()
        );
    }
}
