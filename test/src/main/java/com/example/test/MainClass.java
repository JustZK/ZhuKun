package com.example.test;

import com.example.bean.Label;
import com.example.bean.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainClass {

    public static void main(String[] args) {
        List<Label> labelList = new ArrayList<>();
        List<Label> labelList1 = new ArrayList<>();
        Label label1 = new Label();
        label1.setEPC("A1234567890");
        label1.setRSSI(-11);
        Label label2 = new Label();
        label2.setEPC("a1234567890");
        label2.setRSSI(-18);
        Label label3 = new Label();
        label3.setEPC("a1234567890");
        label3.setRSSI(-19);
        labelList.add(label1);
        labelList1.add(label2);
        System.out.print("labelList.contains(label2): " + labelList.contains(label2));
        System.out.print("\nlabel3.equals(label2): " + label3.equals(label2));
        System.out.print("\nlabelList.size(): " + labelList.size());
        System.out.print("\nlabelList.remove(): " + labelList.removeAll(labelList1));
        System.out.print("\nlabelList.size(): " + labelList.size());
        System.out.print("\nlabelList.addAll(): " + labelList.addAll(labelList1));
        System.out.print("\nlabelList.getRSSI(): " + labelList.get(0).getRSSI() + "\n");

        ArrayList<TreeNode> list = new ArrayList<>();
        list.add(new TreeNode("13123", "23213", "三级企业5454"));
        list.add(new TreeNode("32323", "23213", "三级企业5454353453-"));
        list.add(new TreeNode("32332432423", "23213", "三级企业521321321----"));
        list.add(new TreeNode("32332433333332423", "32332432423", "四级企业521321321----"));
        list.add(new TreeNode("32332432423", "32332433333332423", "五级企业521321321----"));
        list.add(new TreeNode("1", null, "主企业"));
        list.add(new TreeNode("121323", "1", "二级企业1"));
        list.add(new TreeNode("323223", "1", "二级企业2"));
        list.add(new TreeNode("2321312", "1", "二级企业3"));
        list.add(new TreeNode("321323", "1", "二级企业4"));
        list.add(new TreeNode("23213", "1", "二级企业5"));

        List<TreeNode> deptTree = ToTree(list);
        System.out.println(deptTree);

        ArrayList<String> idList = new ArrayList<>();
        ArrayList<String> idList1 = getIdList(idList, deptTree);
        System.out.println(idList1);
    }

    public static ArrayList<String> getIdList(ArrayList<String> idList, List<TreeNode> deptTree) {
        boolean isAgain = false;
        ArrayList<TreeNode> list = new ArrayList<>();
        for (TreeNode treeNode : deptTree) {
            idList.add(treeNode.getId());
            if (treeNode.getChildren() != null) {
                list.addAll(treeNode.getChildren());
                isAgain = true;
            }
        }
        if (isAgain) {
            return getIdList(idList, list);
        }
        return idList;
    }


    public static List<TreeNode> ToTree(List<TreeNode> list) {

        HashMap<String, TreeNode> mapTmp = new HashMap<>();
        for (TreeNode current : list) {
            mapTmp.put(current.getId(), current);
        }
        ArrayList<TreeNode> finalList = new ArrayList<>();
        mapTmp.forEach((k, v) -> {
            if (v.getpId() == null) {
                finalList.add(v);
            } else {
                mapTmp.get(v.getpId()).getChildren().add(v);
            }
        });
        return finalList;
    }
}
