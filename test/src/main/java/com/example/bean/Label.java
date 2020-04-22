package com.example.bean;

public class Label {
    private int mAntennaPort; //天线端口
    private int mRSSI;
    private String mEPC;
    private String mTID;
    private String mPC; //PC（控制盘点时能获取的EPC数据长度）
    private int mInventoryCount; //盘点次数
    private int mOptimalRSSI; //RSSI最优值
    private int mWorstRSSI; //RSSI最差值

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj != null && obj.getClass() == Label.class) {
            Label r = (Label) obj;
            return this.mEPC.equalsIgnoreCase(r.mEPC);
        }
        return false;
    }

    public int getAntennaPort() {
        return mAntennaPort;
    }

    public void setAntennaPort(int antennaPort) {
        mAntennaPort = antennaPort;
    }

    public int getRSSI() {
        return mRSSI;
    }

    public void setRSSI(int RSSI) {
        mRSSI = RSSI;
    }

    public String getEPC() {
        return mEPC;
    }

    public void setEPC(String EPC) {
        mEPC = EPC;
    }

    public String getTID() {
        return mTID;
    }

    public void setTID(String TID) {
        mTID = TID;
    }

    public String getPC() {
        return mPC;
    }

    public void setPC(String PC) {
        mPC = PC;
    }

    public int getInventoryCount() {
        return mInventoryCount;
    }

    public void setInventoryCount(int inventoryCount) {
        mInventoryCount = inventoryCount;
    }

    public int getOptimalRSSI() {
        return mOptimalRSSI;
    }

    public void setOptimalRSSI(int optimalRSSI) {
        mOptimalRSSI = optimalRSSI;
    }

    public int getWorstRSSI() {
        return mWorstRSSI;
    }

    public void setWorstRSSI(int worstRSSI) {
        mWorstRSSI = worstRSSI;
    }
}
