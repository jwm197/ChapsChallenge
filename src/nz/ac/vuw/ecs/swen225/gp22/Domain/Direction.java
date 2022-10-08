package nz.ac.vuw.ecs.swen225.gp22.Domain;

public enum Direction {
    NONE {},
    UP {
        public IntPoint direction() { return new IntPoint(0,-1); }
        public double probUp() { return 0.5; }
        public double probRight() { return 0.2; }
        public double probDown() { return 0.1; }
        public double probLeft() { return 0.2; }
    },
    RIGHT {
        public IntPoint direction() { return new IntPoint(1,0); }
        public double probUp() { return 0.2; }
        public double probRight() { return 0.5; }
        public double probDown() { return 0.2; }
        public double probLeft() { return 0.1; }
    },
    DOWN {
        public IntPoint direction() { return new IntPoint(0,1); }
        public double probUp() { return 0.1; }
        public double probRight() { return 0.2; }
        public double probDown() { return 0.5; }
        public double probLeft() { return 0.2; }
    },
    LEFT {
        public IntPoint direction() { return new IntPoint(-1,0); }
        public double probUp() { return 0.2; }
        public double probRight() { return 0.1; }
        public double probDown() { return 0.2; }
        public double probLeft() { return 0.5; }
    };
    
    public IntPoint direction() { return new IntPoint(0,0); }
    public double probUp() { return 0.25; }
    public double probRight() { return 0.25; }
    public double probDown() { return 0.25; }
    public double probLeft() { return 0.25; }
}
