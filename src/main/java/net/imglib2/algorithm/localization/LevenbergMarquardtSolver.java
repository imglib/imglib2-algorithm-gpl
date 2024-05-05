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

package net.imglib2.algorithm.localization;

import Jama.Matrix;

/**
 * A plain implementation of Levenberg-Marquardt least-squares curve fitting algorithm.
 * This solver makes use of only the function value and its gradient. That is:
 * candidate functions need only to implement the {@link FitFunction#val(double[], double[])}
 * and {@link FitFunction#grad(double[], double[], int)} methods to operate with this
 * solver.
 * <p>
 * It was adapted and stripped from jplewis (www.idiom.com/~zilla) and released under 
 * the GPL. There are various small tweaks for robustness and speed.
 *
 * @author Jean-Yves Tinevez 2011 - 2013
 */
public class LevenbergMarquardtSolver implements FunctionFitter {
	
	private final int maxIteration;
	private final double lambda;
	private final double termEpsilon;
	
	/**
	 * Creates a new Levenberg-Marquardt solver for least-squares curve fitting problems.
	 * @param lambda blend between steepest descent (lambda high) and
	 *	jump to bottom of quadratic (lambda zero). Start with 0.001.
	 * @param termEpsilon termination accuracy (0.01)
	 * @param maxIteration stop and return after this many iterations if not done
	 */
	public LevenbergMarquardtSolver(int maxIteration, double lambda, double termEpsilon) {
		this.maxIteration = maxIteration;
		this.lambda = lambda;
		this.termEpsilon = termEpsilon;
	}
	
	/*
	 * METHODS
	 */
	
	@Override
	public String toString() {
		return "Levenberg-Marquardt least-squares curve fitting algorithm";
	}
	
	/**
	 * Creates a new Levenberg-Marquardt solver for least-squares curve fitting problems,
	 * with default parameters set to:
	 * <ul>
	 * 	<li> <code>lambda  = 1e-3</code>
	 * 	<li> <code>epsilon = 1e-1</code>
	 * 	<li> <code>maxIter = 300</code>
	 * </ul>
	 */
	public LevenbergMarquardtSolver() {
		this(300, 1e-3d, 1e-1d);
	}
	
	/*
	 * MEETHODS
	 */
	
	
	@Override
	public void fit(double[][] x, double[] y, double[] a, FitFunction f) {
		solve(x, a, y, f, lambda, termEpsilon, maxIteration);
	}
	
	
	
	/*
	 * STATIC METHODS
	 */
	
	/**
	 * Calculate the current sum-squared-error
	 * This is deprecated in favor of {@link LevenbergMarquardtSolver#computeSquaredError(double[][], double[], double[], FitFunction)}.
	 */
	@Deprecated
	public static double chiSquared(final double[][] x, final double[] a, final double[] y, final FitFunction f) {
		return computeSquaredError(x, y, a, f);
	}

	/**
	 * Calculate the squared least-squares error of the given data.
	 */
	public static double computeSquaredError(final double[][] x, final double[] y, final double[] a, final FitFunction f) {
		int npts = y.length;
		double sum = 0.;

		for( int i = 0; i < npts; i++ ) {
			double d = y[i] - f.val(x[i], a);
			sum += d * d;
		}

		return sum;
	}

	/**
	 * Minimize E = sum {(y[k] - f(x[k],a)) }^2
	 * Note that function implements the value and gradient of f(x,a),
	 * NOT the value and gradient of E with respect to a!
	 * This is deprecated, use {@link LevenbergMarquardtSolver@fit(double[][], double[], double[], FitFunction, int, double, double)} instead.
	 * 
	 * @param x array of domain points, each may be multidimensional
	 * @param a the parameters/state of the model
	 * @param y corresponding array of values
	 * @param lambda blend between steepest descent (lambda high) and
	 *	jump to bottom of quadratic (lambda zero). Start with 0.001.
	 * @param termepsilon termination accuracy (0.01)
	 * @param maxiter	stop and return after this many iterations if not done
	 *
	 * @return the number of iteration used by minimization
	 */
	@Deprecated
	public static int solve(double[][] x, double[] a, double[] y, FitFunction f,
							double lambda, double termepsilon, int maxiter) {
		return fit(x, y, a, f, maxiter, lambda, termepsilon);
	}

	/**
	 * Minimize E = sum {(y[k] - f(x[k],a)) }^2
	 * Note that function implements the value and gradient of f(x,a),
	 * NOT the value and gradient of E with respect to a!
	 *
	 * @param x array of domain points, each may be multidimensional
	 * @param y corresponding array of values
	 * @param a the parameters/state of the model
	 * @param maxiter	stop and return after this many iterations if not done
	 * @param lambda blend between steepest descent (lambda high) and
	 *	jump to bottom of quadratic (lambda zero). Start with 0.001.
	 * @param termepsilon termination accuracy (0.01)
	 *
	 * @return the number of iteration used by minimization
	 */
	public static int fit(double[][] x, double[] y, double[] a, FitFunction f, int maxiter, double lambda, double termepsilon) {
		int npts = y.length;
		int nparm = a.length;
	
		double e0 = chiSquared(x, a, y, f);
		boolean done = false;

		// g = gradient, H = hessian, d = step to minimum
		// H d = -g, solve for d
		double[][] H = new double[nparm][nparm];
		double[] g = new double[nparm];

		double[] valf = new double[npts];
		double[][] gradf = new double[nparm][npts];

		int iter = 0;
		int term = 0;	// termination count test

		do {
			++iter;

			// precompute values and gradients of f
			for (int i = 0; i < npts; i++) {
				valf[i] = f.val(x[i], a);
				for (int k = 0; k < nparm; k++) {
					gradf[k][i] = f.grad(x[i], a, k);
				}
			}

			// hessian approximation
			for( int r = 0; r < nparm; r++ ) {
				for( int c = 0; c < nparm; c++ ) {
					H[r][c] = 0.;
					for( int i = 0; i < npts; i++ ) {
						H[r][c] += gradf[r][i] * gradf[c][i];
					}  //npts
				} //c
			} //r

			// boost diagonal towards gradient descent
			for( int r = 0; r < nparm; r++ )
				H[r][r] *= (1. + lambda);

			// gradient
			for( int r = 0; r < nparm; r++ ) {
				g[r] = 0.;
				for( int i = 0; i < npts; i++ ) {
					g[r] += (y[i]-valf[i]) * gradf[r][i];
				}
			} //npts
			
			double[] d;
            try {
                    d = (new Matrix(H)).lu().solve(new Matrix(g, nparm)).getRowPackedCopy();
            } catch (RuntimeException re) {
                    // Matrix is singular
                    lambda *= 10.;
                    continue;
            }
            double[] na = (new Matrix(a, nparm)).plus(new Matrix(d,nparm)).getRowPackedCopy();
            double e1 = chiSquared(x, na, y, f);
			
			// termination test (slightly different than NR)
			if (Math.abs(e1-e0) > termepsilon) {
				term = 0;
			}
			else {
				term++;
				if (term == 4) {
					done = true;
				}
			}
			if (iter >= maxiter) done = true;

			// in the C++ version, found that changing this to e1 >= e0
			// was not a good idea.  See comment there.
			//
			if (e1 > e0 || Double.isNaN(e1)) { // new location worse than before
				lambda *= 10.;
			}
			else {		// new location better, accept new parameters
				lambda *= 0.1;
				e0 = e1;
				// simply assigning a = na will not get results copied back to caller
				for( int i = 0; i < nparm; i++ ) {
					a[i] = na[i];
				}
			}

		} while(!done);

		return iter;
	} //solve

	
}
