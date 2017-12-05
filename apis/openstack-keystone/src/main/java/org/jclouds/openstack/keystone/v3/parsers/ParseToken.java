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
package org.jclouds.openstack.keystone.v3.parsers;

import org.jclouds.http.HttpResponse;
import org.jclouds.http.functions.ParseFirstJsonValueNamed;
import org.jclouds.json.internal.GsonWrapper;
import org.jclouds.openstack.keystone.v3.domain.Token;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.TypeLiteral;

public class ParseToken extends ParseFirstJsonValueNamed<Token> {

   @Inject
   ParseToken(GsonWrapper json, TypeLiteral<Token> type, String... nameChoices) {
      super(json, type, nameChoices);
   }

   @Override
   public Token apply(HttpResponse from) {
      Token token = super.apply(from);
      String xSubjectToken = Iterables.getOnlyElement(from.getHeaders().get("X-Subject-Token"));
      return token.toBuilder().id(xSubjectToken).build();
   }
}
