import java.util.ArrayList;

public class State {
    public State parent;
    public ArrayList<State> next;
    public int[][] map;

    public State(ArrayList<Integer> arrayList){
        int[][] tmpMap = new int[Const.map_row][Const.map_col];
        for(int i = 0; i < Const.map_row; i++){
            for(int j = 0; j < Const.map_col; j++){
                tmpMap[i][j] = arrayList.get(i * Const.map_col + j);
            }
        }
        this.map = tmpMap;
    }
    public State(int[][] tmpMap){
        this.map = tmpMap;
    }
    //用1代表鱼
    public boolean isSuccess(){
        for(int i = Const.map_col - 1; i >= 0; i--){
            if(map[Const.exit_row][i] != 1 && map[Const.exit_row][i] != 0) {
                return false;
            }
            else if(map[Const.exit_row][i] == 1){
                return true;
            }
        }
        return true;
    }

    public void print(){
        for(int i = 0; i < Const.map_row; i++){
            for(int j = 0; j < Const.map_col; j++){
                System.out.printf("%-3d", map[i][j]);
                System.out.print(" ");
            }
            System.out.println(" ");
        }
        System.out.println("_________________________________");
    }

    public ArrayList<int[][]> move(){
        ArrayList<int[][]> mapList = new ArrayList<>();
        for(int i = 0; i < Const.map_row; i++){
            for(int j = 0; j < Const.map_col; j++){
                if(map[i][j] == 0){
                    //从四个方向看看能不能移动到这个位置
                    int[][] tmpMap1 = leftMove(i,j);
                    if(tmpMap1 != null)
                        mapList.add(tmpMap1);
                    int[][] tmpMap2 = rightMove(i,j);
                    if(tmpMap2 != null)
                        mapList.add(tmpMap2);
                    int[][] tmpMap3 = downMove(i,j);
                    if(tmpMap3 != null)
                        mapList.add(tmpMap3);
                    int[][] tmpMap4 = upMove(i,j);
                    if(tmpMap4 != null)
                        mapList.add(tmpMap4);
                }
            }
        }
        return mapList;
    }

    @Override
    public int hashCode() {
        int code = 0;
        int index = 1;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                code = this.map[i][j] * index;
                index++;
            }
        }
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        State tmp = (State) obj;
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(this.map[i][j] != tmp.map[i][j]){
                    return false;
                }
            }
        }
        return true;
    }

    private int[][] deepCopyMap(){
        int[][] tmpMap = new int[Const.map_row][Const.map_col];
        for(int i = 0; i < Const.map_row; i++){
            System.arraycopy(map[i], 0, tmpMap[i], 0, Const.map_col);
        }
        return tmpMap;
    }
    //---->
    public int[][] leftMove(int i, int j){
        if(j < 2)
            return null;
        //判断左边是否有鱼
        int count = 0;
        int fishId = 0;
        int index = 0;
        for(int k = j - 1; k >= 0; k--){
            if(map[i][k] != 0 && count == 0){
                fishId = map[i][k];
                count++;
                index = k;
            }
            else if(map[i][k] != 0 && count != 0){
                if(fishId != map[i][k] && count == 1){
                    return null;
                }
                else if(fishId != map[i][k] && count >= 2){
                    break;
                }
                else{
                    count++;
                    index--;
                }
            }
        }
        if(count < 2)
            return null;
        int[][] tmpMap = deepCopyMap();
        for(int k = 0; k < count; k++){
            tmpMap[i][index + k] = 0;
        }
        for(int k = 0; k < count; k++){
            tmpMap[i][j - k] = fishId;
        }
        return tmpMap;
    }
    //<-----
    public int[][] rightMove(int i, int j){
        if(j > map[i].length - 3)
            return null;
        //判断右边是否有鱼
        int count = 0;
        int fishId = 0;
        int index = 0;
        for(int k = j + 1; k < map[i].length; k++){
            if(map[i][k] != 0 && count == 0){
                fishId = map[i][k];
                count++;
                index = k;
            }
            else if(map[i][k] != 0 && count != 0){
                if(fishId != map[i][k] && count == 1){
                    return null;
                }
                else if(fishId != map[i][k] && count >= 2){
                    break;
                }
                else{
                    count++;
                    index++;
                }
            }
        }
        if(count < 2)
            return null;
        int[][] tmpMap = deepCopyMap();
        for(int k = 0; k < count; k++){
            tmpMap[i][index - k] = 0;
        }
        for(int k = 0; k < count; k++){
            tmpMap[i][j + k] = fishId;
        }
        return tmpMap;
    }
    //  |
    //  |
    //  |
    //  v
    public int[][] downMove(int i, int j){
        if(i < 2)
            return null;
        //判断上边是否有鱼
        int count = 0;
        int fishId = 0;
        int index = 0;
        for(int k = i - 1; k >= 0; k--){
            if(map[k][j] != 0 && count == 0){
                fishId = map[k][j];
                count++;
                index = k;
            }
            else if(map[k][j] != 0 && count != 0){
                if(fishId != map[k][j] && count == 1){
                    return null;
                }
                else if(fishId != map[k][j] && count >= 2){
                    break;
                }
                else{
                    count++;
                    index--;
                }
            }
        }

        if(count < 2)
            return null;

        int[][] tmpMap = deepCopyMap();
        for(int k = 0; k < count; k++){
            tmpMap[index + k][j] = 0;
        }
        for(int k = 0; k < count; k++){
            tmpMap[i - k][j] = fishId;
        }
        return tmpMap;
    }
    //  ^
    //  |
    //  |
    //  |
    public int[][] upMove(int i, int j){
        if(i > map.length - 3)
            return null;
        //判断下面是否有鱼
        int count = 0;
        int fishId = 0;
        int index = 0;
        for(int k = i + 1; k < map.length; k++){
            if(map[k][j] != 0 && count == 0){
                fishId = map[k][j];
                count++;
                index = k;
            }
            else if(map[k][j] != 0 && count != 0){
                if(fishId != map[k][j] && count == 1){
                    return null;
                }
                else if(fishId != map[k][j] && count >= 2){
                    break;
                }
                else{
                    count++;
                    index++;
                }
            }
        }
        if(count < 2)
            return null;
        int[][] tmpMap = deepCopyMap();
        for(int k = 0; k < count; k++){
            tmpMap[index - k][j] = 0;
        }
        for(int k = 0; k < count; k++){
            tmpMap[i + k][j] = fishId;
        }
        return tmpMap;
    }
}
