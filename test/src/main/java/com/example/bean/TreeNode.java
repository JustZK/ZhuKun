package com.example.bean;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    public TreeNode(String id, String pId,String name) {
        this.id = id;
        this.name = name;
        this.pId = pId;
    }
    private String id;
    private String name;
    private String pId;
    private List<TreeNode> children = new ArrayList<TreeNode>();


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getpId() {
        return pId;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pId='" + pId + '\'' +
                ", children=" + children +
                '}';
    }
}
