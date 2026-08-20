/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
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
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

/**
 * OAuth Wire.
 * @since 0.1
 */
@Immutable
@ToString
@EqualsAndHashCode(of = { "origin", "key", "secret", "token", "tsecret" })
public final class OAuthWire implements Wire {

    /**
     * HTTP {@code Authorization} header name.
     */
    private static final String AUTHORIZATION = "Authorization";

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
     */
    public OAuthWire(final Wire wire, final String akey, final String scrt,
        final String tkn, final String tscrt) {
        this.origin = wire;
        this.key = akey;
        this.secret = scrt;
        this.token = tkn;
        this.tsecret = tscrt;
    }

    @Override
    public Response send(final Request req, final String home,
        final String method,
        final Collection<Map.Entry<String, String>> headers,
        final InputStream content, final int connect, final int read)
        throws IOException {
        final OAuthService service = new ServiceBuilder()
            .provider(OdeskApi.class)
            .apiKey(this.key)
            .apiSecret(this.secret)
            .debugStream(Logger.stream(Level.FINE, this))
            .build();
        final String[] parts = home.split("\\?", 2);
        final OAuthRequest oauth = new OAuthRequest(
            Verb.valueOf(method), parts[0]
        );
        if (parts.length == 2) {
            for (final String pair : parts[1].split("&", -1)) {
                final String[] eqn = pair.split("=", 2);
                final String value;
                if (eqn.length == 2) {
                    value = URLDecoder.decode(eqn[1], StandardCharsets.UTF_8);
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
                new AbstractMap.SimpleEntry<>(
                    OAuthWire.AUTHORIZATION,
                    oauth.getHeaders().get(OAuthWire.AUTHORIZATION)
                )
            ),
            content,
            connect, read
        );
    }
}
