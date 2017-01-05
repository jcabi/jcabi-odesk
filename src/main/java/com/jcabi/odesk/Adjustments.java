/**
 * Copyright (c) 2012-2017, jcabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
