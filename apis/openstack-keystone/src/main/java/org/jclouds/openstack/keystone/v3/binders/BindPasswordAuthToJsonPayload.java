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

import static java.util.Collections.singletonList;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.json.Json;
import org.jclouds.openstack.keystone.auth.domain.PasswordCredentials;
import org.jclouds.openstack.keystone.auth.domain.TenantAndCredentials;
import org.jclouds.openstack.keystone.v3.domain.Auth;
import org.jclouds.openstack.keystone.v3.domain.Auth.Identity;
import org.jclouds.openstack.keystone.v3.domain.Auth.Identity.PasswordAuth;
import org.jclouds.openstack.keystone.v3.domain.Auth.Identity.PasswordAuth.UserAuth;
import org.jclouds.openstack.keystone.v3.domain.Auth.Identity.PasswordAuth.UserAuth.DomainAuth;
import org.jclouds.openstack.keystone.v3.domain.Auth.Scope;

@Singleton
public class BindPasswordAuthToJsonPayload extends BindAuthToJsonPayload<PasswordCredentials> {

   @Inject
   BindPasswordAuthToJsonPayload(Json jsonBinder) {
      super(jsonBinder);
   }

   @Override
   protected Auth buildAuth(TenantAndCredentials<PasswordCredentials> credentials, Scope scope) {
      PasswordCredentials creds = credentials.credentials();
      DomainAuth domain = DomainAuth.create(credentials.tenantName());
      UserAuth user = UserAuth.create(creds.username(), domain, creds.password());

      return Auth.create(Identity.create(singletonList("password"), null, PasswordAuth.create(user)), scope);
   }

}
