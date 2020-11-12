package com.example.test;

import com.example.bean.Label;
import com.example.bean.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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

        String epcString = "4142434430313233343536373839";
        String epcChar = hexStr2Str(epcString);
        System.out.println(epcChar);

        int[] nums = {3,2,4};
        int target = 6;
        int[] result = twoSum(nums, target);
        System.out.println(result[0] + "," + result[1]);

        ListNode head = new ListNode(3);
        head.next = new ListNode(2);
        ListNode tail = head.next;
        tail.next = new ListNode(0);
        tail = tail.next;
        tail.next = new ListNode(-4);
        ListNode resultListNode = detectCycle( head);
        System.out.println(resultListNode);

        System.out.println(reverse(123456700));
        System.out.println(reverse(-12345670));
    }

    public static int reverse(int x) {
        int result = -1;
        String s = String.valueOf(x);
        boolean isPositiveNumber;
        isPositiveNumber = x >= 0;
        int i = s.length() - 1;
        StringBuilder s1 = new StringBuilder();
        if (isPositiveNumber) {
            for (; i >= 0; i--) {
                s1.append(s, i, i + 1);
            }
        } else {
            for (; i > 0; i--) {
                s1.append(s, i, i + 1);
            }
        }
        System.out.println(s1.toString());
        try {
            result = Integer.parseInt(s1.toString());
            if (!isPositiveNumber) result = -result;
        } catch (Exception e){
            result = -1;
        }
        return result;
    }

    public static boolean canPartition(int[] nums) {
        int numLength = nums.length;
        if (numLength < 2) return false;
        int sun = 0, maxNum = 0;
        for (int a : nums){
            sun = sun + a;
            maxNum = Math.max(sun, a);
        }
        if (sun % 2 != 0) return false;
        int target = sun / 2;
        if (maxNum > target) return false;

        boolean[][] dp = new boolean[numLength][target + 1];
        for (int i = 0; i < numLength; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;
        for (int i = 1; i < numLength; i++) {
            int num = nums[i];
            for (int j = 1; j <= target; j++) {
                if (j >= num) {
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - num];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[numLength - 1][target];
    }

    public static ListNode detectCycle(ListNode head) {
        ListNode pos = head;
        Set<ListNode> visited = new HashSet<ListNode>();
        while (pos != null) {
            if (visited.contains(pos)) {
                return pos;
            } else {
                visited.add(pos);
            }
            pos = pos.next;
        }
        return null;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null, tail = null;
        int carry = 0;
        while (l1 != null || l2 != null){
            int a1 = l1 != null ? l1.val : 0;
            int a2 = l2 != null ? l2.val : 0;
            int sun = carry + a1 + a2;
            if (head == null){
                head = tail = new ListNode(sun % 10);
            } else {
                tail.next = new ListNode(sun % 10);
                tail = tail.next;
            }
            carry = sun / 10;

            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        if (carry > 0) tail.next = new ListNode(carry);

        return head;
    }

//    public static ListNode getLastNumber(ListNode l) {
//
//    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }

    public static int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            if (map.containsKey(target - nums[i])){
                result[0] = map.get(target - nums[i]);
                result[1] = i;
            }
            map.put(nums[i], i);
        }

//        for (int i = 0; i < nums.length - 1; i++){
//            for (int k = i + 1; k < nums.length; k++){
//                if (nums[i] + nums[k] == target){
//                    result[0] = nums[i];
//                    result[1] = nums[k];
//                    break;
//                }
//            }
//        }
        return result;
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
