public class MarioCommands {
    public static class JumpCommand implements Command {
        private Mario mario;

        public JumpCommand(Mario mario) {
            this.mario = mario;
        }

        @Override
        public void execute() {
            mario.jump();
        }
    }

    public static class MoveLeftCommand implements Command {
        private Mario mario;

        public MoveLeftCommand(Mario mario) {
            this.mario = mario;
        }

        @Override
        public void execute() {
            mario.leftMove();
        }
    }

    public static class MoveRightCommand implements Command {
        private Mario mario;

        public MoveRightCommand(Mario mario) {
            this.mario = mario;
        }

        @Override
        public void execute() {
            mario.rightMove();
        }
    }
} 