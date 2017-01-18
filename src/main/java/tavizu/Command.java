package main.java.tavizu;

@FunctionalInterface
public interface Command {
    void run(String arg);
}
