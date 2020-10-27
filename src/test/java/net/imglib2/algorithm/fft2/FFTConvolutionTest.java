/*-
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2020 Tobias Pietzsch, Stephan Preibisch, Stephan Saalfeld,
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
package net.imglib2.algorithm.fft2;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.Test;

import net.imglib2.Cursor;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.type.numeric.real.FloatType;

public class FFTConvolutionTest {

	
	@Test
	public void test_1d() throws IncompatibleTypeException {
		
		final int n_image = 33;
		final int n_kernel = 32;
		
		float[] arr_image = new float[n_image];
		arr_image[n_image/2] = 1;
		
		float[] arr_kernel = new float[n_kernel];
		Random r = new Random();
		for (int i = 0; i < arr_kernel.length; i++) {
			arr_kernel[i] = r.nextFloat();	
		}
		
		final Img< FloatType > image = ArrayImgs.floats(arr_image, n_image);
		final Img< FloatType > kernel = ArrayImgs.floats(arr_kernel, n_kernel);
		final Img< FloatType > result = ArrayImgs.floats(new float[n_image], n_image);
		
		final FFTConvolution< FloatType > conv = new FFTConvolution<FloatType>( image, kernel, result );
		conv.setComputeComplexConjugate(false);
		
		conv.convolve();
		
		assertImagesEqual(kernel,result, 0.0001f);
		
	}
	protected void assertImagesEqual(Img<FloatType> img1, Img<FloatType> img2,
			float delta)
		{
			Cursor<FloatType> c1 = img1.cursor();
			Cursor<FloatType> c2 = img2.cursor();
			while (c1.hasNext()) {
				c1.fwd();
				c2.fwd();
				
				assertEquals(c1.get().getRealFloat(), c2.get().getRealFloat(), delta);
			}

		}
}
