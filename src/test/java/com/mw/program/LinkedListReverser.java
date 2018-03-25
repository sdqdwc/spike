package com.mw.program;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author WangCH
 * @create 2018-03-09 18:16
 */
public class LinkedListReverser {

    /**
     * Reaverses a linked list.递归
     * @param head the linked list to reverse
     * @return head of the reversed linked list
     */
    Node reverseLinkedList(Node head){
        //size == 0 size == 1
        if(head == null || head.getNext() == null){
            return head;
        }
        Node newHead = reverseLinkedList(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return newHead;
    }

    /**
     * 循环反转链表
     * @param head
     * @return
     */
    Node reverseLinkedList2(Node head){
        Node newHead = null;
        Node curHead = head;
        while(curHead != null){
          Node next = curHead.getNext();
          curHead.setNext(newHead);
          newHead = curHead;
          curHead = next;
        }
        return newHead;
    }


    public static void main(String[] args) {
        LinkedListCreator creator = new LinkedListCreator();
        LinkedListReverser reverser = new LinkedListReverser();
        //Node.printLinkedList(reverser.reverseLinkedList(creator.createLinkedList(new ArrayList<>())));
        //Node.printLinkedList(reverser.reverseLinkedList(creator.createLinkedList(Arrays.asList(1))));
        //Node.printLinkedList(reverser.reverseLinkedList(creator.createLinkedList(Arrays.asList(1,2,3,4,5))));
        Node.printLinkedList(reverser.reverseLinkedList2(creator.createLinkedList(new ArrayList<>())));
        Node.printLinkedList(reverser.reverseLinkedList2(creator.createLinkedList(Arrays.asList(1))));
        Node.printLinkedList(reverser.reverseLinkedList2(creator.createLinkedList(Arrays.asList(1,2,3,4,5))));
    }
}
