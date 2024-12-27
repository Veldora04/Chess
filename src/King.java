public class King extends Piece{

    private boolean moved = false;
    protected King(boolean color, int xC, int yC) {
        super(color, 'K', xC, yC);
    }

    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) {
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)) return Math.abs(xCoordinate-xTo) <= 1 && Math.abs(yCoordinate-yTo) <= 1;//todo
        return false;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}
