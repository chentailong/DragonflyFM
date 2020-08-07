package net.along.fragonflyfm.dataBase;

import com.orm.SugarRecord;

public class ProgramDataBase extends SugarRecord {

    private int programId;
    private String programName;
    private int count;
    private long timeStamp;

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getProgramId() {
        return programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "PreferProgramTable{" +
                "programId=" + programId +
                ", programName='" + programName + '\'' +
                ", count=" + count +
                ", timeStamp=" + timeStamp +
                '}';
    }

    @Override
    public long save() {
        return super.save();
    }
}
