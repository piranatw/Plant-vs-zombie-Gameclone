package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import base.BasicZombie;
import base.Bullet;
import base.Cherrybomb;
import base.Potatomine;
import gui.PvzPane;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import logic.Plantable;

public class CollisionManager {
    private PvzPane pvzPane;
    private AnimationTimer collisionTimer;

    public CollisionManager(PvzPane pvzPane) {
        this.pvzPane = pvzPane;
        initCollisionDetection();
    }

    private void initCollisionDetection() {
        collisionTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                checkCollisions();
            }
        };
        collisionTimer.start();
    }

    public void stop() {
        if (collisionTimer != null) {
            collisionTimer.stop();
        }
    }

    @SuppressWarnings("unchecked")
    private void checkCollisions() {
        // Get all bullets and zombies (you'll need to cast or filter them)
        List<Node> bulletNodes = new ArrayList<>(pvzPane.getBulletLayer().getChildren());
        List<Node> zombieNodes = new ArrayList<>(pvzPane.getZombieLayer().getChildren());
        List<Node> plantNodes = new ArrayList<>(pvzPane.getPlantLayer().getChildren());

        // Check for collisions
        for (Node zombieNode : zombieNodes) {
            if (!(zombieNode instanceof BasicZombie))
                continue;
            BasicZombie zombie = (BasicZombie) zombieNode;
            Bounds zombieBounds = zombie.getBoundsInParent();

            for (Node plantNode : plantNodes) {
                if (!(plantNode instanceof Plantable))
                    continue;
                Plantable plant = (Plantable) plantNode;
                Bounds plantBounds = ((Node) plant).getBoundsInParent();
                // System.out.println(plantBounds); // Check if the plant has non-zero bounds
                // System.out.println("Checking zombie at " + zombieBounds + " against plant at " + plantBounds);

                if (boundsIntersect(zombieBounds, plantBounds)) {
                    handleZombiePlantCollision(plant, zombie);
                    break; // Assume one plant per tile
                }
            }
        }

        for (Iterator<Node> bulletIt = bulletNodes.iterator(); bulletIt.hasNext();) {
            Node bulletNode = bulletIt.next();

            Bullet bullet = (Bullet) bulletNode;

            // Get bullet bounds relative to the parent (bulletLayer)
            Bounds bulletBounds = bullet.getBoundsInParent();

            // Check against all zombies
            for (Iterator<Node> zombieIt = zombieNodes.iterator(); zombieIt.hasNext();) {
                Node zombieNode = zombieIt.next();

                if (!(zombieNode instanceof BasicZombie))
                    continue;
                BasicZombie zombie = (BasicZombie) zombieNode;

                // Get zombie bounds relative to the parent (zombieLayer)
                Bounds zombieBounds = zombie.getBoundsInParent();

                // Since both panes are aligned with the same positioning system,
                // we can directly compare the bounds
                if (boundsIntersect(bulletBounds, zombieBounds)) {
                    // Handle collision
                    if (bullet.getName() == "frost" && !zombie.isFrozen()) {
                        handleFrostCollision(bullet, zombie);
                    } else {
                        handleCollision(bullet, zombie);
                    }
                    break; // Break inner loop if bullet hit a zombie
                }
            }
        }

    }

    private boolean boundsIntersect(Bounds a, Bounds b) {
        // Check if two bounds intersect
        return a.intersects(b);
    }
    private void handleZombiePlantCollision(Plantable plant,BasicZombie zombie){
        if(plant instanceof Potatomine){
            zombie.takeDamage(30);
            plant.takeDamage(10);
            Platform.runLater(() -> {
                pvzPane.getPlantLayer().getChildren().remove(plant);
            });
        }else if(plant instanceof Cherrybomb){
            zombie.takeDamage(200000);
            plant.takeDamage(10);
            Platform.runLater(() -> {
                pvzPane.getZombieLayer().getChildren().remove(zombie);
                pvzPane.getPlantLayer().getChildren().remove(plant);
            });
        }
        else{
            zombie.startAttacking(plant);
        }
    }
    private void handleFrostCollision(Bullet bullet, BasicZombie zombie) {
        zombie.takeDamage(bullet.getDamage());
        zombie.Frozen();
    }

    private void handleCollision(Bullet bullet, BasicZombie zombie) {
        // Handle the collision (damage zombie, remove bullet)
        zombie.takeDamage(bullet.getDamage());

        // Remove bullet from the scene
        javafx.application.Platform.runLater(() -> {
            pvzPane.getBulletLayer().getChildren().remove(bullet);
        });

        // If zombie health <= 0, remove it
        if (zombie.getHealth() <= 0) {
            javafx.application.Platform.runLater(() -> {
                pvzPane.getZombieLayer().getChildren().remove(zombie);
            });
        }
    }
}