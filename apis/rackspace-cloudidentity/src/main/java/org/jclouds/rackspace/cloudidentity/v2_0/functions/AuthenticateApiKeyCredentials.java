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
package org.jclouds.rackspace.cloudidentity.v2_0.functions;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jclouds.openstack.keystone.auth.config.CredentialType;
import org.jclouds.openstack.keystone.auth.domain.AuthInfo;
import org.jclouds.openstack.keystone.auth.domain.TenantAndCredentials;
import org.jclouds.openstack.keystone.auth.functions.BaseAuthenticator;
import org.jclouds.rackspace.cloudidentity.v2_0.CloudIdentityAuthenticationApi;
import org.jclouds.rackspace.cloudidentity.v2_0.config.CloudIdentityCredentialTypes;
import org.jclouds.rackspace.cloudidentity.v2_0.domain.ApiKeyCredentials;

/**
 * 
 * @see <a
 *      href="http://docs.rackspace.com/servers/api/v2/cs-devguide/content/curl_auth.html">docs</a>
 */
@CredentialType(CloudIdentityCredentialTypes.API_KEY_CREDENTIALS)
@Singleton
public class AuthenticateApiKeyCredentials extends BaseAuthenticator<ApiKeyCredentials> {
   protected final CloudIdentityAuthenticationApi api;

   @Inject
   AuthenticateApiKeyCredentials(CloudIdentityAuthenticationApi api) {
      this.api = api;
   }

   @Override
   public ApiKeyCredentials createCredentials(String identity, String credential) {
      return ApiKeyCredentials.createWithUsernameAndApiKey(identity, credential);
   }

   @Override
   public String toString() {
      return "authenticateApiKeyCredentials()";
   }

   @Override
   public AuthInfo authenticate(TenantAndCredentials<ApiKeyCredentials> credentials) {
      return api.authenticateApiKey(credentials);
   }
}
