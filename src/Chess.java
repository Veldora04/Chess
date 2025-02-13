import java.util.*;

public class Chess {
    //todo!!
    /*
    * stalemate
    * En passant
    * 50 move rule
    * better input-system
    * visuals
    * */
    private final static boolean Black_Color = false;
    private final static boolean White_Color = true;
    private final static String White_To_Move = "White to move: ";
    private final static String Black_To_Move = "Black to move: ";
    /**private final static String Black_Rook = " ♖";      //Later for visualisation myb
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
    private final static String Empty = " □";**/
    private final static char EMPTY = 'X';
    private final static int asciiVal_a = 97;
    private Piece[][] board = new Piece[8][8];
    private ArrayList<Piece> currentPieces = new ArrayList<>();
    private int zug;
    Scanner scanner = new Scanner(System.in);
    private ArrayList<String>[] notation = new ArrayList[2];

    public Chess() {
        zug = 1;
        assemble(board);
        notation[0] = new ArrayList<>();
        notation[1] = new ArrayList<>();
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
        updatePiecesList();
        while(!isGameOver()) {
            boolean whoTurn = zug % 2 != 0;
            if (isStalemate(whoTurn)){
                System.out.println("Stalemate by " + !whoTurn);
                endPrint(whoTurn);
                return;
            }
            System.out.println((whoTurn ? White_To_Move : Black_To_Move));
            if (whoTurn) {
                printBoardWhite();
            } else {
                printBoardBlack();
            }
            move(whoTurn);
            updatePiecesList();
            zug++; // so move changes
        }
        System.out.println("Game over!!!");
        if (mate(White_Color)) {
            System.out.println("Black won congratulation");
            endPrint(false);
        }
        else {
            System.out.println("White won congratulation");
            endPrint(true);
        }
    }
    private void endPrint(boolean color){
        if (color) {
            for (int j = 0; j < board.length; j++){
                System.out.print(8 - j);
                for (int k = 0; k < board.length; k++){
                    if (board[j][k] != null) System.out.print("   " + board[j][k].name);
                    else System.out.print("   " + EMPTY);
                }
                System.out.println();
            }
            System.out.println("    a   b   c   d   e   f   g   h");
            System.out.println("White:  Black:");
            for (int i = 0; i < notation[0].size(); i++){
                if (i == notation[0].size() - 1) {
                    System.out.println(notation[0].get(i) + "#"); // # indicates end of match
                    break;
                }
                System.out.println(notation[0].get(i) + "\t" + notation[1].get(i));
            }
        }else {
            for(int i = board.length - 1; i >= 0; i--){
                System.out.print(8 - i);
                for (int j = board.length - 1; j >= 0; j--){
                    if (board[i][j] != null) System.out.print("   " + board[i][j].name);
                    else System.out.print("   " + EMPTY);
                }
                System.out.println();
            }
            System.out.println("    h   g   f   e   d   c   b   a");
            System.out.println("White:  Black:");
            for (int i = 0; i < notation[0].size(); i++){
                System.out.println(notation[0].get(i) + "\t" + notation[1].get(i) + (i == notation[0].size() - 1 ? "#" : ""));
            }
        }
    }
    private boolean isStalemate(boolean color){
        if (isKingInCheck(color) || canKingMove(color)) return false;
        for (Piece piece : currentPieces){
            // checks if any movement is possible
            if (piece instanceof Rook || piece instanceof Queen){
                if ((inBounds(piece.xCoordinate-1, piece.yCoordinate) && (board[piece.xCoordinate-1][piece.yCoordinate]==null || board[piece.xCoordinate-1][piece.yCoordinate].color != color))) return false;
                if ((inBounds(piece.xCoordinate+1, piece.yCoordinate) && (board[piece.xCoordinate+1][piece.yCoordinate]==null || board[piece.xCoordinate+1][piece.yCoordinate].color != color))) return false;
                if ((inBounds(piece.xCoordinate, piece.yCoordinate-1) && (board[piece.xCoordinate][piece.yCoordinate-1]==null || board[piece.xCoordinate][piece.yCoordinate-1].color != color))) return false;
                if ((inBounds(piece.xCoordinate, piece.yCoordinate+1) && (board[piece.xCoordinate][piece.yCoordinate+1]==null || board[piece.xCoordinate][piece.yCoordinate+1].color != color))) return false;
            }
            if (piece instanceof Bishop || piece instanceof Queen){
                if ((inBounds(piece.xCoordinate-1, piece.yCoordinate-1) && (board[piece.xCoordinate-1][piece.yCoordinate-1]==null || board[piece.xCoordinate-1][piece.yCoordinate-1].color != color))) return false;
                if ((inBounds(piece.xCoordinate+1, piece.yCoordinate+1) && (board[piece.xCoordinate+1][piece.yCoordinate+1]==null || board[piece.xCoordinate+1][piece.yCoordinate+1].color != color))) return false;
                if ((inBounds(piece.xCoordinate+1, piece.yCoordinate-1) && (board[piece.xCoordinate+1][piece.yCoordinate-1]==null || board[piece.xCoordinate+1][piece.yCoordinate-1].color != color))) return false;
                if ((inBounds(piece.xCoordinate-1, piece.yCoordinate+1) && (board[piece.xCoordinate-1][piece.yCoordinate+1]==null || board[piece.xCoordinate-1][piece.yCoordinate+1].color != color))) return false;
            }
            if (piece instanceof Pawn) { /// different for black and white
                if (inBounds(piece.xCoordinate + (color ? 1 : (-1)), piece.yCoordinate) && board[piece.xCoordinate + (color ? 1 : (-1))][piece.yCoordinate] == null) return false;
                if (inBounds(piece.xCoordinate + (color ? 1 : (-1)), piece.yCoordinate + 1) && board[piece.xCoordinate + (color ? 1 : (-1))][piece.yCoordinate + 1] != null && board[piece.xCoordinate + (color ? 1 : (-1))][piece.yCoordinate + 1].color != color) return false;
                if (inBounds(piece.xCoordinate + (color ? 1 : (-1)), piece.yCoordinate - 1) && board[piece.xCoordinate + (color ? 1 : (-1))][piece.yCoordinate - 1] != null && board[piece.xCoordinate + (color ? 1 : (-1))][piece.yCoordinate - 1].color != color) return false;
            }
            if (piece instanceof Knight) {
                if ((inBounds(piece.xCoordinate+2, piece.yCoordinate+1) && (board[piece.xCoordinate+2][piece.yCoordinate+1]==null || board[piece.xCoordinate+2][piece.yCoordinate+1].color != color))) return false;
                if ((inBounds(piece.xCoordinate+2, piece.yCoordinate-1) && (board[piece.xCoordinate+2][piece.yCoordinate-1]==null || board[piece.xCoordinate+2][piece.yCoordinate-1].color != color))) return false;
                if ((inBounds(piece.xCoordinate+1, piece.yCoordinate+2) && (board[piece.xCoordinate+1][piece.yCoordinate+2]==null || board[piece.xCoordinate+1][piece.yCoordinate+2].color != color))) return false;
                if ((inBounds(piece.xCoordinate+1, piece.yCoordinate-2) && (board[piece.xCoordinate+1][piece.yCoordinate-2]==null || board[piece.xCoordinate+1][piece.yCoordinate-2].color != color))) return false;
                if ((inBounds(piece.xCoordinate-2, piece.yCoordinate+1) && (board[piece.xCoordinate-2][piece.yCoordinate+1]==null || board[piece.xCoordinate-2][piece.yCoordinate+1].color != color))) return false;
                if ((inBounds(piece.xCoordinate-2, piece.yCoordinate-1) && (board[piece.xCoordinate-2][piece.yCoordinate-1]==null || board[piece.xCoordinate-2][piece.yCoordinate-1].color != color))) return false;
                if ((inBounds(piece.xCoordinate-1, piece.yCoordinate+2) && (board[piece.xCoordinate-1][piece.yCoordinate+2]==null || board[piece.xCoordinate-1][piece.yCoordinate+2].color != color))) return false;
                if ((inBounds(piece.xCoordinate-1, piece.yCoordinate-2) && (board[piece.xCoordinate-1][piece.yCoordinate-2]==null || board[piece.xCoordinate-1][piece.yCoordinate-2].color != color))) return false;
            }
        }
        return true;
    }
    public boolean inBounds(int x,int y){
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
    public void printBoardWhite(){
        for (int j = 0; j < board.length; j++){
            System.out.print(8 - j);
            for (int k = 0; k < board.length; k++){
                if (board[j][k] != null) System.out.print("   " + board[j][k].name);
                else System.out.print("   " + EMPTY);
            }
            System.out.print("\t\t");
            printNotations(j);
        }
        System.out.println("    a   b   c   d   e   f   g   h");
    }
    private void printNotations(int i){
        if (zug == 1) { // else NullPointException no notations
            System.out.println();
            return;
        }
        int j = notation[0].size() - 8;
        // notations[0] always the bigger one
        if (j <= 0 && i < notation[0].size()){
            System.out.print(notation[0].get(i));
            if (i >= notation[1].size()) { // because black is one move "behind"
                System.out.println();
                return;
            }
            System.out.print(" " + notation[1].get(i));
        }else if (j > 0){
            System.out.print(notation[0].get(i + j));
            if ((i + j) >= notation[1].size()){
                System.out.println();
                return;
            }
            System.out.print(" " + notation[1].get(i + j));
        }
        System.out.println();
    }
    public void printBoardBlack(){
        for(int i = board.length - 1; i >= 0; i--){
            System.out.print(8 - i);
            for (int j = board.length - 1; j >= 0; j--){
                if (board[i][j] != null) System.out.print("   " + board[i][j].name);
                else System.out.print("   " + EMPTY);
            }
            System.out.print("\t\t");
            printNotations(7 - i);
        }
        System.out.println("    h   g   f   e   d   c   b   a");
    }
    public void move(boolean color){
        System.out.println("Please type in your Move: " + "            (e.g. e2e4 to move piece form e2 to e4, to castle enter castlelong or castleshort)");
        String input = scanner.next().toLowerCase();
        int turnNumber = (zug%2 == 0 ? zug/2:zug/2 +1);
        if (input.equals("castlelong") && castleAllowed(color,false)){
            String temp = turnNumber + "O-O-O";
            if (color){
                notation[0].add(temp);
                board[7][2] = board[7][4];
                board[7][2].moved = true;
                board[7][4] = null;
                board[7][2].xCoordinate = 7;
                board[7][2].yCoordinate = 2;
                board[7][3] = board[7][0];
                board[7][0] = null;
                board[7][3].moved = true;
                board[7][3].xCoordinate = 7;
                board[7][3].yCoordinate = 3;
            }else {
                notation[1].add(temp);
                board[0][2] = board[0][4];
                board[0][2].moved = true;
                board[0][4] = null;
                board[0][2].xCoordinate = 0;
                board[0][2].yCoordinate = 2;
                board[0][3] = board[7][0];
                board[0][0] = null;
                board[0][3].moved = true;
                board[0][3].xCoordinate = 0;
                board[0][3].yCoordinate = 3;
            }
            return;
        }
        if (input.equals("castleshort") && castleAllowed(color,true)){
            String temp = turnNumber + "O-O";
            if (color){
                notation[0].add(temp);
                board[7][6] = board[7][4];
                board[7][6].moved = true;
                board[7][4] = null;
                board[7][6].xCoordinate = 7;
                board[7][6].yCoordinate = 6;
                board[7][5] = board[7][7];
                board[7][5].moved = true;
                board[7][5].xCoordinate = 7;
                board[7][5].yCoordinate = 5;
                board[7][7] = null;
            }else{
                notation[1].add(temp);
                board[0][6] = board[0][4];
                board[0][6].moved = true;
                board[0][4] = null;
                board[0][6].xCoordinate = 0;
                board[0][6].yCoordinate = 6;
                board[0][5] = board[0][7];
                board[0][5].moved = true;
                board[0][5].xCoordinate = 0;
                board[0][5].yCoordinate = 5;
                board[0][7] = null;
            }
            return;
        }
        if (input.length() != 4){ // input too long
            System.out.println("Input was incorrect!!!\nTry again");
            move(color);
            return;
        }
        //separating the input
        String s1 = input.substring(0,1);
        String s2 = input.substring(1,2);
        String s3 = input.substring(2,3);
        String s4 = input.substring(3,4);
        int firstY = StringToIntWeilAsciiScheisseIst(s1);
        int firstX = StringToIntWeilAsciiScheisseIst(s2);
        int secondY = StringToIntWeilAsciiScheisseIst(s3);
        int secondX = StringToIntWeilAsciiScheisseIst(s4);
        if (firstX == -1 || firstY == -1 || secondY == -1 || secondX == -1){ //invalid input
            System.out.println("Input was incorrect!!!\nTry again");
            move(color);
            return;
        }
        //checks if piece is yours or if goal-square is occupied by your piece
        if ((board[firstX][firstY] ==  null || board[firstX][firstY].color != color) || (board[secondX][secondY] != null && board[secondX][secondY].color == color)){
            System.out.println("Not your piece or no piece on coordinate or can't take own piece!\nTry again!");
            move(color);
            return;
        }
        //special treatment for pawns
        if (board[firstX][firstY] instanceof Pawn){
            if (!((Pawn) board[firstX][firstY]).checkIfValidMove(secondX,secondY) && !board[firstX][firstY].checkIfValidMovetoTake(secondX,secondY)){
                System.out.println("Either no piece in given coordinate or piece can't move there!\nTry again");
                move(color);
                return;
            }
        }
        else if (!board[firstX][firstY].checkIfValidMovetoTake(secondX,secondY)){
            System.out.println("Either no piece in given coordinate or piece can't move there!\nTry again");
            move(color);
            return;
        }
        Piece temp = board[secondX][secondY];
        board[secondX][secondY] = board[firstX][firstY];
        board[firstX][firstY] = null;
        if (isKingInCheck(color)) { // case if piece was pinned
            board[firstX][firstY] = board[secondX][secondY];
            board[secondX][secondY] = temp;
            System.out.println("Can't move this piece!\nTry again!");
            move(color);
            return;
        }
        board[secondX][secondY].moved = true;
        board[secondX][secondY].xCoordinate = secondX;
        board[secondX][secondY].yCoordinate = secondY;
        addNotation(board[secondX][secondY],(temp != null),s4,color);
        if (board[secondX][secondY] instanceof Pawn && (secondX == 7 || secondX == 0)){
            System.out.println("Enter piece name to what you what to promote to:             (N for Knight,B for Bishop,Q for Queen,R for Rook");
            promote(secondX,secondY);
        }
    }
    private void addNotation(Piece p1,boolean wasPieceTaken,String stringFromXGoal,boolean color){
        int turnNumber = (zug%2 == 0 ? zug/2:zug/2 +1);
        String res;
        if (!wasPieceTaken){
            res = turnNumber + "." + notationHelper(p1) + (char) (p1.yCoordinate + asciiVal_a);
        }else {
            res = turnNumber + "." + notationHelper(p1) + "x" + (char) (p1.yCoordinate + asciiVal_a);
        }
        if (color){
            notation[0].add(res + (stringFromXGoal));
        }else{
            notation[1].add(res + (stringFromXGoal));
        }
    }
    private String notationHelper(Piece piece){
        if (piece instanceof Bishop) return "B";
        if (piece instanceof Rook) return "R";
        if (piece instanceof Knight) return "N";
        if (piece instanceof Queen) return "Q";
        if (piece instanceof King) return "K";
        return "";
    }
    private void promote(int x,int y){ ///moved = true is unnecessary because of how castle is implemented
        String promote = scanner.next();
        switch (promote.toUpperCase()){
            case "N":{
                board[x][y] = new Knight(board[x][y].color,board[x][y].xCoordinate,board[x][y].yCoordinate);
                break;
            }
            case "B":{
                board[x][y] = new Bishop(board[x][y].color,board[x][y].xCoordinate,board[x][y].yCoordinate);
                break;
            }
            case "Q":{
                board[x][y] = new Queen(board[x][y].color,board[x][y].xCoordinate,board[x][y].yCoordinate);
                break;
            }
            case "R":{
                board[x][y] = new Rook(board[x][y].color,board[x][y].xCoordinate,board[x][y].yCoordinate);
                break;
            }
            default:{
                System.out.println("Input incorrect!\nPlease look at instructions and try again!!");
                promote(x,y);
                break;
            }
        }
    }
    private boolean castleAllowed(boolean color,boolean typeOfCastle){// true == short
        if (typeOfCastle && color && !board[7][7].moved && !board[7][4].moved && board[7][5] == null && board[7][6] == null){
            return true;
        } else if (!typeOfCastle && color && !board[7][0].moved && !board[7][4].moved && board[7][1] == null && board[7][2] == null && board[7][3] == null) {
            return true;
        } else if (!typeOfCastle && !color && !board[0][0].moved && !board[0][4].moved && board[0][1] == null && board[0][2] == null && board[0][3] == null) {
            return true;
        } else if (typeOfCastle && !color && !board[0][7].moved && !board[0][4].moved && board[0][5] == null && board[0][6] == null) {
            return true;
        }
        return false;
    }
    private int StringToIntWeilAsciiScheisseIst(String s){
        return switch (s) {
            case "a","8" -> 0; /// index Vals
            case "b","7" -> 1;
            case "c","6" -> 2;
            case "d","5" -> 3;
            case "e","4" -> 4;
            case "f","3" -> 5;
            case "g","2" -> 6;
            case "h","1" -> 7;
            default -> -1;
        };
    }
    public boolean isGameOver(){
        return mate(White_Color) || mate(Black_Color);
    }
    public boolean mate(boolean colorKing){
        return isKingInCheck(colorKing) && !canKingMove(colorKing);
    }
    public boolean canKingMove(boolean kingColor){
        int x = 0;
        int y = 0;
        for (Piece piece : currentPieces){
            if (piece instanceof King && piece.color == kingColor){
                x = piece.xCoordinate;
                y = piece.yCoordinate;
                break;
            }
        }
        /// 8 if-statements for the 8 different squares a King can go to
        if (inBounds(x-1,y-1) && isSquareInDanger(!kingColor,x-1,y-1) && isEmpty(x-1,y-1)) return true;
        if (inBounds(x-1,y) && isSquareInDanger(!kingColor,x-1,y) && isEmpty(x-1,y)) return true;
        if (inBounds(x-1,y+1) && isSquareInDanger(!kingColor,x-1,y+1) && isEmpty(x-1,y+1)) return true;
        if (inBounds(x,y-1) && isSquareInDanger(!kingColor,x,y-1) && isEmpty(x,y-1)) return true;
        if (inBounds(x,y+1) && isSquareInDanger(!kingColor,x,y+1) && isEmpty(x,y+1)) return true;
        if (inBounds(x+1,y-1) && isSquareInDanger(!kingColor,x+1,y-1) && isEmpty(x+1,y-1)) return true;
        if (inBounds(x+1,y) && isSquareInDanger(!kingColor,x+1,y) && isEmpty(x+1,y)) return true;
        if (inBounds(x+1,y+1) && isSquareInDanger(!kingColor,x+1,y+1) && isEmpty(x+1,y+1)) return true;
        return false;
    }
    public boolean isEmpty(int x,int y){
        return board[x][y] == null;
    }
    public boolean isKingInCheck(boolean KingColor){
        int x;
        int y;
        for (Piece piece : currentPieces){
            if (piece instanceof King && piece.color == KingColor){
                x = piece.xCoordinate;
                y = piece.yCoordinate;
                return isSquareInDanger(KingColor,x,y);
            }
        }
        return false;
    }
    public boolean isSquareInDanger(boolean color,int x,int y){
        int res = 0;
        for (Piece p : currentPieces){
            if (p.checkIfValidMovetoTake(x,y) && p.color != color){
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
        if (!(piece.xCoordinate == x || piece.yCoordinate == y)) return 0; // edge case because of queen
        for (Piece p : currentPieces){
            if (x == piece.xCoordinate && x == p.xCoordinate && numberIsBetweenNumbers(p.yCoordinate,y,piece.yCoordinate)) return 0;
            if (y == piece.yCoordinate && y == p.yCoordinate && numberIsBetweenNumbers(p.xCoordinate,x,piece.xCoordinate)) return 0;
        }
        return 1;
    }
    private int checkDiagonals(int x,int y,Piece piece){
        if ((piece.xCoordinate == x || piece.yCoordinate == y)) return 0; // edge case because of queen
        int dx = piece.xCoordinate > x ? 1 : -1; //Direction along x
        int dy = piece.yCoordinate > y ? 1 : -1; //Direction along y

        int currentX = x + dx; //Start checking from next diagonal square
        int currentY = y + dy;

        while (currentX != piece.xCoordinate && currentY != piece.yCoordinate && inBounds(currentX,currentY)) {
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
    public static void main(String[] args) {
        Chess c = new Chess();
        c.play();
    }
}
