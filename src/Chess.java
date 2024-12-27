import java.util.ArrayList;

public class Chess {
    private final static boolean Black_Color = false;
    private final static boolean White_Color = true;
    private final static String White_To_Move = "White to move: ";
    private final static String Black_To_Move = "Black to move: ";
    private final static String Black_Rook = " ♖";      //Later for visualisation
    private final static String Black_Knight = " ♘";
    private final static String Black_Bishop = " ♗";
    private final static String Black_Queen = " ♕";
    private final static String Black_King = " ♔";
    private final static String Black_Pawn = " ♙";
    private final static String White_Rook = " ♜";
    private final static String White_Knight = " ♞";
    private final static String White_Bishop = " ♝";
    private final static String White_Queen = " ♛";
    private final static String White_King = " ♚";
    private final static String White_Pawn = " ♟";
    private final static String Empty = " □";
    private final static char EMPTY = 'x';
    private Piece[][] board = new Piece[8][8];
    private ArrayList<Piece> currentPieces = new ArrayList<>();
    private int zug;
//    private ArrayList<String>[] notation = new ArrayList[2]; do later

    public Chess() {
        zug = 1;
        assemble(board);
    }
    private static void assemble(Piece[][] b){ //Starting board
        for (int i = 0; i < b.length;i++){
            ///black pawns
            b[1][i] = new Pawn(false,1,i);
            ///white pawns
            b[6][i] = new Pawn(true,6,i);
        }
        ///black pieces
        b[0][0] = new Rook(Black_Color,0,0);
        b[0][7] = new Rook(Black_Color,0,7);
        b[0][1] = new Knight(Black_Color,0,1);
        b[0][6] = new Knight(Black_Color,0,6);
        b[0][2] = new Bishop(Black_Color,0,2);
        b[0][5] = new Bishop(Black_Color,0,5);
        b[0][3] = new Queen(Black_Color,0,3);
        b[0][4] = new King(Black_Color,0,4);
        ///white pieces
        b[7][0] = new Rook(White_Color,7,0);
        b[7][7] = new Rook(White_Color,7,7);
        b[7][1] = new Knight(White_Color,7,1);
        b[7][6] = new Knight(White_Color,7,6);
        b[7][2] = new Bishop(White_Color,7,2);
        b[7][5] = new Bishop(White_Color,7,5);
        b[7][3] = new Queen(White_Color,7,3);
        b[7][4] = new King(White_Color,7,4);
    }
    public void play(){
        while(!isGameOver())
            System.out.println((zug%2!=0 ? White_To_Move : Black_To_Move));
    }
    public boolean isGameOver(){
        return false;
    }
    public boolean canKingMove(){
        return false;
    }
    public boolean isKingInCheck(boolean KingColor){
        int x = 0;
        int y = 0;
        for (Piece[] pieces : board){
            for (Piece piece : pieces){
                if (piece instanceof King && piece.color == KingColor){
                    x = piece.xCoordinate;
                    y = piece.yCoordinate;
                }
            }
        }
        return isSquareInDanger(KingColor,x,y);
    }
    public boolean isSquareInDanger(boolean color,int x,int y){
        int res = 0;
        for (Piece p : currentPieces){
            if (p.checkIfValidMovetoTake(x,y)){
                if (p instanceof Rook || p instanceof Queen){
                    res += checkRows(x,y,p);
                }
                if (p instanceof Bishop || p instanceof Queen){
                    res += checkDiagonals(x,y,p);
                }
                if (p instanceof Pawn || p instanceof Knight) return true; ///if is valid move to take it is there always possible
            }
        }
        return res != 0;
    }
    private int checkRows(int x,int y,Piece piece){
        for (Piece p : currentPieces){
            if (x == piece.xCoordinate && x == p.xCoordinate && numberIsBetweenNumbers(p.yCoordinate,y,piece.yCoordinate)) return 1;
            if (y == piece.yCoordinate && y == p.yCoordinate && numberIsBetweenNumbers(p.xCoordinate,x,piece.xCoordinate)) return 1;
        }
        return 0;
    }
    private int checkDiagonals(int x,int y,Piece piece){
        int dx = piece.xCoordinate > x ? 1 : -1; //Direction along x
        int dy = piece.yCoordinate > y ? 1 : -1; //Direction along y

        int currentX = x + dx; //Start checking from next diagonal square
        int currentY = y + dy;

        while (currentX != piece.xCoordinate && currentY != piece.yCoordinate) {
            for (Piece p : currentPieces) {
                if (p.xCoordinate == currentX && p.yCoordinate == currentY) {
                    return 0; //Blocked
                }
            }
            currentX += dx;
            currentY += dy;
        }
        return 1; //Threat
    }
    private boolean numberIsBetweenNumbers(int toCheck,int from,int to){
        return Math.min(from,to) < toCheck && Math.max(from,to) > toCheck;
    }
    private void updatePiecesList(){ //update everytime turn is over
        currentPieces.clear();
        for (Piece[] p : board){
            for (Piece p1 : p){
                if (p1 != null) currentPieces.add(p1);
            }
        }
    }
}
