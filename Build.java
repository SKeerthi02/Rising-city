import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Build {


    public RedBlackTree redblacktree;
    public MinHeap heap;
    public int jobID;
    ArrayList<String> result;

    public Build() {
        this.heap = new MinHeap();
        this.redblacktree = new RedBlackTree();
        this.result = new ArrayList<String>();

    }

    public void WritetoFile() {
        try {
            BufferedWriter bufferwriter = new BufferedWriter(new FileWriter("output_file.txt"));
            int i = 0;
            while (i < result.size()) {
                bufferwriter.append(result.get(i));
                bufferwriter.append('\n');
                ++i;
            }
            bufferwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InsertBuilding(int buildNum, int total_time, int executionTime) {

        if (redblacktree.insertSearch(buildNum, redblacktree.root) == 0) {
            redblacktree.Insert(buildNum, total_time, executionTime);
            RedBlackNode newNode = redblacktree.Search(buildNum, redblacktree.root);
            heap.insert(total_time, executionTime, newNode);
        } else {
            result.add("BAZINGAA! Building - " + buildNum + " already exits in the System");
            WritetoFile();
            System.exit(1);
        }
    }

    public void PrintBuilding(int ID) {
        RedBlackNode newNode = redblacktree.Search(ID, redblacktree.root);
        if (!newNode.isEmpty()) {
            result.add("(" + redblacktree.PrintBuilding(ID) + "," + newNode.executiontime + "," + newNode.totaltime + ")");
        } else {
            result.add("(0,0,0)");
        }
    }

    public void printDeletingBuilding(int ID, long completed_time) {
        result.add("(" + ID + "," + completed_time + ")");
    }

    public void PrintBuilding(int ID1, int ID2) {
        ArrayList<Integer> res = redblacktree.PrintBuilding(ID1, ID2);
        if (res.size() != 0) {
            String s = "";
            int i = 0;
            while (i < res.size()) {
                if (res.get(i) != 0) {
                    RedBlackNode newNode = redblacktree.Search(res.get(i), redblacktree.root);
                    s += "(" + res.get(i) + "," + newNode.executiontime + "," + newNode.totaltime + ")";
                    if (i != res.size() - 1) {
                        s += ",";
                    }
                }
                ++i;
            }
            char[] checkarr = s.toCharArray();
            if (checkarr[checkarr.length - 1] == ',') {
                s = s.substring(0, checkarr.length - 1);
            }
            result.add(s);
        } else {
            String s = "";
            s += "(0,0,0)";
            result.add(s);
        }
    }

}
