package Server.Integration;

public class ImageProcessRequest {


    private double[] signal;
    private String matrixModel;
    private String imageDimensions;
    private String algorithm;
    private String user;
    private boolean gain;
    private double gainValue = 0;

    public ImageProcessRequest(){}

    public double[] getSignal() {
        return signal;
    }

    public String getMatrixModel() {
        return matrixModel;
    }
    public String getDimensions(){
        return imageDimensions;
    }

    public String getAlgorithm(){
        return algorithm;
    }

    public String getUser() {
        return user;
    }

    public boolean isGain() {
        return gain;
    }

    public double getGainValue() {
        return gainValue;
    }
}
