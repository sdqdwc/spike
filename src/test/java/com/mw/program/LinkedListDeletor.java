package com.mw.program;

import java.util.Arrays;

/**
 * @author WangCH
 * @create 2018-03-12 19:55
 */
public class LinkedListDeletor {

    public Node deleteIfEquals(Node head,int value){
        while(head!=null && head.getValue() == value){
            head = head.getNext();
        }
        Node prev = head;
        if(head == null){
            return null;
        }
        while(prev.getNext() != null){
            if(prev.getNext().getValue() == value){
                prev.setNext(prev.getNext().getNext());
            }else{
                prev = prev.getNext();
            }
        }
        return head;
    }


    public static void main(String[] args) {
        LinkedListCreator creator = new LinkedListCreator();
        LinkedListDeletor deletor = new LinkedListDeletor();
        Node.printLinkedList(deletor.deleteIfEquals(creator.createLinkedList(Arrays.asList(1,2,3,2,5)),2));
        Node.printLinkedList(deletor.deleteIfEquals(creator.createLinkedList(Arrays.asList(2,1,3,2,5)),2));
        Node.printLinkedList(deletor.deleteIfEquals(creator.createLinkedList(Arrays.asList(2,2,3,4,5)),2));
        Node.printLinkedList(deletor.deleteIfEquals(creator.createLinkedList(Arrays.asList(2,2)),2));
    }
}
