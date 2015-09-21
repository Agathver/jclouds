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
package org.jclouds.cloudfiles.binders;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.inject.Singleton;

import org.jclouds.cloudfiles.reference.CloudFilesHeaders;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.Binder;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMultimap;

@Singleton
public class BindIterableToHeadersWithPurgeCDNObjectEmail implements Binder {
   @SuppressWarnings("unchecked")
   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      checkArgument(checkNotNull(input, "input") instanceof Iterable<?>, "this binder is only valid for Iterable!");
      checkNotNull(request, "request");

      Iterable<String> emails = (Iterable<String>) input;
      String emailCSV = Joiner.on(", ").join((List<String>) emails);
      ImmutableMultimap<String, String> headers = 
    		  ImmutableMultimap.<String, String> of(CloudFilesHeaders.CDN_CONTAINER_PURGE_OBJECT_EMAIL, emailCSV);
      
      return (R) request.toBuilder().replaceHeaders(headers).build();
   }
}