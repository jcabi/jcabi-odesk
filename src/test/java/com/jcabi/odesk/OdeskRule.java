/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.log.Logger;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Extension that creates {@link Odesk} instance.
 * @since 0.3
 */
final class OdeskRule implements BeforeEachCallback {

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

    @Override
    public void beforeEach(final ExtensionContext context) {
        if (OdeskRule.KEY == null) {
            Logger.warn(
                this,
                "sys prop failsafe.odesk.key is not set, skipping"
            );
            Assumptions.assumeTrue(false, "failsafe.odesk.key is not set");
        }
        this.subj = new RtOdesk(
            OdeskRule.KEY,
            OdeskRule.SECRET,
            OdeskRule.TOKEN,
            OdeskRule.TSECRET
        );
    }

    /**
     * Get odesk.
     * @return Odesk
     */
    Odesk odesk() {
        return this.subj;
    }
}
