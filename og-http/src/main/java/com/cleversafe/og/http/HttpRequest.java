//
// Copyright (C) 2005-2011 Cleversafe, Inc. All rights reserved.
//
// Contact Information:
// Cleversafe, Inc.
// 222 South Riverside Plaza
// Suite 1700
// Chicago, IL 60606, USA
//
// licensing@cleversafe.com
//
// END-OF-HEADER
//
// -----------------------
// @author: rveitch
//
// Date: Mar 11, 2014
// ---------------------

package com.cleversafe.og.http;

import static com.google.common.base.Preconditions.checkNotNull;

import java.net.URI;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.cleversafe.og.operation.Entity;
import com.cleversafe.og.operation.Metadata;
import com.cleversafe.og.operation.Method;
import com.cleversafe.og.operation.Request;
import com.cleversafe.og.util.Entities;
import com.google.common.collect.ImmutableMap;

/**
 * A defacto implementation of the {@code Request} interface
 * 
 * @since 1.0
 */
public class HttpRequest implements Request
{
   private final Method method;
   private final URI uri;
   private final Map<String, String> headers;
   private final Entity entity;
   private final Map<String, String> metadata;
   private static final DateTimeFormatter RFC1123 =
         DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss zzz").withLocale(Locale.US);

   private HttpRequest(final Builder builder)
   {
      this.method = checkNotNull(builder.method);
      this.uri = checkNotNull(builder.uri);
      this.headers = ImmutableMap.copyOf(builder.headers);
      this.entity = checkNotNull(builder.entity);
      this.metadata = ImmutableMap.copyOf(builder.metadata);
   }

   @Override
   public Method getMethod()
   {
      return this.method;
   }

   @Override
   public URI getUri()
   {
      return this.uri;
   }

   @Override
   public String getHeader(final String key)
   {
      return this.headers.get(key);
   }

   @Override
   public Iterator<Entry<String, String>> headers()
   {
      return this.headers.entrySet().iterator();
   }

   @Override
   public Entity getEntity()
   {
      return this.entity;
   }

   @Override
   public String getMetadata(final Metadata key)
   {
      return this.metadata.get(key.toString());
   }

   @Override
   public String getMetadata(final String key)
   {
      return this.metadata.get(key);
   }

   @Override
   public Iterator<Entry<String, String>> metadata()
   {
      return this.metadata.entrySet().iterator();
   }

   /**
    * An http request builder
    */
   public static class Builder
   {
      private final Method method;
      private final URI uri;
      private final Map<String, String> headers;
      private Entity entity;
      private final Map<String, String> metadata;

      /**
       * Constructs a builder
       * <p>
       * Note: this builder automatically includes a {@code Date} header with an rfc1123 formatted
       * datetime set to the time of builder construction
       * 
       * @param method
       *           the request method for this request
       * @param uri
       *           the uri for this request
       */
      public Builder(final Method method, final URI uri)
      {
         this.method = method;
         this.uri = uri;
         this.headers = new LinkedHashMap<String, String>();
         this.headers.put("Date", RFC1123.print(new DateTime()));
         this.entity = Entities.none();
         this.metadata = new LinkedHashMap<String, String>();
      }

      /**
       * Configures a request header to include with this request
       * 
       * @param key
       *           a header key
       * @param value
       *           a header value
       * @return this builder
       */
      public Builder withHeader(final String key, final String value)
      {
         this.headers.put(key, value);
         return this;
      }

      /**
       * Configures a request entity to include with this request
       * 
       * @param entity
       *           an entity
       * @return this builder
       */
      public Builder withEntity(final Entity entity)
      {
         this.entity = entity;
         return this;
      }

      /**
       * Configures an additional piece of metadata to include with this request, using a
       * {@code Metadata} entry as the key
       * 
       * @param key
       *           a metadata key
       * @param value
       *           a metadata value
       * @return this builder
       */
      public Builder withMetadata(final Metadata key, final String value)
      {
         return withMetadata(key.toString(), value);
      }

      /**
       * Configures an additional piece of metadata to include with this request
       * 
       * @param key
       *           a metadata key
       * @param value
       *           a metadata value
       * @return this builder
       */
      public Builder withMetadata(final String key, final String value)
      {
         this.metadata.put(key, value);
         return this;
      }

      /**
       * Constructs an http request instance
       * 
       * @return an http request instance
       * @throws NullPointerException
       *            if any null header or metadata keys or values were added to this builder
       */
      public HttpRequest build()
      {
         return new HttpRequest(this);
      }
   }
}
