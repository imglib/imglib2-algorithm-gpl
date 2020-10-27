/*
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

package net.imglib2.algorithm.region.localneighborhood;

import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.region.hypersphere.HyperSphereCursor;
import net.imglib2.outofbounds.OutOfBoundsFactory;

@Deprecated
public class HyperSphereNeighborhoodGPL<T>
		extends AbstractNeighborhood<T> {

	private long radius;

	/*
	 * CONSTRUCTORS
	 */
	public HyperSphereNeighborhoodGPL(final RandomAccessibleInterval<T> source,
			final OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBounds, final long radius) {
		super(source.numDimensions(), outOfBounds);
		this.radius = radius;
	}

	/*
	 * CONSTRUCTORS
	 */
	public HyperSphereNeighborhoodGPL(final int numDims,
			final OutOfBoundsFactory<T, RandomAccessibleInterval<T>> outOfBounds, final long radius) {
		super(numDims, outOfBounds);
		this.radius = radius;
	}

	@Override
	public HyperSphereCursor<T> cursor() {
		return new HyperSphereCursor<T>(extendedSource, center, radius);
	}

	@Override
	public HyperSphereCursor<T> localizingCursor() {
		return cursor();
	}

	@Override
	public HyperSphereCursor<T> iterator() {
		return cursor();
	}

	@Override
	public long size() {
		return computeSize();
	}

	/**
	 * Compute the number of elements for iteration
	 */
	protected long computeSize() {
		final HyperSphereCursor<T> cursor = new HyperSphereCursor<T>(source,
				this.center, radius);

		// "compute number of pixels"
		long size = 0;
		while (cursor.hasNext()) {
			cursor.fwd();
			++size;
		}

		return size;
	}

	@Override
	public AbstractNeighborhood<T> copy() {
		if (source != null)
			return new HyperSphereNeighborhoodGPL<T>(source, outOfBounds,
					radius);
		return new HyperSphereNeighborhoodGPL<T>(n, outOfBounds, radius);
	}

}
