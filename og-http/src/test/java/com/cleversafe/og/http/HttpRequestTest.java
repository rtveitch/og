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
// Date: Jun 30, 2014
// ---------------------

package com.cleversafe.og.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cleversafe.og.operation.Entity;
import com.cleversafe.og.operation.EntityType;
import com.cleversafe.og.operation.Metadata;
import com.cleversafe.og.operation.Method;
import com.cleversafe.og.util.Entities;

public class HttpRequestTest
{
   private Method method;
   private URI uri;

   @Before
   public void setBefore() throws URISyntaxException
   {
      this.method = Method.PUT;
      this.uri = new URI("http://192.168.8.1/container/object");
   }

   @Test(expected = IllegalArgumentException.class)
   public void testNegativeId()
   {
      HttpRequest.custom().withId(-1).withMethod(this.method).withUri(this.uri).build();
   }

   @Test
   public void testZeroId()
   {
      HttpRequest.custom().withId(0).withMethod(this.method).withUri(this.uri).build();
   }

   @Test
   public void testPositiveId()
   {
      HttpRequest.custom().withId(1).withMethod(this.method).withUri(this.uri).build();
   }

   @Test
   public void testNoId()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
   }

   @Test(expected = NullPointerException.class)
   public void testNullMethod()
   {
      HttpRequest.custom().withMethod(null).withUri(this.uri).build();
   }

   @Test(expected = NullPointerException.class)
   public void testNoMethod()
   {
      HttpRequest.custom().withUri(this.uri).build();
   }

   @Test(expected = NullPointerException.class)
   public void testNullUri()
   {
      HttpRequest.custom().withMethod(this.method).withUri(null).build();
   }

   @Test(expected = NullPointerException.class)
   public void testNoUri()
   {
      HttpRequest.custom().withMethod(this.method).build();
   }

   @Test(expected = NullPointerException.class)
   public void testHeaderNullKey()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withHeader(null, "value").build();
   }

   @Test(expected = NullPointerException.class)
   public void testHeaderNullValue()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withHeader("key", null).build();
   }

   @Test(expected = NullPointerException.class)
   public void testNullEntity()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withEntity(null).build();
   }

   @Test(expected = NullPointerException.class)
   public void testMetadataNullKey()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata((Metadata) null,
            "value").build();
   }

   @Test(expected = NullPointerException.class)
   public void testMetadataNullKey2()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata((String) null,
            "value").build();
   }

   @Test(expected = NullPointerException.class)
   public void testMetadataNullValue()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata(Metadata.ABORTED,
            null).build();
   }

   @Test(expected = NullPointerException.class)
   public void testMetadataNullValue2()
   {
      HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata("key", null).build();
   }

   @Test
   public void testDefaultId()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      Assert.assertEquals(0, r.getId());
   }

   @Test
   public void testId()
   {
      final HttpRequest r =
            HttpRequest.custom().withId(5).withMethod(this.method).withUri(this.uri).build();
      Assert.assertEquals(5, r.getId());
   }

   @Test
   public void testMethod()
   {
      final HttpRequest r = HttpRequest.custom().withMethod(Method.HEAD).withUri(this.uri).build();
      Assert.assertEquals(Method.HEAD, r.getMethod());
   }

   @Test
   public void testUri() throws URISyntaxException
   {
      final URI aUri = new URI("https://10.1.1.1/container/object");
      final HttpRequest r = HttpRequest.custom().withMethod(this.method).withUri(aUri).build();
      Assert.assertEquals(aUri, r.getUri());
   }

   @Test
   public void testMissingHeader()
   {
      final HttpRequest r = HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      Assert.assertNull(r.getHeader("key"));
   }

   @Test
   public void testHeader()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).withHeader("key",
                  "value").build();
      Assert.assertEquals("value", r.getHeader("key"));
   }

   @Test
   public void testNoHeaders()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      final Iterator<Entry<String, String>> it = r.headers();
      Assert.assertTrue(it.hasNext());
      // Skip Date header which is automatically added
      it.next();
      Assert.assertFalse(it.hasNext());
   }

   @Test
   public void testHeaders()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).withHeader("key",
                  "value").build();
      final Iterator<Entry<String, String>> it = r.headers();
      Assert.assertTrue(it.hasNext());
      // Skip Date header which is automatically added
      it.next();
      Assert.assertTrue(it.hasNext());
      final Entry<String, String> e = it.next();
      Assert.assertEquals("key", e.getKey());
      Assert.assertEquals("value", e.getValue());
      Assert.assertFalse(it.hasNext());
   }

   @Test
   public void testHeaders2()
   {
      final HttpRequest.Builder b = HttpRequest.custom().withMethod(this.method).withUri(this.uri);
      for (int i = 0; i < 10; i++)
      {
         // (100 - i) exposes sorted vs insertion order
         b.withHeader("key" + (100 - i), "value" + i);
      }
      final HttpRequest r = b.build();
      final Iterator<Entry<String, String>> it = r.headers();
      Assert.assertTrue(it.hasNext());
      // Skip Date header which is automatically added
      it.next();
      for (int i = 0; i < 10; i++)
      {
         Assert.assertTrue(it.hasNext());
         final Entry<String, String> e = it.next();
         Assert.assertEquals("key" + (100 - i), e.getKey());
         Assert.assertEquals("value" + i, e.getValue());
      }
      Assert.assertFalse(it.hasNext());
   }

   @Test
   public void testDefaultEntity()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      Assert.assertEquals(EntityType.NONE, r.getEntity().getType());
      Assert.assertEquals(0, r.getEntity().getSize());
   }

   @Test
   public void testEntity()
   {
      final Entity e = Entities.of(EntityType.ZEROES, 12345);
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).withEntity(e).build();
      Assert.assertEquals(EntityType.ZEROES, r.getEntity().getType());
      Assert.assertEquals(12345, r.getEntity().getSize());
   }

   @Test
   public void testMissingMetadata()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      Assert.assertNull(r.getMetadata(Metadata.ABORTED));
   }

   @Test
   public void testMissingMetadata2()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      Assert.assertNull(r.getMetadata("aborted"));
   }

   @Test
   public void testMetadataEntry()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata(
                  Metadata.ABORTED, "1").build();
      Assert.assertEquals("1", r.getMetadata(Metadata.ABORTED));
   }

   @Test
   public void testMetadataEntry2()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata("key",
                  "value").build();
      Assert.assertEquals("value", r.getMetadata("key"));
   }

   @Test
   public void testMetadata()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).build();
      final Iterator<Entry<String, String>> it = r.metadata();
      Assert.assertFalse(it.hasNext());
   }

   @Test
   public void testMetadata2()
   {
      final HttpRequest r =
            HttpRequest.custom().withMethod(this.method).withUri(this.uri).withMetadata("key",
                  "value").build();
      final Iterator<Entry<String, String>> it = r.metadata();
      Assert.assertTrue(it.hasNext());
      final Entry<String, String> e = it.next();
      Assert.assertEquals("key", e.getKey());
      Assert.assertEquals("value", e.getValue());
      Assert.assertFalse(it.hasNext());
   }

   @Test
   public void testMetadata3()
   {
      final HttpRequest.Builder b = HttpRequest.custom().withMethod(this.method).withUri(this.uri);
      for (int i = 0; i < 10; i++)
      {
         b.withMetadata("key" + i, "value" + i);
      }
      b.withMetadata(Metadata.ABORTED, "1");
      final HttpRequest r = b.build();
      final Iterator<Entry<String, String>> it = r.metadata();
      Assert.assertTrue(it.hasNext());
      for (int i = 0; i < 10; i++)
      {
         final Entry<String, String> e = it.next();
         Assert.assertEquals("key" + i, e.getKey());
         Assert.assertEquals("value" + i, e.getValue());
      }
      Assert.assertTrue(it.hasNext());
      final Entry<String, String> e = it.next();
      Assert.assertEquals("ABORTED", e.getKey());
      Assert.assertEquals("1", e.getValue());
      Assert.assertFalse(it.hasNext());
   }
}