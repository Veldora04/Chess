public abstract class Piece {
    protected final boolean color; // true == white
    protected final char name;
    protected int xCoordinate; // indexValues(0-7) not (1-8)
    protected int yCoordinate;
    protected boolean moved;

    protected Piece(boolean color,char name,int xC,int yC){
        this.color = color;
        this.name = name;
        xCoordinate = xC;
        yCoordinate = yC;
        moved = false;
    }
    public abstract boolean checkIfValidMovetoTake(int xTo,int yTo);
    public boolean validCoordinates(int xFrom,int yFrom,int xTo,int yTo){
        return isbetween0And7(xFrom,yFrom) && isbetween0And7(xTo,yTo);
    }
    private boolean isbetween0And7(int x,int y){
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
