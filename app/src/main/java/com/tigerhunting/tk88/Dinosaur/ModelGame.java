package com.tigerhunting.tk88.Dinosaur;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NoDataException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

public class ModelGame implements elemnet {

    private RealMatrix r1,r2;

    public ModelGame(final double[][] measMatrix, final double[][] measNoise)
            throws NullArgumentException, NoDataException, DimensionMismatchException {
        this(new Array2DRowRealMatrix(measMatrix), new Array2DRowRealMatrix(measNoise));
    }

    public ModelGame(final RealMatrix measMatrix, final RealMatrix measNoise) {
        this.r1 = measMatrix;
        this.r2 = measNoise;
    }
    public RealMatrix getMatric1() {
        return r1;
    }
    public RealMatrix getMatric2() {
        return r2;
    }
}