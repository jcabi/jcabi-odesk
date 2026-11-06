/*
 * SPDX-FileCopyrightText: Copyright (c) 2012-2026 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.http.Request;
import com.jcabi.http.request.FakeRequest;
import com.jcabi.http.request.JdkRequest;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.Arrays;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RtAdjustments}.
 *
 * @since 1.0
 */
final class RtAdjustmentsTest {

    /**
     * RtAdjustments sends the supplied charge, including zero.
     *
     * @throws Exception If some problem inside
     */
    @Test
    void sendsChargeAmount() throws Exception {
        for (final BigDecimal charge : Arrays.asList(
            BigDecimal.ZERO, new BigDecimal("0.00"), BigDecimal.TEN
        )) {
            final Request request = new JdkRequest("http://localhost").through(
                (self, home, method, headers, content, connect, read) -> {
                    MatcherAssert.assertThat(method, Matchers.equalTo(Request.POST));
                    MatcherAssert.assertThat(
                        home,
                        Matchers.containsString(
                            "charge_amount=".concat(charge.toString())
                        )
                    );
                    return new FakeRequest()
                        .withStatus(HttpURLConnection.HTTP_OK)
                        .withBody("{\"adjustment\":{\"reference\":\"123\"}}")
                        .fetch();
                }
            );
            MatcherAssert.assertThat(
                new RtAdjustments(request, "team")
                    .add("engagement", charge, "comments", "notes"),
                Matchers.equalTo("123")
            );
        }
    }
}
