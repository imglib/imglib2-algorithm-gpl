/*
 * #%L
 * ImgLib2: a general-purpose, multidimensional image processing library.
 * %%
 * Copyright (C) 2009 - 2015 Tobias Pietzsch, Stephan Preibisch, Barry DeZonia,
 * Stephan Saalfeld, Curtis Rueden, Albert Cardona, Christian Dietz, Jean-Yves
 * Tinevez, Johannes Schindelin, Jonathan Hale, Lee Kamentsky, Larry Lindsey, Mark
 * Hiner, Michael Zinsmaier, Martin Horn, Grant Harris, Aivar Grislis, John
 * Bogovic, Steffen Jaensch, Stefan Helfrich, Jan Funke, Nick Perry, Mark Longair,
 * Melissa Linkert and Dimiter Prodanov.
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

package net.imglib2.algorithm.fft;

import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.exception.IncompatibleTypeException;
import net.imglib2.img.Img;
import net.imglib2.img.ImgFactory;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.complex.ComplexFloatType;
import net.imglib2.view.Views;

/**
 * Convolve an image with the inverse of a kernel which is division in the
 * Fourier domain. This is the simple, unnormalized version of what is used in
 * the {@link PhaseCorrelation}.
 * 
 * 
 * @author Stephan Saalfeld <saalfeld@mpi-cbg.de>
 * @deprecated use {@link net.imglib2.algorithm.fft2.FFT} instead
 */
@Deprecated
public class InverseFourierConvolution< T extends RealType< T >, S extends RealType< S > > extends FourierConvolution< T, S >
{
	public InverseFourierConvolution( final RandomAccessibleInterval< T > image, final RandomAccessibleInterval< S > kernel, final ImgFactory< T > imgFactory, final ImgFactory< S > kernelImgFactory, final ImgFactory< ComplexFloatType > fftImgFactory )
	{
		super( image, kernel, imgFactory, kernelImgFactory, fftImgFactory );
	}

	public InverseFourierConvolution( final Img< T > image, final Img< S > kernel, final ImgFactory< ComplexFloatType > fftImgFactory )
	{
		super( image, kernel, fftImgFactory );
	}

	public InverseFourierConvolution( final Img< T > image, final Img< S > kernel ) throws IncompatibleTypeException
	{
		super( image, kernel );
	}

	/**
	 * Divide in Fourier Space
	 * 
	 * @param a
	 * @param b
	 */
	@Override
	protected void multiply( final RandomAccessibleInterval< ComplexFloatType > a, final RandomAccessibleInterval< ComplexFloatType > b )
	{
		final Cursor< ComplexFloatType > cursorA = Views.iterable( a ).cursor();
		final Cursor< ComplexFloatType > cursorB = Views.iterable( b ).cursor();

		while ( cursorA.hasNext() )
		{
			cursorA.fwd();
			cursorB.fwd();

			cursorA.get().div( cursorB.get() );
		}
	}
}
