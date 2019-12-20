import java.io.*;
import java.lang.*;
import java.util.*;

class risingCity {
    static HeapNode newheap = null;

    private static int decide(String buildingData, int arguments) {
        switch (arguments) {
            case 1:
                return Integer.parseInt(buildingData.split("\\)")[0].split(",")[0]);
            default:
                return Integer.parseInt(buildingData.split("\\)")[0].split(",")[1]);
        }
    }

    public static void main(String args[]) throws IOException {
        BufferedReader bufferreader = new BufferedReader(new FileReader(args[0]));
        BufferedReader timereader = new BufferedReader(new FileReader(args[0]));
        List<Long> PrintB_bef_del = new ArrayList<>();
        List<String> PrintB_bef_del_s = new ArrayList<>();

        int counter = 0;
        boolean isBusy = false;
        boolean buildingDone = false;
        long maximum_time = 0;
        String work_order;

        while ((work_order = timereader.readLine()) != null) {
            if (work_order.split(" ")[1].split("\\(")[0].equalsIgnoreCase("Insert"))
                maximum_time += Long.parseLong(work_order.split(",")[1].split("\\)")[0]) + Long.parseLong(work_order.split(":")[0]);
            ;
        }
        int buildNum1, buildNum2, buildingExecutionTime = 0;

        Build build = new Build();

        //parsing the input file to recognize commands given by user
        long global_time = 0;
        int total_time = 0;
        String line = bufferreader.readLine(); // 0: Insert(50,100)
        long building_arrival_time = Integer.parseInt(line.split(":")[0]); //  0
        String second = line.split(" ")[1];  //  Insert(50,100)
        String command = (second.split("\\("))[0];  // Insert
        String arg = (second.split("\\("))[1]; //  50,100)
        long start = System.nanoTime();
        for (global_time = 0; global_time <= maximum_time; ++global_time) {
            if (global_time == building_arrival_time) {      //
                if (command.equalsIgnoreCase("Insert")) {     // if command is Insert
                    buildNum1 = decide(arg, 1);
                    total_time = decide(arg, 2);
                    build.InsertBuilding(buildNum1, total_time, buildingExecutionTime);
                }
                if (command.equalsIgnoreCase("PrintJob") && PrintB_bef_del.contains(building_arrival_time)) {
                    if (arg.split("\\)")[0].split(",").length == 1) {
                        buildNum1 = decide(arg, 1);
                        build.PrintBuilding(buildNum1);
                    }
                    if (arg.split("\\)")[0].split(",").length == 2) {
                        buildNum1 = decide(arg, 1);
                        buildNum2 = decide(arg, 2);
                        build.PrintBuilding(buildNum1, buildNum2);
                    }
                }
                if ((line = bufferreader.readLine()) != null) {
                    building_arrival_time = Integer.parseInt(line.split(":")[0]);
                    second = line.split(" ")[1];
                    command = (second.split("\\("))[0];
                    String argu = (second.split("\\("))[1];
                    if (command.equalsIgnoreCase("PrintJob")) {
                        PrintB_bef_del.add(building_arrival_time);
                        PrintB_bef_del_s.add(argu);
                    }
                    arg = (second.split("\\("))[1];
                }
            }
            //look for new building
            if (!isBusy) {
                if (build.heap.isEmpty()) {
                    continue;
                } else {
                    newheap = build.heap.extractMin();
                    isBusy = true;
                    buildingDone = false;
                }
            }
            // If already building construction under going
            if (isBusy) {
                counter++;
                newheap.key += 1;
                newheap.redblacknode.executiontime += 1;
                if (newheap.redblacknode.executiontime == newheap.total_time) {
                    if (PrintB_bef_del.contains(global_time + 1)) {
                        int index = PrintB_bef_del.indexOf(global_time + 1);
                        if (PrintB_bef_del_s.get(index).split("\\)")[0].split(",").length == 2) {
                            buildNum1 = decide(arg, 1);
                            buildNum2 = decide(arg, 2);
                            PrintB_bef_del.remove(index);
                            PrintB_bef_del_s.remove(index);
                            build.PrintBuilding(buildNum1, buildNum2);
                        } else {
                            buildNum1 = decide(arg, 1);
                            build.PrintBuilding(buildNum1);
                        }
                    }
                    buildingDone = true;
                    build.printDeletingBuilding(newheap.redblacknode.BuildingNumber, (global_time + 1));
                    build.redblacktree.delete(newheap.redblacknode);
                    if (counter != 5) {
                        counter = 0;
                        isBusy = false;
                    }
                } else {
                    buildingDone = false;
                }
                //inserting data back if file not executed completely
                if (counter == 5) {
                    counter = 0;
                    if (!buildingDone) {
                        build.heap.insert(newheap.total_time, newheap.key, newheap.redblacknode);
                    }
                    isBusy = false;
                }
            }
        }
        build.WritetoFile();
        long end = System.nanoTime();
        System.out.println("Total Time taken : " + ((double) (end - start) / 1000000000) + "seconds");
        bufferreader.close();
    }
}
