package ecnu.dll.dataset.struct;

import cn.edu.dll.basic.DateUtil;
import cn.edu.dll.struct.bean_structs.BeanInterface;

import java.text.ParseException;

public class TrajectoryBean implements BeanInterface<TrajectoryBean> {
    private static String TimeFormat = "YYYY-MM-dd HH:mm:ss";
    private Integer userID;
    private Long timestamp;
    private Double longitude;
    private Double latitude;

    public TrajectoryBean() {
    }

    public TrajectoryBean(Integer userID, Long timestamp, Double longitude, Double latitude) {
        this.userID = userID;
        this.timestamp = timestamp;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double[] getPosition() {
        return new Double[]{this.longitude, this.latitude};
    }

    public void setPosition(Double[] position) {
        this.longitude = position[0];
        this.latitude = position[1];
    }


    @Override
    public TrajectoryBean toBean(String[] parameters) {
        Integer userID = Integer.valueOf(parameters[0]);
        Long timestamp;
        try {
            timestamp = DateUtil.parseTimestamp(parameters[1], TimeFormat);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Double longitude = Double.valueOf(parameters[2]);
        Double latitude = Double.valueOf(parameters[3]);
        return new TrajectoryBean(userID, timestamp, longitude, latitude);
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "userID=" + userID +
                ", timestamp=" + timestamp +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
