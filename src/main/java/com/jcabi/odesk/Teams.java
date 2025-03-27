/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import java.io.IOException;
import javax.validation.constraints.NotNull;

/**
 * Teams.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface Teams {

    /**
     * Get reference IDs of all teams.
     * @return List of reference IDs
     * @throws IOException If fails due to IO problem
     */
    @NotNull(message = "list of IDs is never NULL")
    Iterable<String> iterate() throws IOException;

    /**
     * Get team by reference.
     * @param ref Reference
     * @return Team
     */
    @NotNull(message = "team is never NULL")
    Team team(@NotNull(message = "ref can't be NULL") String ref);

}
