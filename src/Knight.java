public class Knight extends Piece{

    public Knight(boolean color,int x,int y){
        super(color,'N',x,y);

    }
    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) {
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)){
            int temp = Math.abs(xTo - xCoordinate);
            int temp1 = Math.abs(yTo - yCoordinate);
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
