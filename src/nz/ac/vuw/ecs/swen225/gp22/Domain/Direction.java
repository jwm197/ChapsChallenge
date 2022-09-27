package nz.ac.vuw.ecs.swen225.gp22.Domain;

public enum Direction {
    UP {
        public Point direction() { return new Point(0,-1); }
    },
    RIGHT {
        public Point direction() { return new Point(1,0); }
    },
    DOWN {
        public Point direction() { return new Point(0,1); }
    },
    LEFT {
        public Point direction() { return new Point(-1,0); }
    };

    public Point direction() { return new Point(0,0); }
}
