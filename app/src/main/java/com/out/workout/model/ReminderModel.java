package com.out.workout.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

public final class ReminderModel implements Parcelable{

    private ReminderModel(Parcel in) {
        id = in.readLong();
        time = in.readLong();
        booleanArray = in.readSparseBooleanArray();
        isEnabled = in.readByte() != 0;
    }

    public static final Creator<ReminderModel> CREATOR = new Creator<ReminderModel>() {
        @Override
        public ReminderModel createFromParcel(Parcel in) {
            return new ReminderModel(in);
        }

        @Override
        public ReminderModel[] newArray(int size) {
            return new ReminderModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeLong(time);
        parcel.writeSparseBooleanArray(booleanArray);
        parcel.writeByte((byte) (isEnabled ? 1 : 0));
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({MON,TUES,WED,THURS,FRI,SAT,SUN})
    @interface Days{}
    public static final int MON = 1;
    public static final int TUES = 2;
    public static final int WED = 3;
    public static final int THURS = 4;
    public static final int FRI = 5;
    public static final int SAT = 6;
    public static final int SUN = 7;


    private final long id;
    private long time;
    private SparseBooleanArray booleanArray;
    private boolean isEnabled;

    public ReminderModel(long id) {
        this(id, System.currentTimeMillis());
    }

    public ReminderModel(long id, long time, @Days int... days) {
        this.id = id;
        this.time = time;
        this.booleanArray = buildDaysArray(days);
    }

    public long getId() {
        return id;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
    public void setDay(@Days int day, boolean isAlarmed) {
        booleanArray.append(day, isAlarmed);
    }

    public SparseBooleanArray getDays() {
        return booleanArray;
    }

    public boolean getDay(@Days int day){
        return booleanArray.get(day);
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public int notificationId() {
        final long id = getId();
        return (int) (id^(id>>>32));
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", time=" + time +
                ", allDays=" + booleanArray +
                ", isEnabled=" + isEnabled +
                '}';
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) (id^(id>>>32));
        result = 31 * result + (int) (time^(time>>>32));
        for(int i = 0; i < booleanArray.size(); i++) {
            result = 31 * result + (booleanArray.valueAt(i)? 1 : 0);
        }
        return result;
    }

    private static SparseBooleanArray buildDaysArray(@Days int... days) {
        final SparseBooleanArray array = buildBaseDaysArray();
        for (@Days int day : days) {
            array.append(day, true);
        }
        return array;
    }

    private static SparseBooleanArray buildBaseDaysArray() {
        final int numDays = 7;
        final SparseBooleanArray array = new SparseBooleanArray(numDays);
        array.put(MON, false);
        array.put(TUES, false);
        array.put(WED, false);
        array.put(THURS, false);
        array.put(FRI, false);
        array.put(SAT, false);
        array.put(SUN, false);
        return array;
    }
}
