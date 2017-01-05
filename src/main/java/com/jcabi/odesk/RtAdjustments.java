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
