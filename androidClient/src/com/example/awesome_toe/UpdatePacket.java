package com.example.awesome_toe;
import java.util.Date;

public class UpdatePacket {
    private final int value;
    
    public UpdatePacket() {
        this((int) (System.currentTimeMillis() / 1000L + 2208988800L));
    }
    
    public UpdatePacket(int value) {
        this.value = value;
    }
        
    public int value() {
        return value;
    }
        
    @Override
    public String toString() {
        return new Date((value() - 2208988800L) * 1000L).toString();
    }
}