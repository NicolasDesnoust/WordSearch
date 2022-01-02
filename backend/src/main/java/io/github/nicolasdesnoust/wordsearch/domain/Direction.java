package io.github.nicolasdesnoust.wordsearch.domain;

public enum Direction {
    LEFT_TO_RIGHT {
        @Override
        public int getXMove() {
            return 1;
        }

        @Override
        public int getYMove() {
            return 0;
        }
    },
    RIGHT_TO_LEFT {
        @Override
        public int getXMove() {
            return -1;
        }

        @Override
        public int getYMove() { 
            return 0;
        }
    },
    TOP_TO_BOTTOM {
        @Override
        public int getXMove() {     
            return 0;
        }

        @Override
        public int getYMove() {    
            return 1;
        }
    },
    BOTTOM_TO_TOP {
        @Override
        public int getXMove() { 
            return 0;
        }

        @Override
        public int getYMove() {
            return -1;
        }
    },
    TOP_LEFT_TO_BOTTOM_RIGHT {
        @Override
        public int getXMove() {    
            return 1;
        }

        @Override
        public int getYMove() {    
            return 1;
        }
    },
    BOTTOM_RIGHT_TO_TOP_LEFT {
        @Override
        public int getXMove() {
            return -1;
        }

        @Override
        public int getYMove() {
            return -1;
        }
    },
    TOP_RIGHT_TO_BOTTOM_LEFT {
        @Override
        public int getXMove() {
            return -1;
        }

        @Override
        public int getYMove() {
            return 1;
        }
    },
    BOTTOM_LEFT_TO_TOP_RIGHT {
        @Override
        public int getXMove() {
            return 1;
        }

        @Override
        public int getYMove() {
            return -1;
        }
    };

    public abstract int getXMove();

    public abstract int getYMove();
}
