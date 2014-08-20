/**
 * Copyright (c) 2012-2014, jcabi.com
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

import com.jcabi.log.Logger;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Rule that creates {@link Odesk} instance.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.3
 */
final class OdeskRule implements TestRule {

    /**
     * Odesk key.
     */
    private static final String KEY =
        System.getProperty("failsafe.odesk.key");

    /**
     * Odesk secret.
     */
    private static final String SECRET =
        System.getProperty("failsafe.odesk.secret");

    /**
     * Odesk token.
     */
    private static final String TOKEN =
        System.getProperty("failsafe.odesk.token");

    /**
     * Token secret.
     */
    private static final String TSECRET =
        System.getProperty("failsafe.odesk.tsecret");

    /**
     * Odesk we're working with.
     */
    private transient Odesk subj;

    /**
     * Get odesk.
     * @return Odesk
     */
    public Odesk odesk() {
        return this.subj;
    }

    @Override
    public Statement apply(final Statement stmt, final Description desc) {
        // @checkstyle IllegalThrows (10 lines)
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (OdeskRule.KEY == null) {
                    Logger.warn(
                        this,
                        "sys prop failsafe.odesk.key is not set, skipping"
                    );
                } else {
                    OdeskRule.this.connect();
                    stmt.evaluate();
                }
            }
        };
    }

    /**
     * Create Odesk subj.
     * @throws Exception If fails
     */
    private void connect() throws Exception {
        this.subj = new RtOdesk(
            OdeskRule.KEY,
            OdeskRule.SECRET,
            OdeskRule.TOKEN,
            OdeskRule.TSECRET
        );
    }

}
