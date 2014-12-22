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
// Date: Oct 23, 2013
// ---------------------

package com.cleversafe.og.util.distribution;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Random;

/**
 * A distribution implementation that returns values conforming to a normal distribution.
 * 
 * @since 1.0
 */
public class NormalDistribution extends AbstractDistribution {
  /**
   * Constructs a normal distribution instance, using the provided random instance for random seed
   * data
   * 
   * @param average the average value of this distribution
   * @param spread the spread of this distribution. Spread is defined as the first standard
   *        deviation.
   * @throws IllegalArgumentException if average is negative
   * @throws IllegalArgumentException if spread is negative
   */
  public NormalDistribution(final double average, final double spread, final Random random) {
    super(average, spread, random);
    final double min = average - (3 * spread);
    checkArgument(min >= 0.0, "three standard deviations must be >= 0.0 [%s]", min);
  }

  /**
   * {@inheritDoc}
   * 
   * This implementation will always return a non-negative sample.
   */
  @Override
  public double nextSample() {
    double result;
    do {
      result = this.average + (this.spread * this.random.nextGaussian());
    } while (result < 0.0);
    return result;
  }

  @Override
  public String toString() {
    return "NormalDistribution [average=" + this.average + ", spread=" + this.spread + "]";
  }
}
