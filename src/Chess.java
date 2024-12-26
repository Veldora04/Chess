public class Chess {
    private final static String White_To_Move = "White to move: ";
    private final static String Black_To_Move = "Black to move: ";
    private final static String Black_Rook = " ♖";
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
    private final static String First_Line = "  a  b c  d  e  f g  h";
    private String[][] board = new String[8][9];
//    private ArrayList<String>[] notation = new ArrayList[2]; später machen
    private boolean whiteToMove = true;

    public Chess() {
        assemble(board);
    }

    public static void assemble(String[][] b){
        System.out.println(First_Line);
        for (int i = 0; i < b.length; i++){
            b[i][0] = String.valueOf(i);
        }
        for (int i = 1; i < b[0].length; i++){
            b[1][i] = Black_Pawn;
            b[6][i] = White_Pawn;
        }
        for (int i = 2; i < b.length - 2;i++){
            for (int j = 1; j < b[0].length; j++){
                b[i][j] = Empty;
            }
        }
        b[0][1] = b[0][8] = Black_Rook;
        b[0][2] = b[0][7] = Black_Knight;
        b[0][3] = b[0][6] = Black_Bishop;
        b[0][4] = Black_Queen;
        b[0][5] = Black_King;
        b[7][1] = b[7][8] = White_Rook;
        b[7][2] = b[7][7] = White_Knight;
        b[7][3] = b[7][6] = White_Bishop;
        b[7][4] = White_Queen;
        b[7][5] = White_King;
    }
    public static void print(String[][] b){
        for (String[] strings : b) {
            for (int j = 0; j < b[0].length; j++) {
                System.out.print(strings[j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Chess c = new Chess();
    }
}
