/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link Odesk}.
 * @since 0.1
 */
final class OdeskTest {

    /**
     * Odesk can be instantiated.
     */
    @Test
    void works() {
        MatcherAssert.assertThat(
            new RtOdesk("k", "s", "t", "ts"),
            Matchers.notNullValue()
        );
    }
}
