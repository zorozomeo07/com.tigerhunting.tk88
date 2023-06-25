package com.tigerhunting.tk88.Dinosaur;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public class Molel implements elemnet2 {

    private RealMatrix r1, r2, r3, r4;
    private RealVector rv1;
    public Molel(final double[][] stateTransition,
                 final double[][] control,
                 final double[][] processNoise,
                 final double[] initialStateEstimate,
                 final double[][] initialErrorCovariance)
            throws NullArgumentException, NoDataException, DimensionMismatchException {

        this(new Array2DRowRealMatrix(stateTransition),
                new Array2DRowRealMatrix(control),
                new Array2DRowRealMatrix(processNoise),
                new ArrayRealVector(initialStateEstimate),
                new Array2DRowRealMatrix(initialErrorCovariance));
    }


    public Molel(final double[][] stateTransition,
                 final double[][] control,
                 final double[][] processNoise)
            throws NullArgumentException, NoDataException, DimensionMismatchException {

        this(new Array2DRowRealMatrix(stateTransition),
                new Array2DRowRealMatrix(control),
                new Array2DRowRealMatrix(processNoise), null, null);
    }


    public Molel(final RealMatrix stateTransition,
                 final RealMatrix control,
                 final RealMatrix processNoise,
                 final RealVector initialStateEstimate,
                 final RealMatrix initialErrorCovariance) {
        this.r1 = stateTransition;
        this.r3 = control;
        this.r2 = processNoise;
        this.rv1 = initialStateEstimate;
        this.r4 = initialErrorCovariance;
    }

    public RealMatrix ma_elenment3() {
        return r1;
    }

    public RealMatrix getMatric5() {
        return r3;
    }

    public RealMatrix elemnet1() {
        return r2;
    }

    public RealVector r_elemnet1() {
        return rv1;
    }

    public RealMatrix getMatric4() {
        return r4;
    }
}