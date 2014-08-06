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
// Date: Jul 9, 2014
// ---------------------

package com.cleversafe.og.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.cleversafe.og.api.OperationManagerException;
import com.cleversafe.og.api.Request;
import com.cleversafe.og.object.ObjectManagerException;
import com.cleversafe.og.supplier.Suppliers;
import com.cleversafe.og.test.SimpleOperationManager;
import com.google.common.base.Supplier;

public class SimpleOperationManagerTest
{
   private Supplier<Supplier<Request>> requestMix;
   private Request request;

   @Before
   @SuppressWarnings("unchecked")
   public void before()
   {
      this.requestMix = mock(Supplier.class);
      this.request = mock(Request.class);
      when(this.requestMix.get()).thenReturn(Suppliers.of(this.request));
   }

   @Test(expected = NullPointerException.class)
   public void testNullRequestMix()
   {
      new SimpleOperationManager(null);
   }

   @Test
   public void testNext() throws OperationManagerException
   {
      final SimpleOperationManager m = new SimpleOperationManager(this.requestMix);
      final Request r = m.next();
      Assert.assertEquals(r, this.request);
   }

   @Test(expected = OperationManagerException.class)
   public void testGetOperationManagerException() throws OperationManagerException
   {
      when(this.requestMix.get()).thenThrow(new IllegalStateException());
      final SimpleOperationManager m = new SimpleOperationManager(this.requestMix);
      m.next();
   }

   @Test(expected = OperationManagerException.class)
   public void testGetOperationManagerException2() throws OperationManagerException
   {
      @SuppressWarnings("unchecked")
      final Supplier<Request> p = mock(Supplier.class);
      when(p.get()).thenThrow(new ObjectManagerException());
      when(this.requestMix.get()).thenReturn(p);
      final SimpleOperationManager m = new SimpleOperationManager(this.requestMix);
      m.next();
   }
}