/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2023 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
 * John Bogovic, Albert Cardona, Barry DeZonia, Christian Dietz, Jan Funke,
 * Aivar Grislis, Jonathan Hale, Grant Harris, Stefan Helfrich, Mark Hiner,
 * Martin Horn, Steffen Jaensch, Lee Kamentsky, Larry Lindsey, Melissa Linkert,
 * Mark Longair, Brian Northan, Nick Perry, Curtis Rueden, Johannes Schindelin,
 * Jean-Yves Tinevez and Michael Zinsmaier.
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package net.imglib2.algorithm.pde;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class PdeUtilTest {

	@Test
	public void test() {
		double[] results;
		results = PdeUtil.realSymetricMatrix2x2(2, Float.MIN_VALUE, 0.1);
		assertEquals(1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, Float.MIN_VALUE, -0.1);
		assertEquals(-1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, -Float.MIN_VALUE, 0.1);
		assertEquals(1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, Float.MIN_VALUE, 0.1);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, -Float.MIN_VALUE, -0.1);
		assertEquals(-1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, Float.MIN_VALUE, -0.1);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, -Float.MIN_VALUE, 0.1);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, -Float.MIN_VALUE, -0.1);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, Float.MIN_VALUE, 0.9);
		assertEquals(1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, Float.MIN_VALUE, -0.9);
		assertEquals(-1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, -Float.MIN_VALUE, 0.9);
		assertEquals(1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, Float.MIN_VALUE, 0.9);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(2, -Float.MIN_VALUE, -0.9);
		assertEquals(-1, results[2], 0);
		assertEquals(0, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, Float.MIN_VALUE, -0.9);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, -Float.MIN_VALUE, 0.9);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
		results = PdeUtil.realSymetricMatrix2x2(-2, -Float.MIN_VALUE, -0.9);
		assertEquals(0, results[2], 0);
		assertEquals(1, results[3], 0);
	}
}
