<h1 align="center">
  <br>
  <a href="https://raw.githubusercontent.com/wajeehanwar/2DvsKD---A-Boid-Visualizer"><img src="https://raw.githubusercontent.com/wajeehanwar/2DvsKD---A-Boid-Visualizer/master/images/flock.jpg" alt="Bird Flock" width="400"></a>
  <br>
  2DvsKD - A Boid Visualizer 
  <br>
</h1>

<h4 align="center">A simulation of the flocking behavior of birds, using a PointST or KdTreeST data type to be able to visualize the impact of data structure implementation on performance. </h4>

<p align="center">
<a href="#overview">Overview</a> •
<a href="#view">View</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#download">Download</a> •
  <a href="#credits">Credits</a> •
  <a href="#license">License</a>
</p>

## Overview

The aggregate motion of a flock of birds, a herd of land animals, or a school of fish is a beautiful and familiar part of the natural world. The aggregate motion of the simulated flock is created by a distributed behavioral model much like that at work in a natural flock; the birds choose their own course. Each simulated bird is implemented as an independent actor that navigates according to its local perception of the dynamic environment, the laws of simulated physics that rule its motion, and a set of behaviors programmed into it. The aggregate motion of the simulated flock is the result of the dense interaction of the relatively simple behaviors of the individual simulated birds.

BoidSimulator is an implementation of Craig Reynold’s Boids program to simulate the flocking behavior of birds, using a PointST or KdTreeST data type to be able to visualize the impact of data structure implementation on performance.

Boids is an example of [emergent](https://en.wikipedia.org/wiki/Emergence "Emergence") behavior - the complexity of Boids arises from the interaction of individual agents (the boids, in this case) adhering to a set of simple rules. The rules applied in the simplest Boids world are as follows:

- **separation**: [steer](https://en.wiktionary.org/wiki/steer#Verb_2 "wikt:steer") to avoid crowding local flockmates
- **alignment**: steer towards the average heading of local flockmates
- **cohesion**: steer to move towards the average position (center of mass) of local flockmates

## View

<h4>Brute Force Point Boid Implementation</h4>
 <a href="https://raw.githubusercontent.com/wajeehanwar/2DvsKD---A-Boid-Visualizer"><img src="https://raw.githubusercontent.com/wajeehanwar/2DvsKD---A-Boid-Visualizer/master/images/boid_2d.png" alt="Brute Force Boid Implementation" width="500"></a>
 
 <h4>K-Dimension Tree Boid Implementation</h4>
 <a href="https://raw.githubusercontent.com/wajeehanwar/2DvsKD---A-Boid-Visualizer"><img src="https://raw.githubusercontent.com/wajeehanwar/2DvsKD---A-Boid-Visualizer/master/images/boid_kdtree.png" alt="KD Tree Boid Implementation" width="500"></a>

## How To Use

Requires installation of [Java](https://java.com/en/download/help/download_options.xml). To compile and run this application from your command line:

```bash
# To compile program.
$ javac BoidSimulator.java
```

```bash
# The first command-line argument specifies which data type to
# use (brute for PointST or kdtree for KdTreeST), the second
# argument specifies the number of boids, and the third argument
# specifies the number of friends each boid has.

$ java BoidSimulator brute 100 10

$ java BoidSimulator kdtree 100 10
```

## Download

You can [download](https://github.com//wajeehanwar/2DvsKD---A-Boid-Visualizer) here.

## Credits

- Swami Iyer
- Robert Sedgewick
- Kevin Wayne
- Craig Reynold

This software was developed using the following:

- Java

## License

MIT

---

> GitHub [@wajeehanwar](https://github.com/wajeehanwar)
