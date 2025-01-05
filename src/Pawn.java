public class Pawn extends Piece{

    protected Pawn(boolean color, int xC, int yC) {
        super(color, 'P', xC, yC);
    }
    ///class needs to methods because pawns move and taking differ
    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) {
        boolean isXToCoordinateBigger = (xTo - xCoordinate > 0);
        if ((isXToCoordinateBigger || (xTo - xCoordinate == 0)) && color) return false;
        if (!isXToCoordinateBigger && !color) return false;
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)){
            return (Math.abs(xTo - xCoordinate) == 1 && Math.abs(yTo - yCoordinate) == 1);
        }
        return false;
    }
    public boolean checkIfValidMove(int x,int y){
        if(!moved && color) return yCoordinate == y && (xCoordinate - x > 0 && xCoordinate - x < 3);
        if(!moved) return yCoordinate == y && (x - xCoordinate > 0 && x - xCoordinate < 3);
        if (color) return yCoordinate == y && xCoordinate - x == 1;
        else return yCoordinate == y && xCoordinate - x == -1;
    }

}
