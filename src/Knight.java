public class Knight extends Piece{

    public Knight(boolean color,int x,int y){
        super(color,'N',x,y);
    }
    @Override
    public boolean checkIfValidMove(int xFrom, int yFrom, int xTo, int yTo) {
        if (validCoordinates(xFrom,yFrom,xTo,yTo)){
            int temp = Math.abs(xTo - xFrom);
            int temp1 = Math.abs(yTo - yFrom);
            if (temp < 3 && temp > 0){
                switch (temp){
                    case 1: return temp1 == 2;
                    case 2: return temp1 == 1;
                }
            } else return false;
        }
        return false;
    }
}
