public class Rook extends Piece{
    private boolean moved = false;

    public Rook(boolean color,int x,int y) {
        super(color, 'R',x,y);
    }

    @Override
    public boolean checkIfValidMove(int xFrom, int yFrom, int xTo, int yTo) {
        if (validCoordinates(xFrom,yFrom,xTo,yTo)) return xFrom == xTo || yFrom == yTo;
        return false;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
