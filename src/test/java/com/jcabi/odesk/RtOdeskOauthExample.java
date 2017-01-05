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
