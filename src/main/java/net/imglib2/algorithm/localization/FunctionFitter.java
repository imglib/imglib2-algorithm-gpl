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
package net.imglib2.algorithm.localization;

public interface FunctionFitter {
	
	/**
	 * Minimizes <code>E = sum {(y[k] - f(x[k],a)) }^2</code>.
	 * Note that function implements the value and gradient of f(x,a),
	 * NOT the value and gradient of E with respect to a!
	 * 
	 * @param x array of domain points, each may be multidimensional. 
	 * (For instance, for 2D data, provides a double array of N double arrays 
	 * of 2 elements: x &amp; y.)
	 * @param y corresponding array of values.
	 * @param a the parameters/state of the model. Is updated by the call. 
	 * @param f  the function to fit on the domain points.
	 * @throws Exception  if the fitting process meets a numerical problem.
	 */
	public void fit(double[][] x, double[] y, double[] a, FitFunction f) throws Exception;

}
