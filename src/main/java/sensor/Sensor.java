package sensor;

public class Sensor {

    private short ID;
    private int workingRange;
    private int updateFrequency;
    private String OPT;
    private boolean detect;

    public Sensor(short ID, int workingRange, int updateFrequency, String OPT) {
        this.setID(ID);
        this.setWorkingRange(workingRange);
        this.setUpdateFrequency(updateFrequency);
        this.setOPT(OPT);
        this.setDetect(false);
    }

    public short getID() {
        return ID;
    }

    public void setID(short ID) {
        this.ID = ID;
    }

    public int getWorkingRange() {
        return workingRange;
    }

    public void setWorkingRange(int workingRange) {
        this.workingRange = workingRange;
    }

    public int getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(int updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public String getOPT() {
        return OPT;
    }

    public void setOPT(String OPT) {
        this.OPT = OPT;
    }

    @Override
    public String toString() {
        String sb = String.format("%2d", getID()) +
                ":R" +
                String.format("%3d_P", getWorkingRange()) +
                (isDetect() ? "1" : "0") +
                ":" + getOPT();
        return sb;

    }

    public boolean isDetect() {
        return detect;
    }

    public void setDetect(boolean detect) {
        this.detect = detect;
    }
}
