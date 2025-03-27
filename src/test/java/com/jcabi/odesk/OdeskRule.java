/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
