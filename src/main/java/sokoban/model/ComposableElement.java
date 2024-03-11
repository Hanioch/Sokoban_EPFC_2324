package sokoban.model;

public abstract class ComposableElement {
}

abstract class Animal {
    public abstract void animalSound();
    public void sleep() {
        System.out.println("Zzz");
    }
}