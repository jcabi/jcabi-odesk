/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import com.jcabi.http.Request;
import com.jcabi.http.request.JdkRequest;
import com.jcabi.http.wire.RetryWire;
import com.jcabi.http.wire.VerboseWire;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Default RESTful implementation of {@link Odesk}.
 * @since 0.1
 */
@Immutable
@ToString
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = { "key", "secret", "token", "tsecret" })
public final class RtOdesk implements Odesk {

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
     * Token secret.
     */
    private final transient String tsecret;

    /**
     * Public ctor.
     * @param app Application key
     * @param scrt Application secret
     * @param tkn OAuth access token
     * @param tscrt OAuth access token secret part
     */
    public RtOdesk(final String app, final String scrt,
        final String tkn, final String tscrt) {
        this.key = app;
        this.secret = scrt;
        this.token = tkn;
        this.tsecret = tscrt;
    }

    @Override
    public Request entry() {
        return new JdkRequest("https://www.upwork.com/api/hr")
            .through(VerboseWire.class)
            .through(RetryWire.class)
            .through(OAuthWire.class, this.key, this.secret, this.token, this.tsecret);
    }

    @Override
    @NotNull(message = "teams is never NULL")
    public Teams teams() {
        return new RtTeams(this.entry());
    }
}
