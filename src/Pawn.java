public class Pawn extends Piece{

    private boolean moved = false;
    protected Pawn(boolean color, int xC, int yC) {
        super(color, 'P', xC, yC);
    }

    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) {
        boolean isXToCoordinateBigger = (xTo - xCoordinate > 0);
        if ((isXToCoordinateBigger || (xTo - xCoordinate == 0)) && !color) return false;
        if (!isXToCoordinateBigger && color) return false;
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)){
            return (Math.abs(xTo - xCoordinate) == 1 && Math.abs(yTo - yCoordinate) == 1);
        }
        return false;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}
