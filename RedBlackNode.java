public class RedBlackNode {

    int BuildingNumber;
    int count;
    int totaltime;
    int executiontime;
    RedBlackNode left_node, right_node, parent;
    Color color;

    public RedBlackNode(int BuildingNumber, int count, RedBlackNode left_node, RedBlackNode right_node,
                        RedBlackNode parent, Color color, int totaltime, int executiontime) {

        this.BuildingNumber = BuildingNumber;
        this.count = count;
        this.left_node = left_node;
        this.right_node = right_node;
        this.parent = parent;
        this.color = color;
        this.totaltime = totaltime;
        this.executiontime = executiontime;
    }

    public RedBlackNode() {
    }

    public boolean isEmpty() {
        return false;
    }
}
