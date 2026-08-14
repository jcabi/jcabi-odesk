/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.log.Logger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * OAuth example for {@link RtOdesk}.
 *
 * <p>Run this example from command line like this:
 *
 * <pre>
 * $ mvn clean install -Dit.test=RtOdeskOauthExample \
 *   -Dfailsafe.odesk.key=... -Dfailsafe.odesk.secret=...
 * </pre>
 *
 * @since 0.1
 */
final class RtOdeskOauthExample {

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
     * Odesk access token can be obtained through OAuth.
     */
    @Test
    void obtainsAccessToken() {
        Assumptions.assumeTrue(
            RtOdeskOauthExample.KEY != null,
            "failsafe.odesk.key is not set"
        );
        final OAuthService service = new ServiceBuilder()
            .provider(OAuthWire.OdeskApi.class)
            .apiKey(RtOdeskOauthExample.KEY)
            .apiSecret(RtOdeskOauthExample.SECRET)
            .build();
        final Token rqst = service.getRequestToken();
        Logger.info(
            this, "authorization URL: %s (open it in a browser)",
            service.getAuthorizationUrl(rqst)
        );
        Logger.info(this, "enter Odesk verifier and press ENTER:");
        final Token access;
        try (Scanner input = new Scanner(System.in, StandardCharsets.UTF_8)) {
            access = service.getAccessToken(
                rqst, new Verifier(input.nextLine())
            );
        }
        Logger.info(this, "access token is: %s", access.getToken());
        Logger.info(this, "access token secret is: %s", access.getSecret());
        MatcherAssert.assertThat(
            access.getToken(),
            Matchers.not(Matchers.emptyString())
        );
    }
}
