/**
 * Copyright (c) 2012-2015, jcabi.com
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
import com.jcabi.http.Request;
import com.jcabi.http.Response;
import com.jcabi.http.Wire;
import com.jcabi.immutable.Array;
import com.jcabi.log.Logger;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import javax.ws.rs.core.HttpHeaders;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * OAuth Wire.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@ToString
@EqualsAndHashCode(of = { "origin", "key", "secret", "token", "tsecret" })
public final class OAuthWire implements Wire {

    /**
     * Original wire.
     */
    private final transient Wire origin;

    /**
     * Application key.
     */
    private final transient String key;

    /**
     * Application secret.
     */
    private final transient String secret;

    /**
     * Access token.
     */
    private final transient String token;

    /**
     * Access token secret.
     */
    private final transient String tsecret;

    /**
     * Public ctor.
     * @param wire Original wire
     * @param akey App key
     * @param scrt App secret
     * @param tkn OAuth access token
     * @param tscrt OAuth access token secret part
     * @checkstyle ParameterNumber (10 lines)
     */
    public OAuthWire(final Wire wire, final String akey, final String scrt,
        final String tkn, final String tscrt) {
        this.origin = wire;
        this.key = akey;
        this.secret = scrt;
        this.token = tkn;
        this.tsecret = tscrt;
    }

    // @checkstyle ParameterNumber (10 lines)
    @Override
    public Response send(final Request req, final String home,
        final String method,
        final Collection<Map.Entry<String, String>> headers,
        final InputStream content, final int connect, final int read)
        throws IOException {
        final OAuthService service = new ServiceBuilder()
            .provider(OAuthWire.OdeskApi.class)
            .apiKey(this.key)
            .apiSecret(this.secret)
            .debugStream(Logger.stream(Level.FINE, this))
            .build();
        final String[] parts = home.split("\\?", 2);
        final OAuthRequest oauth = new OAuthRequest(
            Verb.valueOf(method), parts[0]
        );
        if (parts.length == 2) {
            for (final String pair : parts[1].split("&")) {
                final String[] eqn = pair.split("=", 2);
                final String value;
                if (eqn.length == 2) {
                    value = URLDecoder.decode(eqn[1], "UTF-8");
                } else {
                    value = "";
                }
                oauth.addQuerystringParameter(eqn[0], value);
            }
        }
        service.signRequest(new Token(this.token, this.tsecret), oauth);
        return this.origin.send(
            req, oauth.getCompleteUrl(), method,
            new Array<Map.Entry<String, String>>(headers).with(
                new AbstractMap.SimpleEntry<String, String>(
                    HttpHeaders.AUTHORIZATION,
                    oauth.getHeaders().get(HttpHeaders.AUTHORIZATION)
                )
            ),
            content,
            connect, read
        );
    }

    /**
     * Odesk provider.
     * @link https://github.com/fernandezpablo85/scribe-java/pull/438
     */
    public static final class OdeskApi extends DefaultApi10a {
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

}
