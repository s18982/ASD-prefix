import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class s18982 {
    public static void main(String[] args) {
        File file = new File(args[0]);
        PQueue pQueue = new PQueue();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
              String str = scanner.next();
              int x = scanner.nextInt();

              pQueue.add(new Vertex(str,x));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        pQueue.PrintQ();
        Vertex tmp1 = null,tmp2=null;

        int i = 0;
        System.out.println(pQueue.getSize());
        while (i<pQueue.getSize()-1){
            tmp1 = pQueue.MIN();
            System.out.println(tmp1.label+"  "+tmp1.left+" "+tmp1.right+"  t1");
            pQueue.DelMin();
            tmp2 = pQueue.MIN();
            System.out.println(tmp2.label+" "+tmp2.left+" "+tmp2.right+" t2");
            pQueue.DelMin();
            tmp1 = connect(tmp1,tmp2);
            System.out.println(tmp1.label+" "+tmp1.left+" "+tmp1.right+" co");
            pQueue.add(tmp1);
            pQueue.PrintQ();
        }
        System.out.println("sd");
        pQueue.PrintQ();

    }

    public static Vertex connect(Vertex v1, Vertex v2){
        Vertex vertex3 = new Vertex();

        vertex3.x = v1.x+v2.x;
        vertex3.label += v1.label;
        vertex3.label+=v2.label;
        vertex3.left = v1.label;
        vertex3.right = v2.label;

        return vertex3;
    }
}

class Vertex {
    public int x;
    public String label = "";
    public String left = null;
    public String right = null;

    public Vertex(){
    }

    public Vertex(String string, int x){
        label = string;
        this.x = x;
    }
}

class Node{
    public Vertex vertex;
    public Node next;

    public Node(Vertex v){
        vertex = v;
        next = null;
    }
    public Node(Vertex v1, Vertex v2){
        vertex = v1;
        next = new Node(v2);
    }
}

class PQueue{
    public Node head;
    public static int size = 0;

    public PQueue(){
    }

    public void add(Vertex vertex){
        if(head == null){
            Node node = new Node(vertex);
            head = node;
            size++;
        }else {

            if (head != null && size == 1){
                Node nodeHead = head;
                Node node = new Node(vertex);
                if(vertex.x<nodeHead.vertex.x){
                    node.next=nodeHead;
                    head = node;
                    size++;
                }else{
                    nodeHead.next = node;
                    size++;
                }
            }else{
                if(head != null && size>1){
                    Node node = new Node(vertex);
                    Node nodeHead = head;
                    if(node.vertex.x < nodeHead.vertex.x){
                        node.next = nodeHead;
                        head = node;
                        size++;
                    }else {
                        int i = 1;
                        if(i ==1) {
                            if (node.vertex.x >= nodeHead.vertex.x) {
                                Node tmp = nodeHead.next;
                                nodeHead.next = node;
                                node.next = tmp;
                                head = nodeHead;
                                size++;
                            }
                            i++;
                            Node prevNode = nodeHead;
                            Node nextNode = node.next;
                            while (i != size){
                                if(node.vertex.x>=nextNode.vertex.x){
                                    if(nextNode.next!= null){
                                        Node tmp = nextNode.next;
                                        nextNode.next = node;
                                        prevNode.next = nextNode;
                                        node.next = tmp;
                                    }else{
                                        nextNode.next = node;
                                        prevNode.next = nextNode;
                                        node.next = null;
                                    }
                                    prevNode = prevNode.next;
                                    nextNode = node.next;
                                    head = nodeHead;
                                    i++;
                                }else{
                                    i = size;
                                }
                            }
                        }
                    }

                }
            }

        }
    }
    public int getSize(){
        return size;
    }

    public void PrintQ(){
        Node node = head;
        for (int i = 0;i<size;i++){

            System.out.print(node.vertex.label+", ");
            node = node.next;
        }
        System.out.println();
    }

    public Vertex MIN(){
        return head.vertex;
    }

    public void DelMin(){
        if(head == null){
            head = null;

        }
        if(head != null){
            if(head.next==null){
                head = null;

            }else
            if(head.next != null){
                Node node = new Node(head.next.vertex);
                head = head.next;
            }
        }
        size--;
    }
}

