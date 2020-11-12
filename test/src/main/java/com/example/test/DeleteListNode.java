package com.example.test;

public class DeleteListNode {
    static ListNode head = null;

    public static void main(String[] args) {
        int[] input = new int[]{1, 2, 3, 3, 4, 4, 5};
        ListNode listNode = buildListNode(input);
        head = listNode;
        while (listNode != null) {
            System.out.println("val " + listNode.val + " /listNode" + listNode.next);
            listNode = listNode.next;
        }
        head = removeElements(head, 3);
        listNode = head;
        while (listNode != null) {
            System.out.println("val " + listNode.val + "/listNode" + listNode.next);
            listNode = listNode.next;
        }
    }

    private static ListNode buildListNode(int[] input) {
        ListNode first = null, last = null, newNode;
        int num;
        if (input.length > 0) {
            for (int i = 0; i < input.length; i++) {
                newNode = new ListNode(input[i]);
                newNode.next = null;
                if (first == null) {
                    first = newNode;
                    last = newNode;
                } else {
                    last.next = newNode;
                    last = newNode;
                }

            }
        }
        return first;
    }

    private static ListNode removeElements(ListNode head, int val) {
        if (head == null) {
            return null;
        }
        ListNode p = head, q = head.next;
        while (q != null) {
            if (q.val == val) {
                p.next = q.next;
                q = q.next;
            } else {
                p = p.next;
                q = q.next;
            }
        }
        if (head.val == val) {
            return head.next;
        }
        return head;

    }
}
