/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import java.io.IOException;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;

/**
 * Adjustments.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
public interface Adjustments {

    /**
     * Make a custom/bonus payment.
     * @param engagement Engagement reference number
     * @param charge Amount to be charged from the payer (can be zero)
     * @param comments Payment comments
     * @param notes Notes to add to the payment
     * @return Reference ID of a new adjustment
     * @throws IOException If fails due to IO problem
     * @see <a href="http://developers.odesk.com/w/page/25400171/Custom%20Payment%20API">Custom Payment API</a>
     * @checkstyle ParameterNumber (10 lines)
     * @since 0.7
     */
    @NotNull(message = "adjustment ID is never NULL")
    String add(
        @NotNull(message = "engagement ref can't be NULL") String engagement,
        @NotNull(message = "charge amount can't be NULL") BigDecimal charge,
        @NotNull(message = "comments can't be NULL") String comments,
        @NotNull(message = "notes can't be NULL") String notes)
        throws IOException;

    /**
     * Get list of all adjustments (their reference IDs).
     * @return List of them all
     * @throws IOException If fails due to IO problem
     * @see <a href="http://developers.odesk.com/w/page/25400171/Custom%20Payment%20API">Custom Payment API</a>
     */
    @NotNull(message = "iterable of adjustments is never NULL")
    Iterable<String> iterate() throws IOException;

}
