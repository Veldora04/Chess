public class Rook extends Piece{
    private boolean moved = false;

    public Rook(boolean color,int x,int y) {
        super(color, 'R',x,y);
    }

    @Override
    public boolean checkIfValidMovetoTake(int xTo, int yTo) {
        if (validCoordinates(xCoordinate,yCoordinate,xTo,yTo)) return xCoordinate == xTo || yCoordinate == yTo;
        return false;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}
