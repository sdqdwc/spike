package com.mw.program;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author WangCH
 * @create 2018-03-09 17:39
 */
public class LinkedListCreator {

    /**
     * Creates a linked list
     * @param data the data to create the list
     * @return head of the linked list.The returned linked list
     * ends with last node with getNext() == null
     */
    public Node createLinkedList(List<Integer> data){
        if(data.isEmpty()){
            return null;
        }
        Node firstNode = new Node(data.get(0));
        firstNode.setNext(createLinkedList(data.subList(1,data.size())));
        return firstNode;
    }

    /**
     * 循环创建链表
     * @param data
     * @return
     */
    public Node createLinkedList2(List<Integer> data){
        if(data.isEmpty()){
            return null;
        }
        Node firstNode = new Node(data.get(0));
        //循环创建链表
        Node curNode = firstNode;
        for(int i=1;i<data.size();i++){
            Node nextNode = new Node(data.get(i));
            curNode.setNext(nextNode);
            curNode = nextNode;
        }
        return firstNode;
    }

    //测试创建链表
    public static void main(String[] args) {
        LinkedListCreator creator = new LinkedListCreator();
        //Node.printLinkedList(creator.createLinkedList(new ArrayList<>()));
        //Node.printLinkedList(creator.createLinkedList(Arrays.asList(1)));
        //Node.printLinkedList(creator.createLinkedList(Arrays.asList(1,2,3,4,5)));
        Node.printLinkedList(creator.createLinkedList2(new ArrayList<>()));
        Node.printLinkedList(creator.createLinkedList2(Arrays.asList(1)));
        Node.printLinkedList(creator.createLinkedList2(Arrays.asList(1,2)));
        Node.printLinkedList(creator.createLinkedList2(Arrays.asList(1,2,3,4,5)));

    }
}
