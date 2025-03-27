/**
 * SPDX-FileCopyrightText: Copyright (c) 2012-2025 Yegor Bugayenko
 * SPDX-License-Identifier: MIT
 */
package com.jcabi.odesk;

import com.jcabi.aspects.Immutable;
import com.jcabi.aspects.Loggable;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.http.response.RestResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import javax.json.JsonObject;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * RESTful {@link Teams}.
 *
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 0.1
 */
@Immutable
@ToString
@Loggable(Loggable.DEBUG)
@EqualsAndHashCode(of = "entry")
final class RtTeams implements Teams {

    /**
     * Request to use.
     */
    private final transient Request entry;

    /**
     * Public ctor.
     * @param req Request
     */
    RtTeams(final Request req) {
        this.entry = req;
    }

    @Override
    public Iterable<String> iterate() throws IOException {
        final Collection<JsonObject> teams = this.entry
            .uri().path("v2/teams.json").back()
            .fetch()
            .as(RestResponse.class)
            .assertStatus(HttpURLConnection.HTTP_OK)
            .as(JsonResponse.class)
            .json().readObject().getJsonArray("teams")
            .getValuesAs(JsonObject.class);
        final Collection<String> refs = new ArrayList<String>(teams.size());
        for (final JsonObject team : teams) {
            refs.add(team.getString("reference"));
        }
        return refs;
    }

    @Override
    @NotNull(message = "team is never NULL")
    public Team team(@NotNull(message = "ref can't be NULL") final String ref) {
        return new RtTeam(this.entry, ref);
    }
}
