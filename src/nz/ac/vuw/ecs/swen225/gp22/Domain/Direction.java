package nz.ac.vuw.ecs.swen225.gp22.Domain;

/**
 * Represents the direction an entity is facing/is moving in.
 * 
 * @author Yuri Sidorov (300567814)
 * 
 */
public enum Direction {
    /**
     * No direction
     */
    NONE {},

    /**
     * Up direction
     */
    UP {
        @Override
        public IntPoint direction() { return new IntPoint(0,-1); }
        @Override
        public double probUp() { return 0.5; }
        @Override
        public double probRight() { return 0.2; }
        @Override
        public double probDown() { return 0.1; }
        @Override
        public double probLeft() { return 0.2; }
    },

    /**
     * Right direction
     */
    RIGHT {
        @Override
        public IntPoint direction() { return new IntPoint(1,0); }
        @Override
        public double probUp() { return 0.2; }
        @Override
        public double probRight() { return 0.5; }
        @Override
        public double probDown() { return 0.2; }
        @Override
        public double probLeft() { return 0.1; }
    },

    /**
     * Down direction
     */
    DOWN {
        @Override
        public IntPoint direction() { return new IntPoint(0,1); }
        @Override
        public double probUp() { return 0.1; }
        @Override
        public double probRight() { return 0.2; }
        @Override
        public double probDown() { return 0.5; }
        @Override
        public double probLeft() { return 0.2; }
    },

    /**
     * Left direction
     */
    LEFT {
        @Override
        public IntPoint direction() { return new IntPoint(-1,0); }
        @Override
        public double probUp() { return 0.2; }
        @Override
        public double probRight() { return 0.1; }
        @Override
        public double probDown() { return 0.2; }
        @Override
        public double probLeft() { return 0.5; }
    };
    
    /**
     * Get the unit point representation of the direction.
     * 
     * @return The unit point representation of the direction.
     */
    public IntPoint direction() { return new IntPoint(0,0); }

    /**
     * Get the probability of moving up.
     * 
     * @return The probability of moving up.
     */
    public double probUp() { return 0.25; }

    /**
     * Get the probability of moving right.
     * 
     * @return The probability of moving right.
     */
    public double probRight() { return 0.25; }

    /**
     * Get the probability of moving down.
     * 
     * @return The probability of moving down.
     */
    public double probDown() { return 0.25; }

    /**
     * Get the probability of moving left.
     * 
     * @return The probability of moving left.
     */
    public double probLeft() { return 0.25; }
}
