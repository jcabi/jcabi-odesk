/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

/**
 * Odesk provider.
 * @since 0.1
 * @link https://github.com/fernandezpablo85/scribe-java/pull/438
 */
public final class OdeskApi extends DefaultApi10a {

    @Override
    public String getAccessTokenEndpoint() {
        return "https://www.odesk.com/api/auth/v1/oauth/token/access";
    }

    @Override
    public String getAuthorizationUrl(final Token tkn) {
        return String.format(
            "https://www.odesk.com/services/api/auth?oauth_token=%s",
            tkn.getToken()
        );
    }

    @Override
    public String getRequestTokenEndpoint() {
        return "https://www.odesk.com/api/auth/v1/oauth/token/request";
    }
}
