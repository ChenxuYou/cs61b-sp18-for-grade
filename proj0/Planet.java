public class Planet {

    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private double G = 6.67e-11;

    // Constructor
    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass,
            String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    // make a copy
    public Planet(Planet p) {
        this.xxPos = p.xxPos;
        this.yyPos = p.yyPos;
        this.xxVel = p.xxVel;
        this.yyVel = p.yyVel;
        this.mass = p.mass;
        this.imgFileName = p.imgFileName;
    }

    // calculate the distance r
    public double calcDistance(Planet other) {
        double dx = this.xxPos - other.xxPos;
        double dy = this.yyPos - other.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // calculate the force exerted by another planet
    public double calcForceExertedBy(Planet other) {
        return G * this.mass * other.mass / (this.calcDistance(other) * this.calcDistance(other));
    }

    // compute the component of the force along the X-axis
    public double calcForceExertedByX(Planet other) {
        double dx = other.xxPos - this.xxPos;
        double F = this.calcForceExertedBy(other);
        double r = this.calcDistance(other);
        return F * dx / r;
    }

    // compute the component of the force along the Y-axis
    public double calcForceExertedByY(Planet other) {
        double dy = other.yyPos - this.yyPos;
        double F = this.calcForceExertedBy(other);
        double r = this.calcDistance(other);
        return F * dy / r;
    }

    // compute the net component of the force along the X-axis
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double result = 0;
        for (Planet eachPlanet : allPlanets) {
            if (!this.equals(eachPlanet)) {
                result += this.calcForceExertedByX(eachPlanet);
            }
        }
        return result;
    }

    // compute the net component of the force along the Y-axis
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double result = 0;
        for (Planet eachPlanet : allPlanets) {
            if (!this.equals(eachPlanet)) {
                result += this.calcForceExertedByY(eachPlanet);
            }
        }
        return result;
    }

    // update the planet's status
    public void update(double dt, double fX, double fY) {
        double accelerationX = fX / this.mass;
        double accelerationY = fY / this.mass;
        this.xxVel = this.xxVel + dt * accelerationX;
        this.yyVel = this.yyVel + dt * accelerationY;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }

    // draw the planet on the screen
    public void draw() {
        String imgToDraw = "./images/" + this.imgFileName;
        StdDraw.picture(this.xxPos, this.yyPos, imgToDraw);
    }

}
