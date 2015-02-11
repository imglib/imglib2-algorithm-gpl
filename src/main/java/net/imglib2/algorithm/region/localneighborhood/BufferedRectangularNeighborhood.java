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

package net.imglib2.algorithm.region.localneighborhood;

import java.util.Iterator;

import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.outofbounds.OutOfBoundsFactory;
import net.imglib2.type.Type;

/**
 * 
 * 
 */
public class BufferedRectangularNeighborhood<T extends Type<T>>
		extends AbstractNeighborhood<T> {

	private int size;

	public BufferedRectangularNeighborhood(RandomAccessibleInterval<T> source,
			OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBounds, long[] spans) {
		this(source.numDimensions(), outOfBounds, spans);
		updateSource(source);
	}

	public BufferedRectangularNeighborhood(int numDims,
			OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBounds, long[] spans) {
		super(numDims, outOfBounds);
		setSpan(spans);

		size = 1;
		for (long s : spans)
			size *= s;
	}

	@Override
	public Cursor<T> cursor() {
		return new BufferedRectangularNeighborhoodCursor<T>(this);
	}

	@Override
	public Cursor<T> localizingCursor() {
		return cursor();
	}

	@Override
	public long size() {
		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return cursor();
	}

	@Override
	public BufferedRectangularNeighborhood<T> copy() {
		if (source != null)
			return new BufferedRectangularNeighborhood<T>(source,
					outOfBounds, span);
		return new BufferedRectangularNeighborhood<T>(n, outOfBounds,
				span);
	}
}
