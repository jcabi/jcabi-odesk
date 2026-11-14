/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.http.Request;
import com.jcabi.http.request.FakeRequest;
import java.io.ByteArrayInputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link OAuthWire}.
 *
 * @since 1.0
 */
final class OAuthWireTest {

    /**
     * OAuthWire preserves encoded query parameter names.
     *
     * @throws Exception If some problem inside
     */
    @Test
    void preservesQueryParameterNames() throws Exception {
        for (final String name : Arrays.asList(
            "filter%5Bname%5D", "first%20name", "first%2Bname", "first+name"
        )) {
            new OAuthWire(
                (self, home, method, headers, content, connect, read) -> {
                    MatcherAssert.assertThat(
                        URLDecoder.decode(
                            URI.create(home).getRawQuery(), StandardCharsets.UTF_8
                        ),
                        Matchers.equalTo(
                            URLDecoder.decode(name, StandardCharsets.UTF_8)
                                .concat("=alice")
                        )
                    );
                    return new FakeRequest()
                        .withStatus(HttpURLConnection.HTTP_OK).fetch();
                },
                "key", "secret", "token", "token-secret"
            ).send(
                new FakeRequest(),
                "https://www.upwork.com/api/hr/v2/teams.json?"
                    .concat(name).concat("=alice"),
                Request.GET, Collections.emptyList(),
                new ByteArrayInputStream(new byte[0]), 0, 0
            );
        }
    }
}
