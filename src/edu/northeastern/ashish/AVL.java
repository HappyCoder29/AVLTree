package edu.northeastern.ashish;

import java.util.LinkedList;
import java.util.Queue;

public class AVL {

    public Node root;
    public AVL(){

    }

    private int height(Node node){
        if(node == null)
            return 0;
        return node.height;
    }

    private int getBalance(Node node){
        if(node == null)
            return 0;
        return  height(node.left) - height(node.right);
    }

    private Node rotateRight(Node A){
        Node temp = A.left;
        A.left = temp.right;
        temp.right = A;

        // update heights
        A.height = Math.max(height(A.left), height(A.right)) +1;
        temp.height = Math.max(height(temp.left), height(temp.right)) + 1;
        return  temp;
    }

    private Node rotateLeft(Node A){
        Node temp = A.right;
        A.right = temp.left ;
        temp.left = A;

        // update heights
        A.height = Math.max(height(A.left), height(A.right)) +1;
        temp.height = Math.max(height(temp.left), height(temp.right)) + 1;
        return  temp;
    }

    // Level order iterative which prints every level at one line
    public void levelOrder(){
        if(root == null)
            return;

        // Take a queue and enqueue root and null
        // every level ending is signified by null
        // since there is just one node at root we enqueue root as well as null
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        queue.add(null);


        while(queue.size() != 0){

            Node node = queue.remove();
            // If the node is not null print it and enqueue its left and right child
            // if they exist
            if(node != null){
                System.out.print(node.data + " ,");
                if(node.left != null)
                    queue.add(node.left);
                if(node.right != null)
                    queue.add(node.right);
            }else{
                // We have reached a new level
                // Check is queue is empty, if yes then we are done
                // otherwise print a new line and enqueue a new null for next level
                System.out.println();
                if(queue.size() == 0)
                    break;
                queue.add(null);
            }
        }
    }

    public void insert(int data){
        root = insert(root, data);
    }

    private Node insert(Node node, int data){
        if(node == null)
            return  (new Node(data));
        if(data < node.data){
            node.left = insert(node.left, data);
        }else if (data > node.data){
            node.right = insert(node.right, data);
        }else{
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        // check for balance if balance is not right
        // rotate left or right

        if(balance > 1 && data < node.left.data){
            return  rotateRight(node);
        }

        if(balance < -1 && data > node.right.data){
            return  rotateLeft(node);
        }

        if(balance > 1 && data > node.left.data){
            node.left = rotateLeft(node.left);
            return  rotateRight(node);
        }

        if(balance < -1 && data < node.right.data){
            node.right = rotateRight(node.right);
            return  rotateLeft(node);
        }

        return  node;

    }

    public void deleteNode(int data){
        root = deleteNode(root, data);
    }

    private Node deleteNode(Node node, int data){
        if(node == null)
            return  null;

        if(data < node.data) {
            node.left = deleteNode(node.left, data);
        }
        else if (data > node.data){
            node.right = deleteNode(node.right, data);
        }else{

            // when both or just one node is null
            if(node.left == null)
                return node.right;
            else if (node.right == null)
                return node.left;

            node.data = getMinNode(node.right).data;
            node.right = deleteNode(node.right, node.data);

        }
        if(node == null)
            return  node;

        node.height = Math.max(height(node.left), height(node.right)) +1;

        int balance = getBalance(node);

        // check for balance if balance is not right
        // rotate left or right

        if(balance > 1 && getBalance(node.left) >= 0){
            return  rotateRight(node);
        }
        if(balance > 1 && getBalance(node.left) <0 ){
            node.left = rotateLeft(node.left);
            return  rotateRight(node);
        }

        if(balance < -1 && getBalance(node.right)<=0){
            return  rotateLeft(node);
        }



        if(balance < -1 && getBalance(node.right)> 0){
            node.right = rotateRight(node.right);
            return  rotateLeft(node);
        }

        return  node;
    }

    private Node getMinNode(Node node){
        Node current = node;
        while(current.left != null)
            current = current.left;
        return  current;
    }




}
