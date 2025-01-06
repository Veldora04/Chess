public class Queen extends Piece{

    protected Queen(boolean color, int xC, int yC) {
        super(color, 'Q', xC, yC);
    }

    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) { // valid if Rook or Bishop move
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)) return xCoordinate == xTo || yCoordinate == yTo || (Math.abs((yCoordinate - yTo)/(xCoordinate - xTo)) == 1);
        return false;
    }

}
