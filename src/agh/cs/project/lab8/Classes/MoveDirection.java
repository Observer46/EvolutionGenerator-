package agh.cs.project.lab8.Classes;

public enum MoveDirection {
    FORWARD,
    FORWARDRIGHT,
    RIGHT,
    BACKWARDRIGHT,
    BACKWARD,
    BACKWARDLEFT,
    LEFT,
    FORWARDLEFT;

    public static MoveDirection geneToMove(int gene){
        switch(gene){
            case 0:
                return MoveDirection.FORWARD;
            case 1:
                return MoveDirection.FORWARDRIGHT;
            case 2:
                return MoveDirection.RIGHT;
            case 3:
                return MoveDirection.BACKWARDRIGHT;
            case 4:
                return MoveDirection.BACKWARD;
            case 5:
                return MoveDirection.BACKWARDLEFT;
            case 6:
                return MoveDirection.LEFT;
            case 7:
                return MoveDirection.FORWARDLEFT;
            default:
                throw new IllegalArgumentException(gene + " is not in legal gene range (0-7)");
        }
    }

//    public MoveDirection addDirections(MoveDirection moveDir){ // Przenieść na MapDirection
//        int direction = (this.ordinal() + moveDir.ordinal())%7;
//        return MoveDirection.geneToMove(direction);
//    }
//
//    public Vector2d dirToMove(){ // Przenieść na MapDirection
//        switch (this){
//            case FORWARD:
//                return new Vector2d(0,1);
//            case FORWARDRIGHT:
//                return new Vector2d(1,1);
//            case RIGHT:
//                return new Vector2d(1,0);
//            case BACKWARDRIGHT:
//                return new Vector2d(1,-1);
//            case BACKWARD:
//                return new Vector2d(0,-1);
//        }
//    }

}
