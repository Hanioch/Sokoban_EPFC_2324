package sokoban.model;

public interface Command {
    boolean execute();
    void undo();
}
