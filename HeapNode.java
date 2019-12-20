public class HeapNode {
    int key;
    int total_time;
    RedBlackNode redblacknode;

    public HeapNode(int total_time, int executionTime, RedBlackNode redblacknode) {
        key = executionTime;
        this.redblacknode = redblacknode;
        this.total_time = total_time;
    }

    public HeapNode() {
    }
}
