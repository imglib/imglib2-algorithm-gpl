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
