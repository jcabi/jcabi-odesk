/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
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
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@ToString
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = "ent")
public final class RtOdesk implements Odesk {

    /**
     * Request to use.
     */
    private final transient Request ent;

    /**
     * Public ctor.
     * @param key App key
     * @param secret App secret
     * @param token OAuth access token
     * @param tsecret OAuth access token secret part
     * @checkstyle ParameterNumber (10 lines)
     */
    public RtOdesk(final String key, final String secret,
        final String token, final String tsecret) {
        this.ent = new JdkRequest("https://www.upwork.com/api/hr")
            .through(VerboseWire.class)
            .through(RetryWire.class)
            .through(OAuthWire.class, key, secret, token, tsecret);
    }

    @Override
    public Request entry() {
        return this.ent;
    }

    @Override
    @NotNull(message = "teams is never NULL")
    public Teams teams() {
        return new RtTeams(this.ent);
    }

}
