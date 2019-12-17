package agh.cs.project.lab8.Classes;

public enum MapDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW;

    public static MapDirection convertToMapDir(int direction) {
        switch (direction) {
            case 0:
                return MapDirection.N;
            case 1:
                return MapDirection.NE;
            case 2:
                return MapDirection.E;
            case 3:
                return MapDirection.SE;
            case 4:
                return MapDirection.S;
            case 5:
                return MapDirection.SW;
            case 6:
                return MapDirection.W;
            case 7:
                return MapDirection.NW;
            default:
                throw new IllegalArgumentException(direction + " is not in legal direction range (0-7)");
        }
    }

    public MapDirection rotateOrientation(MoveDirection moveDir) {
        int direction = (this.ordinal() + moveDir.ordinal()) % 7;
        return MapDirection.convertToMapDir(direction);
    }

    public Vector2d dirToMove() {
        switch (this) {
            case N:
                return new Vector2d(0, 1);
            case NE:
                return new Vector2d(1, 1);
            case E:
                return new Vector2d(1, 0);
            case SE:
                return new Vector2d(1, -1);
            case S:
                return new Vector2d(0, -1);
            case SW:
                return new Vector2d(-1, -1);
            case W:
                return new Vector2d(-1, 0);
            case NW:
                return new Vector2d(-1, 1);
            default:
                throw new IllegalArgumentException(this.toString() + " is an unknown MapDirection");
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case N:
                return "N";
            case NE:
                return "NE";
            case E:
                return "E";
            case SE:
                return "SE";
            case S:
                return "S";
            case SW:
                return "SW";
            case W:
                return "W";
            case NW:
                return "NW";
            default:
                return super.toString();
        }
    }
}
