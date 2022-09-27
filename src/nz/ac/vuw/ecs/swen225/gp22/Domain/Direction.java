package nz.ac.vuw.ecs.swen225.gp22.Domain;

public enum Direction {
    UP {
        public IntPoint direction() { return new IntPoint(0,-1); }
    },
    RIGHT {
        public IntPoint direction() { return new IntPoint(1,0); }
    },
    DOWN {
        public IntPoint direction() { return new IntPoint(0,1); }
    },
    LEFT {
        public IntPoint direction() { return new IntPoint(-1,0); }
    };
    
    public IntPoint direction() { return new IntPoint(0,0); }
}
