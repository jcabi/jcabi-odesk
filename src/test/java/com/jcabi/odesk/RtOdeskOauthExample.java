/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.log.Logger;
import java.util.Scanner;
import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Test;
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
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @checkstyle ClassDataAbstractionCoupling (500 lines)
 */
public final class RtOdeskOauthExample {

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
     * @throws Exception If some problem inside
     */
    @Test
    public void obtainsAccessToken() throws Exception {
        Assume.assumeThat(RtOdeskOauthExample.KEY, Matchers.notNullValue());
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
        final Scanner input = new Scanner(System.in);
        final Verifier verifier = new Verifier(input.nextLine());
        final Token access = service.getAccessToken(rqst, verifier);
        Logger.info(this, "access token is: %s", access.getToken());
        Logger.info(this, "access token secret is: %s", access.getSecret());
    }

}
