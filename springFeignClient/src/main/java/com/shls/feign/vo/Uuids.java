package com.shls.feign.vo;

//多个uuid
public class Uuids {
    //uuid
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Uuids(){
        super();
    }
    public Uuids(String uuid){
        super();
        this.uuid = uuid;
    }
}
