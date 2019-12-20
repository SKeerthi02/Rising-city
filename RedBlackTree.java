
import java.util.ArrayList;


class Empty extends RedBlackNode {
    public Empty() {
        super();
        this.color = Color.black;
    }

    public boolean isEmpty() {
        return true;
    }
}

public class RedBlackTree {
    RedBlackNode root = new Empty();

    //print the total count of IDs between Building_from and Building_to;
    public ArrayList<Integer> PrintBuilding(int Building_from, int Building_to) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        searchBuildings(root, Building_from, Building_to, arr);
        int total = 0;
        return arr;
    }

    public int PrintBuilding(int ID) {
        ArrayList<Integer> arr = new ArrayList<Integer>();
        findBuildings(root, ID, arr);
        int total = 0;
        if (arr.size() == 0) {
            return 0;
        } else
            return arr.get(0);
    }

    //this function finds all the nodes in the range low,high
    private void searchBuildings(RedBlackNode root, int building_num_from, int building_num_to, ArrayList<Integer> res) {
        if (root == null) {
            return;
        }
        if (root.BuildingNumber > building_num_from) {
            searchBuildings(root.left_node, building_num_from, building_num_to, res);
        }
        if (root.BuildingNumber >= building_num_from && root.BuildingNumber <= building_num_to) {
            res.add(root.BuildingNumber);
        }
        if (root.BuildingNumber < building_num_to) {
            searchBuildings(root.right_node, building_num_from, building_num_to, res);
        }

    }

    //this function finds the jobs with matching id to search node
    private void findBuildings(RedBlackNode root, int k, ArrayList<Integer> res) {
        if (root == null) {
            return;
        }
        if (root.BuildingNumber > k) {
            findBuildings(root.left_node, k, res);
        }
        if (root.BuildingNumber == k) {
            res.add(root.BuildingNumber);
        }
        if (root.BuildingNumber < k) {
            findBuildings(root.right_node, k, res);
        }
    }

    //find min node in RedBlackTree
    public RedBlackNode findMin(RedBlackNode root) {
        RedBlackNode current = root;
        while (!current.left_node.isEmpty()) {
            current = current.left_node;
        }
        return current;
    }

    //find max node in RB_tree
    public RedBlackNode findMax(RedBlackNode root) {
        RedBlackNode current = root;
        while (!current.right_node.isEmpty()) {
            current = current.right_node;
        }
        return current;
    }

    //search a node, if exsits, return the node, otherwise return null;
    public RedBlackNode Search(int BuildingNumber, RedBlackNode root) {
        while (!root.isEmpty()) {
            if (root.BuildingNumber == BuildingNumber) {
                return root;
            } else if (root.BuildingNumber > BuildingNumber) {
                root = root.left_node;
            } else {
                root = root.right_node;
            }
        }
        return root;
    }

    public int insertSearch(int BuildingNumber, RedBlackNode root) {
        while (!root.isEmpty()) {
            if (root.BuildingNumber == BuildingNumber) {
                return 1;
            } else if (root.BuildingNumber > BuildingNumber) {
                root = root.left_node;
            } else {
                root = root.right_node;
            }
        }
        return 0;
    }

    // insertion into RB_tree
    public RedBlackNode Insert(int BuildingNumber, int total_time, int executionTime) {
        RedBlackNode temp = this.root;
        RedBlackNode last = temp;
        while (!temp.isEmpty()) {
            last = temp;
            if (BuildingNumber > temp.BuildingNumber) {
                temp = temp.right_node;
            } else {
                temp = temp.left_node;
            }
        }
        Empty Empty = new Empty();
        bst();
        RedBlackNode red = new RedBlackNode(BuildingNumber, 0, Empty, Empty, Empty, Color.red, total_time, executionTime);
        red.parent = last;
        if (last.isEmpty()) {
            this.root = red;
        }
        if (last.BuildingNumber > BuildingNumber) {
            last.left_node = red;
        } else {
            last.right_node = red;
        }
        return red;
    }

    // left rotate of a given node;
    public void left_rotation(RedBlackNode node) {
        RedBlackNode nodey = node.right_node;
        node.right_node = nodey.left_node;
        if (!nodey.left_node.isEmpty()) {
            nodey.left_node.parent = node;
        }
        if (node.parent.isEmpty()) {
            this.root = nodey;
            nodey.parent = node.parent;
        } else if (node == node.parent.left_node) {
            node.parent.left_node = nodey;
            nodey.parent = node.parent;
        } else {
            node.parent.right_node = nodey;
            nodey.parent = node.parent;
        }
        nodey.left_node = node;
        node.parent = nodey;
    }

    //right rotate of a given node;
    public void right_rotation(RedBlackNode node) {
        RedBlackNode nodex = node.left_node;
        node.left_node = nodex.right_node;
        if (!nodex.right_node.isEmpty()) {
            nodex.right_node.parent = node;
        }
        if (node.parent.isEmpty()) {
            this.root = nodex;
            nodex.parent = node.parent;
        } else if (node == node.parent.left_node) {
            node.parent.left_node = nodex;
            nodex.parent = node.parent;
        } else {
            node.parent.right_node = nodex;
            nodex.parent = node.parent;
        }
        nodex.right_node = node;
        node.parent = nodex;
    }


    //replace the two given nodes
    public void replace_RB(RedBlackNode nodeu, RedBlackNode nodev) {
        if (nodeu.parent.isEmpty()) {
            this.root = nodev;
        } else if (nodeu == nodeu.parent.left_node) {
            nodeu.parent.left_node = nodev;
        } else {
            nodeu.parent.right_node = nodev;
        }
        nodev.parent = nodeu.parent;
    }

    //deletion of node from RedBlackTree
    public void delete(RedBlackNode nodez) {
        RedBlackNode nodey = nodez;
        RedBlackNode copy_y = nodey;
        RedBlackNode nodex;
        copy_y.color = nodey.color;
        if (nodez.left_node.isEmpty()) {
            nodex = nodez.right_node;
            replace_RB(nodez, nodez.right_node);
        } else if (nodez.right_node.isEmpty()) {
            nodex = nodez.left_node;
            replace_RB(nodez, nodez.left_node);
        } else {
            nodey = findMin(nodez.right_node);
            nodex = nodey.right_node;
            copy_y.color = nodey.color;
            replace_RB(nodey, nodey.right_node);
            replace_RB(nodez, nodey);
            nodey.left_node = nodez.left_node;
            nodey.left_node.parent = nodey;
            nodey.right_node = nodez.right_node;
            nodey.right_node.parent = nodey;
            nodey.color = nodez.color;
        }
        if (copy_y.color == Color.black) {
            rearrange(nodex);
        }
    }
    public void bst(){
        int i, key, j;
        int arr[] = {6,7,3,8,1,9,3};
        for (i = 1; i < arr.length; i++)
        {
            key = arr[i];
            j = i - 1;

        /* Move elements of arr[0..i-1], that are
        greater than key, to one position ahead
        of their current position */
            while (j >= 0 && arr[j] > key)
            {
                arr[j + 1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }
    //After deletion, do the fixup to satisfy the property of red black tree
    public void rearrange(RedBlackNode nodex) {
        while (nodex != this.root && nodex.color == Color.black) {
            //nodex is left child
            if (nodex == nodex.parent.left_node) {
                RedBlackNode nodew = nodex.parent.right_node;
                //when sibling of nodex is red
                if (nodew.color == Color.red) {
                    nodew.color = Color.black;
                    nodex.parent.color = Color.red;
                    left_rotation(nodex.parent);
                    nodew = nodex.parent.right_node;
                }
                if (nodew.left_node.color == Color.black && nodew.right_node.color == Color.black) {
                    //when sibling of nodex is black, and both children of sibling are black
                    nodew.color = Color.red;
                    nodex = nodex.parent;
                } else {
                    //sibling of nodex is black and left child of sibling  is red and right child is black
                    if (nodew.right_node.color == Color.black) {
                        nodew.left_node.color = Color.black;
                        nodew.color = Color.red;
                        right_rotation(nodew);
                        nodew = nodex.parent.right_node;
                    }
                    //sibling of nodex is black and right child is black
                    nodew.color = nodex.parent.color;
                    nodex.parent.color = Color.black;
                    nodew.right_node.color = Color.black;
                    left_rotation(nodex.parent);
                    nodex = this.root;

                }
            }
            //nodex is the right child
            else if (nodex == nodex.parent.right_node) {
                RedBlackNode nodew = nodex.parent.left_node;
                if (nodew.color == Color.red) {
                    nodew.color = Color.black;
                    nodex.parent.color = Color.red;
                    right_rotation(nodex.parent);
                    nodew = nodex.parent.left_node;
                }
                if (nodew.left_node.color == Color.black && nodew.right_node.color == Color.black) {
                    nodew.color = Color.red;
                    nodex = nodex.parent;
                } else {
                    if (nodew.left_node.color == Color.black) {
                        nodew.right_node.color = Color.black;
                        nodew.color = Color.red;
                        left_rotation(nodew);
                        nodew = nodex.parent.left_node;
                    }
                    nodew.color = nodex.parent.color;
                    nodex.parent.color = Color.black;
                    nodew.left_node.color = Color.black;
                    right_rotation(nodex.parent);
                    nodex = this.root;
                }

            }

        }
        nodex.color = Color.black;
    }

}

	
