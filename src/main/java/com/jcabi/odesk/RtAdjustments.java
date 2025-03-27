/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import com.jcabi.http.Request;
import com.jcabi.http.RequestURI;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import javax.json.JsonString;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * RESTful {@link Adjustments}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@ToString
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = "entry")
final class RtAdjustments implements Adjustments {

    /**
     * Request to use.
     */
    private final transient Request entry;

    /**
     * Public ctor.
     * @param req Request
     * @param name Team ref/name
     */
    RtAdjustments(final Request req, final String name) {
        this.entry = req.uri()
            .path("v2/teams")
            .path(name)
            .path("adjustments.json")
            .back();
    }

    // @checkstyle ParameterNumber (10 lines)
    @Override
    @NotNull(message = "adjustment ID is never NULL")
    public String add(
        @NotNull(message = "engagement ref can't be NULL")
        final String engagement,
        @NotNull(message = "charge amount can't be NULL")
        final BigDecimal charge,
        @NotNull(message = "comments can't be NULL") final String comments,
        @NotNull(message = "notes can't be NULL") final String notes)
        throws IOException {
        RequestURI uri = this.entry.uri()
            .queryParam("engagement__reference", engagement)
            .queryParam("comments", comments)
            .queryParam("notes", notes);
        if (charge != null && charge.compareTo(BigDecimal.ZERO) != 0) {
            uri = uri.queryParam("charge_amount", charge.toString());
        }
        return uri.back().method(Request.POST).fetch()
            .as(RestResponse.class)
            .assertStatus(HttpURLConnection.HTTP_OK)
            .as(JsonResponse.class)
            .json().readObject().getJsonObject("adjustment")
            .getString("reference");
    }

    @Override
    @NotNull(message = "iterable of adjustments is never NULL")
    public Iterable<String> iterate() throws IOException {
        final Collection<JsonString> values = this.entry.fetch()
            .as(RestResponse.class)
            .assertStatus(HttpURLConnection.HTTP_OK)
            .as(JsonResponse.class)
            .json().readObject().getJsonArray("adjustments")
            .getValuesAs(JsonString.class);
        final Collection<String> refs = new ArrayList<String>(values.size());
        for (final JsonString val : values) {
            refs.add(val.getString());
        }
        return refs;
    }
}
