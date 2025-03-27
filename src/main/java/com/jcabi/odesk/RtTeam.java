/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import com.jcabi.http.Request;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * RESTful {@link Team}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@ToString
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = { "entry", "ref" })
final class RtTeam implements Team {

    /**
     * Request to use.
     */
    private final transient Request entry;

    /**
     * Team reference.
     */
    private final transient String ref;

    /**
     * Public ctor.
     * @param req Request
     * @param name Team ref/name
     */
    RtTeam(final Request req, final String name) {
        this.entry = req;
        this.ref = name;
    }

    @Override
    @NotNull(message = "adjustments is never NULL")
    public Adjustments adjustments() {
        return new RtAdjustments(this.entry, this.ref);
    }
}
