/*
 * Copyright (C) 2005-2015 Cleversafe, Inc. All rights reserved.
 * 
 * Contact Information: Cleversafe, Inc. 222 South Riverside Plaza Suite 1700 Chicago, IL 60606, USA
 * 
 * licensing@cleversafe.com
 */

package com.cleversafe.og.supplier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.cleversafe.og.object.ObjectManager;
import com.cleversafe.og.object.ObjectManagerException;
import com.cleversafe.og.object.ObjectMetadata;

public class ReadObjectNameSupplierTest {
  private ObjectManager objectManager;

  @Before
  public void before() {
    this.objectManager = mock(ObjectManager.class);
  }

  @Test(expected = NullPointerException.class)
  public void nullObjectManager() {
    new ReadObjectNameSupplier(null);
  }

  @Test
  public void readObjectNameSupplier() {
    final String object = "objectName";
    final ObjectMetadata objectName = mock(ObjectMetadata.class);
    when(objectName.getName()).thenReturn(object);
    when(this.objectManager.acquireNameForRead()).thenReturn(objectName);

    assertThat(new ReadObjectNameSupplier(this.objectManager).get(), is(object));
  }

  @Test(expected = ObjectManagerException.class)
  public void supplierException() {
    when(this.objectManager.acquireNameForRead()).thenThrow(new ObjectManagerException());
    new ReadObjectNameSupplier(this.objectManager).get();
  }
}
