package com.tigerhunting.tk88.Dinosaur;

import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.CholeskyDecomposition;
import org.apache.commons.math3.linear.MatrixDimensionMismatchException;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.NonSquareMatrixException;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.SingularMatrixException;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.MathUtils;

public class Matric_Game {

    double dH = 0;
    double dA = 0;

    private final elemnet2 elemnet2;

    private final elemnet elemnet;

    private RealMatrix matric1, matric2, matric3, matric4, matric5, matric6;

    private RealVector rvone;



    public Matric_Game(final elemnet2 process, final elemnet measurement)
            throws NullArgumentException, NonSquareMatrixException, DimensionMismatchException,
            MatrixDimensionMismatchException {

        MathUtils.checkNotNull(process);
        MathUtils.checkNotNull(measurement);

        this.elemnet2 = process;
        this.elemnet = measurement;

        matric1 = elemnet2.ma_elenment3();
        MathUtils.checkNotNull(matric1);
        matric2 = matric1.transpose();

        // create an empty matrix if no control matrix was given
        if (elemnet2.getMatric5() == null) {
            matric3 = new Array2DRowRealMatrix();
        } else {
            matric3 = elemnet2.getMatric5();
        }

        matric4 = elemnet.getMatric1();
        MathUtils.checkNotNull(matric4);
        matric5 = matric4.transpose();


        RealMatrix processNoise = elemnet2.elemnet1();
        MathUtils.checkNotNull(processNoise);
        RealMatrix measNoise = elemnet.getMatric2();
        MathUtils.checkNotNull(measNoise);


        if (elemnet2.r_elemnet1() == null) {
            rvone = new ArrayRealVector(matric1.getColumnDimension());
        } else {
            rvone = elemnet2.r_elemnet1();
        }

        if (matric1.getColumnDimension() != rvone.getDimension()) {
            throw new DimensionMismatchException(matric1.getColumnDimension(),
                    rvone.getDimension());
        }


        if (elemnet2.getMatric4() == null) {
            matric6 = processNoise.copy();
        } else {
            matric6 = elemnet2.getMatric4();
        }


        if (!matric1.isSquare()) {
            throw new NonSquareMatrixException(
                    matric1.getRowDimension(),
                    matric1.getColumnDimension());
        }


        if (matric3 != null &&
                matric3.getRowDimension() > 0 &&
                matric3.getColumnDimension() > 0 &&
                matric3.getRowDimension() != matric1.getRowDimension()) {
            throw new MatrixDimensionMismatchException(matric3.getRowDimension(),
                    matric3.getColumnDimension(),
                    matric1.getRowDimension(),
                    matric3.getColumnDimension());
        }


        MatrixUtils.checkAdditionCompatible(matric1, processNoise);


        if (matric4.getColumnDimension() != matric1.getRowDimension()) {
            throw new MatrixDimensionMismatchException(matric4.getRowDimension(),
                    matric4.getColumnDimension(),
                    matric4.getRowDimension(),
                    matric1.getRowDimension());
        }


        if (measNoise.getRowDimension() != matric4.getRowDimension()) {
            throw new MatrixDimensionMismatchException(measNoise.getRowDimension(),
                    measNoise.getColumnDimension(),
                    matric4.getRowDimension(),
                    measNoise.getColumnDimension());
        }
    }


    public int getStateDimension() {
        return rvone.getDimension();
    }


    public int getMeasurementDimension() {
        return matric4.getRowDimension();
    }


    public double[] getRvone() {
        return rvone.toArray();
    }

    public RealVector getStateEstimationVector() {
        return rvone.copy();
    }


    public double[][] getMatric6() {
        return matric6.getData();
    }

    public RealMatrix getErrorCovarianceMatrix() {
        return matric6.copy();
    }

    public void predict() {
        predict((RealVector) null);
    }


    public void predict(final double[] u) throws DimensionMismatchException {
        predict(new ArrayRealVector(u, false));
    }

    public void predict(final RealVector u) throws DimensionMismatchException {

        if (u != null &&
                u.getDimension() != matric3.getColumnDimension()) {
            throw new DimensionMismatchException(u.getDimension(),
                    matric3.getColumnDimension());
        }

        rvone = matric1.operate(rvone);

        if (u != null) {
            rvone = rvone.add(matric3.operate(u));
        }

        dA = u.getEntry(0);


        matric6 = matric1.multiply(matric6)
                .multiply(matric2)
                .add(elemnet2.elemnet1());
    }

    public void correct(final double[] z)
            throws NullArgumentException, DimensionMismatchException, SingularMatrixException {
        correct(new ArrayRealVector(z, false));
    }


    public void correct(final RealVector z)
            throws NullArgumentException, DimensionMismatchException, SingularMatrixException {
         MathUtils.checkNotNull(z);
        if (z.getDimension() != matric4.getRowDimension()) {
            throw new DimensionMismatchException(z.getDimension(),
                    matric4.getRowDimension());
        }

        RealMatrix s = matric4.multiply(matric6)
                .multiply(matric5)
                .add(elemnet.getMatric2());

            RealVector innovation = z.subtract(matric4.operate(rvone));

        RealMatrix kalmanGain = new CholeskyDecomposition(s).getSolver()
                .solve(matric4.multiply(matric6.transpose()))
                .transpose();

        rvone = rvone.add(kalmanGain.operate(innovation));


        if (rvone.getEntry(0)-dH < 0.25 && rvone.getEntry(0)-dH > -0.25 ){
            rvone.setEntry(0,dH);

        }
        if (dA < 0.25 && dA >-0.25){
            rvone.setEntry(1,0);

        }
        dH = rvone.getEntry(0);

        RealMatrix identity = MatrixUtils.createRealIdentityMatrix(kalmanGain.getRowDimension());
        matric6 = identity.subtract(kalmanGain.multiply(matric4)).multiply(matric6);
    }

    public static Matric_Game kalmanInitial(){

        double dt = 0.178d;

        double measurementNoise = 10d;

        double accelNoise = 0.2d;


        RealMatrix A = new Array2DRowRealMatrix(new double[][] { { 1, dt }, { 0, 1 } });

        RealMatrix B = new Array2DRowRealMatrix(
                new double[][] { { FastMath.pow(dt, 2d) / 2d }, { dt } });

        RealMatrix H = new Array2DRowRealMatrix(new double[][] { { 1d, 0d } });
        System.out.println(H.toString());

        RealVector x = new ArrayRealVector(new double[] { 0, 0 });


        RealMatrix tmp = new Array2DRowRealMatrix(
                new double[][] { { FastMath.pow(dt, 4d) / 4d, FastMath.pow(dt, 3d) / 2d },
                        { FastMath.pow(dt, 3d) / 2d, FastMath.pow(dt, 2d) } });
        RealMatrix Q = tmp.scalarMultiply(FastMath.pow(accelNoise, 2));

        RealMatrix P0 = new Array2DRowRealMatrix(new double[][] { { 1, 1 }, { 1, 1 } });

        RealMatrix R = new Array2DRowRealMatrix(
                new double[] { FastMath.pow(measurementNoise, 2) });

        elemnet2 pm = new Molel(A, B, Q, x, P0);
        elemnet mm = new ModelGame(H, R);

        return new Matric_Game(pm,mm);
    }
}