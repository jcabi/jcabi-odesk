/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import com.jcabi.http.Request;
import javax.validation.constraints.NotNull;

/**
 * Odesk.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface Odesk {

    /**
     * RESTful request, an entry point to the Github API.
     * @return Request
     * @since 0.11
     */
    @NotNull(message = "request is never NULL")
    Request entry();

    /**
     * Get teams.
     * @return Teams
     */
    @NotNull(message = "teams is never NULL")
    Teams teams();

}
