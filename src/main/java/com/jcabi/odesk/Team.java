/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;

/**
 * Team.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface Team {

    /**
     * Get adjustments.
     * @return Adjustments
     * @see <a href="http://developers.odesk.com/w/page/25400171/Custom%20Payment%20API">Custom Payment API</a>
     */
    Adjustments adjustments();

}
