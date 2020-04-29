package com.example.test;

import com.example.bean.Label;

import java.util.ArrayList;
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
        System.out.print("\nlabelList.getRSSI(): " + labelList.get(0).getRSSI());

    }
}
