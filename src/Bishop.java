public class Bishop extends Piece{

    protected Bishop(boolean color, char name, int xC, int yC) {
        super(color, name, xC, yC);
    }

    @Override
    public boolean checkIfValidMove(int xFrom, int yFrom, int xTo, int yTo) {
        if (validCoordinates(xFrom,yFrom,xTo,yTo)) return Math.abs((yFrom - yTo)/(xFrom - xTo)) == 1;
        return false;
    }
}
