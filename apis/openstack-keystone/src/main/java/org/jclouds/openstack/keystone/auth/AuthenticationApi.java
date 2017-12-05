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
package org.jclouds.openstack.keystone.auth;

import org.jclouds.openstack.keystone.auth.domain.ApiAccessKeyCredentials;
import org.jclouds.openstack.keystone.auth.domain.AuthInfo;
import org.jclouds.openstack.keystone.auth.domain.TenantAndCredentials;
import org.jclouds.openstack.keystone.auth.domain.PasswordCredentials;
import org.jclouds.openstack.keystone.auth.domain.TokenCredentials;

/**
 * Authentication methods to be implemented to all Keystone authentication APIs.
 */
public interface AuthenticationApi {

   AuthInfo authenticatePassword(TenantAndCredentials<PasswordCredentials> credentials);

   AuthInfo authenticateAccessKey(TenantAndCredentials<ApiAccessKeyCredentials> credentials);

   AuthInfo authenticateToken(TenantAndCredentials<TokenCredentials> credentials);
}
