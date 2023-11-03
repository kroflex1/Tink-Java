package edu.project2;

import edu.project2.Generators.PrimaGenerator;
import edu.project2.Renders.SimpleRenderer;

public class Main {
    public static void main(String[] args) {
        SimpleRenderer renderer = new SimpleRenderer();
        Maze maze = new PrimaGenerator().generate(50,50);
        System.out.print(renderer.render(maze));
    }
}
