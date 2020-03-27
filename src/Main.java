import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Integer> arrayList = new ArrayList<Integer>(Const.map_row * Const.map_col);
        for(int i = 0; i < Const.map_row * Const.map_col; i++){
            if(scan.hasNextInt()){
                arrayList.add(scan.nextInt());
            }
            else{
                System.out.println("please enter right number:");
                return;
            }
        }
        State root = new State(arrayList);
        Queue<State> stateQueue = new LinkedList<>();
        HashSet<State> stateSet = new HashSet<>();
        stateQueue.offer(root);
        stateSet.add(root);
        while(!stateQueue.isEmpty()){
            State parent = stateQueue.poll();
            ArrayList<int[][]> mapList = parent.move();
            for(int[][] map : mapList){
                State child = new State(map);
                if(!stateSet.add(child)){
                    continue;
                }
                child.parent = parent;
                if(child.isSuccess()){
                    ArrayList<State> result = new ArrayList<>();
                    State tmp = child;
                    while(tmp != null){
                        result.add(tmp);
                        tmp = tmp.parent;
                    }
                    for(int i = result.size() - 1; i >= 0; i--){
                        result.get(i).print();
                    }
                    System.out.println(result.size());
                    return;
                }
                else{
                    stateQueue.offer(child);
                }
            }
        }


    }
}
