package com.example.bean;

public class SysDepart {
    private String id;

    private String parentId;

    private String departName;

    public SysDepart(String id, String parentId, String departName) {
        this.id = id;
        this.parentId = parentId;
        this.departName = departName;
    }

    @Override
    public String toString() {
        return "SysDepart{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", departName='" + departName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }
}
