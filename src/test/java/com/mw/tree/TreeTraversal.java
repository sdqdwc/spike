package com.mw.tree;

/**
 * @author WangCH
 * @create 2018-03-21 13:46
 */
public class TreeTraversal {

    /**
     * 前序遍历二叉树
     * @param root
     */
    public void preOrder(TreeNode root){
        if(root == null){
            return;
        }
        System.out.print(root.getValue());
        preOrder(root.getLeft());
        preOrder(root.getRight());
    }

    /**
     * 中序遍历
     * @param root
     */
    public void inOrder(TreeNode root){
        if(root == null){
            return;
        }
        inOrder(root.getLeft());
        System.out.print(root.getValue());
        inOrder(root.getRight());
    }

    /**
     * 后序遍历
     * @param root
     */
    public void postOrder(TreeNode root){
        if(root == null){
            return;
        }
        postOrder(root.getLeft());
        postOrder(root.getRight());
        System.out.print(root.getValue());
    }

    public static void main(String[] args) {
        TreeCreator creator = new TreeCreator();
        TreeNode sampleTree= creator.createSampleTree();

        TreeTraversal treeTraversal = new TreeTraversal();
        System.out.print("前序遍历：");
        treeTraversal.preOrder(sampleTree);
        System.out.println();
        System.out.print("中序遍历：");
        treeTraversal.inOrder(sampleTree);
        System.out.println();
        System.out.print("后序遍历：");
        treeTraversal.postOrder(sampleTree);
        System.out.println();
    }
}
