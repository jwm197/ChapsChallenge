package nz.ac.vuw.ecs.swen225.gp22.Persistency;

record Location(int x, int y) {
    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

record Path(int x, int y, int x1, int y1) {
    @Override
    public String toString() {
        return "Path{" +
                "x=" + x +
                ", y=" + y +
                ", x1=" + x1 +
                ", y1=" + y1 +
                '}';
    }
}