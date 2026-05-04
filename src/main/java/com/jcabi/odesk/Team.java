/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;

/**
 * Team.
 * @since 0.1
 */
@FunctionalInterface
@Immutable
public interface Team {

    /**
     * Get adjustments.
     * @return Adjustments
     * @see <a href="http://developers.odesk.com/w/page/25400171/Custom%20Payment%20API">Custom Payment API</a>
     */
    Adjustments adjustments();
}
