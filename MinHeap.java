public class MinHeap {

    private HeapNode rcarray[];
    int counter_size;

    public MinHeap() {
        this.rcarray = new HeapNode[100];
        counter_size = 0;
    }

    /* Insert Building Info into Heap
     *  Execution time of the building is used as key for heapify
     * */
    public void insert(int total_time, int executionTime, RedBlackNode node) {
        HeapNode newB = new HeapNode(total_time, executionTime, node);
        if (counter_size == rcarray.length) {
            increaseArraySize();
        }
        rcarray[counter_size] = newB;
        counter_size++;
        int last_ele = counter_size - 1;
        int parent_node = fetch_parent(last_ele);
        leaf(parent_node);

        while (parent_node != last_ele && rcarray[last_ele].key < rcarray[parent_node].key) {
            replace(last_ele, parent_node);
            last_ele = parent_node;
            parent_node = fetch_parent(last_ele);
        }

        buildHeap();
    }

    /*Function to double the size of the array
    when the array gets filled completely in the present state*/
    public void increaseArraySize() {
        HeapNode temp[] = new HeapNode[2 * counter_size];
        if (counter_size == rcarray.length) {
            for (int i = 0; i < counter_size; i++)
                temp[i] = rcarray[i];
        }
        leaf(counter_size);
        rcarray = temp;
    }

    public void buildHeap() {
        int k = counter_size / 2;
        while (k >= 0) {
            Heapify(k);
            --k;
        }
    }

    /*extractMin provides the minimum element in the Heap i.e. the root node*/
    public HeapNode extractMin() {
        switch (counter_size) {
            case 0:
                throw new IllegalStateException("MinHeap is currently empty/No new building is in the queue");
            case 1:
                HeapNode min = remove(0);
                --counter_size;
                return min;
        }
        HeapNode min = rcarray[0];
        HeapNode lastItem = remove(counter_size - 1);
        leaf(counter_size);
        rcarray[0] = lastItem;
        Heapify(0);
        --counter_size;
        return min;
    }

    private void replace(int index, int parent) {
        HeapNode temp = rcarray[parent];
        rcarray[parent] = rcarray[index];
        rcarray[index] = temp;
    }

    public HeapNode remove(int position) {
        HeapNode temp = rcarray[position];
        for (int i = position; i < counter_size - 1; i++) {
            rcarray[i] = rcarray[i + 1];
        }
        return temp;
    }

    private boolean leaf(int k) {
        if (k >= (counter_size / 2) && k <= counter_size) {
            return true;
        }
        return false;
    }

    /* minHeapify method is called to maintain the property of minHeap
     * after every insertion, deletion and extractMin*/
    private void Heapify(int element) {
        int leftof = fetchLeft(element);
        int rightof = fetchright(element);
        int smallest = -1;
        if (leftof <= counter_size - 1 && rcarray[leftof].key <= rcarray[element].key) {
            if (rcarray[leftof].key == rcarray[element].key) {
                if (rcarray[leftof].redblacknode.BuildingNumber < rcarray[element].redblacknode.BuildingNumber) {
                    smallest = leftof;
                } else {
                    smallest = element;
                }
            } else {
                smallest = leftof;
            }
        } else {
            smallest = element;
        }
        if (rightof <= counter_size - 1 && rcarray[rightof].key <= rcarray[smallest].key) {
            if (rcarray[rightof].key == rcarray[smallest].key) {
                if (rcarray[rightof].redblacknode.BuildingNumber < rcarray[smallest].redblacknode.BuildingNumber) {
                    smallest = rightof;
                }
            } else {
                smallest = rightof;
            }
        }
        if (smallest != element) {
            replace(element, smallest);
            Heapify(smallest);
        }
    }

    private int fetch_parent(int index) {
        return (index - 1) / 2;
    }

    public boolean isEmpty() {
        if (counter_size == 0)
            return true;
        else
            return false;
    }

    private int fetchright(int index) {

        return 2 * index + 2;
    }

    private int fetchLeft(int index) {

        return 2 * index + 1;
    }


}