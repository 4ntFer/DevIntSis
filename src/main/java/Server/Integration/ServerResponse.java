package Server.Integration;

public class ServerResponse {

    //Head
    private String user;
    private String algorithm;
    private String pixels;
    private int iteractions;
    private String starts;
    private String ends;
    private double averageCpuUsage;
    private double averageMemoryUsage;

    //Image
    private byte[] Image;

    public ServerResponse(
            String user,
            String algorithm,
            String pixels,
            int iteractions,
            String starts,
            String ends,
            double averageCpuUsage,
            double averageMemoryUsage,
            byte[] image
    ) {


        this.user = user;
        this.algorithm = algorithm;
        this.pixels = pixels;
        this.iteractions = iteractions;
        this.starts = starts;
        this.ends = ends;
        this.averageCpuUsage = averageCpuUsage;
        this.averageMemoryUsage = averageMemoryUsage;
        Image = image;
    }

    public ServerResponse(){};


    public void setUser(String user) {
        this.user = user;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public void setPixels(String pixels) {
        this.pixels = pixels;
    }

    public void setIteractions(int iteractions) {
        this.iteractions = iteractions;
    }

    public void setStarts(String starts) {
        this.starts = starts;
    }

    public void setEnds(String ends) {
        this.ends = ends;
    }

    public void setAverageCpuUsage(double averageCpuUsage) {
        this.averageCpuUsage = averageCpuUsage;
    }

    public void setAverageMemoryUsage(double averageMemoryUsage) {
        this.averageMemoryUsage = averageMemoryUsage;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getUser() {
        return user;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public String getPixels() {
        return pixels;
    }

    public int getIteractions() {
        return iteractions;
    }

    public String getStarts() {
        return starts;
    }

    public String getEnds() {
        return ends;
    }

    public double getAverageCpuUsage() {
        return averageCpuUsage;
    }

    public double getAverageMemoryUsage() {
        return averageMemoryUsage;
    }

    public byte[] getImage() {
        return Image;
    }
}
