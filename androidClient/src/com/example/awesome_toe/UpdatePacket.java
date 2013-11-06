package com.example.awesome_toe;
import java.util.Date;

public class UpdatePacket {
    private final int value;
    
    public UpdatePacket() {
        this((int) (System.currentTimeMillis()));
    }
    
    public UpdatePacket(int value) {
        this.value = value;
    }
        
    public int value() {
        return value;
    }
        
    @Override
    public String toString() {
        return new Date(value()+25L*24L*60L*60L*1000L).toString();
    }
}