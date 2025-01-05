public class Bishop extends Piece{

    protected Bishop(boolean color, int xC, int yC) {
        super(color, 'B', xC, yC);
    }

    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) {
        if (xCoordinate == xTo) return false;
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)) return Math.abs((yCoordinate - yTo)/(xCoordinate - xTo)) == 1;
        return false;
    }

}
