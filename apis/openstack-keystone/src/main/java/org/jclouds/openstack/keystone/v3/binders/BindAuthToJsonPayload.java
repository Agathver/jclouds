/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.keystone.v3.binders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Predicates.instanceOf;
import static com.google.common.collect.Iterables.tryFind;
import static org.jclouds.openstack.keystone.v3.domain.Auth.Scope.DOMAIN;
import static org.jclouds.openstack.keystone.v3.domain.Auth.Scope.PROJECT;

import java.util.Map;

import org.jclouds.http.HttpRequest;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.json.Json;
import org.jclouds.openstack.keystone.auth.domain.TenantAndCredentials;
import org.jclouds.openstack.keystone.v3.domain.Auth;
import org.jclouds.openstack.keystone.v3.domain.Auth.Domain;
import org.jclouds.openstack.keystone.v3.domain.Auth.DomainScope;
import org.jclouds.openstack.keystone.v3.domain.Auth.Id;
import org.jclouds.openstack.keystone.v3.domain.Auth.ProjectScope;
import org.jclouds.openstack.keystone.v3.domain.Auth.Scope;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;
import org.jclouds.rest.internal.GeneratedHttpRequest;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

public abstract class BindAuthToJsonPayload<T> extends BindToJsonPayload implements MapBinder {

   protected BindAuthToJsonPayload(Json jsonBinder) {
      super(jsonBinder);
   }

   protected abstract Auth buildAuth(TenantAndCredentials<T> credentials, Scope scope);

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      checkArgument(checkNotNull(request, "request") instanceof GeneratedHttpRequest,
            "this binder is only valid for GeneratedHttpRequests!");
      GeneratedHttpRequest gRequest = (GeneratedHttpRequest) request;

      Optional<Object> authentication = tryFind(gRequest.getInvocation().getArgs(),
            instanceOf(TenantAndCredentials.class));
      checkArgument(authentication.isPresent(), "no credentials found in the api call arguments");

      @SuppressWarnings("unchecked")
      TenantAndCredentials<T> credentials = (TenantAndCredentials<T>) authentication.get();
      Scope scope = parseScope(credentials.scope());
      Auth auth = buildAuth(credentials, scope);

      R authRequest = super.bindToRequest(request, ImmutableMap.of("auth", auth));
      authRequest.getPayload().setSensitive(true);

      return authRequest;
   }
   
   private Scope parseScope(@Nullable String input) {
      if (input == null) return null;
      String[] parts = input.split(":");
      checkArgument(parts.length == 2, "Invalid scope: %s", input);
      checkArgument(PROJECT.equals(parts[0]) || DOMAIN.equals(parts[0]), "Scope prefix should be '%s' or '%s'",
            PROJECT, DOMAIN);
      return PROJECT.equals(parts[0]) ? ProjectScope.create(Id.create(parts[1])) : DomainScope.create(Domain
            .create(parts[1]));
   }

}
