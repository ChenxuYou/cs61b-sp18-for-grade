import java.util.ArrayList;

public class NBody {
    private static final String backgroundToDraw = "./images/starfield.jpg";
    // private static final int numOfPlanets = 5;

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        double timer = 0;

        double[] xForces = new double[planets.length];
        double[] yForces = new double[planets.length];

        while (timer < T) {
            // Calculate forces
            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            // Update statuses
            for (int i = 0; i < planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            // Drawing Part
            StdDraw.enableDoubleBuffering();
            StdDraw.clear();
            drawBackground(radius);
            for (Planet planet : planets) {
                planet.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            timer += dt;
        }
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", planets[i].xxPos,
                    planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass,
                    planets[i].imgFileName);
        }
    }

    public static double readRadius(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        return in.readDouble();
    }

    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        
        ArrayList<Planet> planetList = new ArrayList<>();
        while (in.hasNextLine()) {
            String line = in.readLine().trim();
            if (line.isEmpty() || !Character.isDigit(line.charAt(0))) {
                break;
            }
            String[] parts = line.split("\\s+");
            try {
                double xxPos = Double.parseDouble(parts[0]);
                double yyPos = Double.parseDouble(parts[1]);
                double xxVel = Double.parseDouble(parts[2]);
                double yyVel = Double.parseDouble(parts[3]);
                double mass = Double.parseDouble(parts[4]);
                String imgFileName = parts[5];
                planetList.add(new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName));
            } catch (NumberFormatException e) {
                // Skip lines that can't be parsed correctly
                continue;
            }
        }
        Planet[] planets = planetList.toArray(new Planet[0]);
        System.out.println(planets);
        return planets;
    }

    private static void drawBackground(double radius) {
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, backgroundToDraw, 2.1 * radius, 2.1 * radius);
    }
}
