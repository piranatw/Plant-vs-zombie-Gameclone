package main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import base.Bullet;
import base.Cherrybomb;
import base.Plant;
import base.Potatomine;
import base.Zombie;
import gui.PvzPane;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Node;
//import logic.Plantable;

public class CollisionManager {
    private PvzPane pvzPane;
    private AnimationTimer collisionTimer;

    public CollisionManager(PvzPane pvzPane) {
        this.pvzPane = pvzPane;
        initCollisionDetection();
    }

    public void stop() {
        if (collisionTimer != null) {
            collisionTimer.stop();
        }
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

    private void checkCollisions() {
        List<Node> bulletNodes = new ArrayList<>(pvzPane.getBulletLayer().getChildren());
        List<Node> zombieNodes = new ArrayList<>(pvzPane.getZombieLayer().getChildren());
        List<Node> plantNodes = new ArrayList<>(pvzPane.getPlantLayer().getChildren());

        for (Node zombieNode : zombieNodes) {
            if (!(zombieNode instanceof Zombie))
                continue;
            Zombie zombie = (Zombie) zombieNode;
            Bounds zombieBounds = zombie.getBoundsInParent();

            for (Node plantNode : plantNodes) {
                if (!(plantNode instanceof Plant))
                    continue;
                Plant plant = (Plant) plantNode;
                Bounds plantBounds = ((Node) plant).getBoundsInParent();

                if (boundsIntersect(zombieBounds, plantBounds)) {
                    handleZombiePlantCollision(plant, zombie);
                    break;
                }
            }
        }

        for (Iterator<Node> bulletIt = bulletNodes.iterator(); bulletIt.hasNext();) {
            Node bulletNode = bulletIt.next();
            if (bulletNode instanceof Bullet) {
                Bullet bullet = (Bullet) bulletNode;
                Bounds bulletBounds = bullet.getBoundsInParent();
                for (Node zombieNode : zombieNodes) {
                    if (!(zombieNode instanceof Zombie))
                        continue;
                    Zombie zombie = (Zombie) zombieNode;
                    Bounds zombieBounds = zombie.getBoundsInParent();

                    if (boundsIntersect(bulletBounds, zombieBounds)) {
                        if ("frost".equals(bullet.getName()) && !zombie.isFrozen()) {
                            handleFrostCollision(bullet, zombie);
                        } else {
                            handleCollision(bullet, zombie);
                        }
                        break;
                    }
                }
            }
        }}


    private boolean boundsIntersect(Bounds a, Bounds b) {
        return a.intersects(b);
    }

    private void handleZombiePlantCollision(Plant plant, Zombie zombie) {
        if (plant instanceof Potatomine) {
            zombie.takeDamage(100);
            plant.takeDamage(10);
            Platform.runLater(() -> pvzPane.getPlantLayer().getChildren().remove(plant));
        } else if (plant instanceof Cherrybomb) {
            zombie.takeDamage(200000);
            plant.takeDamage(10);
            Platform.runLater(() -> {
                pvzPane.getZombieLayer().getChildren().remove(zombie);
                pvzPane.getPlantLayer().getChildren().remove(plant);
            });
        } else {
            zombie.startAttacking(plant);
        }
    }

    private void handleFrostCollision(Bullet bullet, Zombie zombie) {
        zombie.takeDamage(bullet.getDamage());
        zombie.Frozen();
        Platform.runLater(() -> pvzPane.getBulletLayer().getChildren().remove(bullet));
        removeZombieIfDead(zombie);
    }

    private void handleCollision(Bullet bullet, Zombie zombie) {
        zombie.takeDamage(bullet.getDamage());
        Platform.runLater(() -> pvzPane.getBulletLayer().getChildren().remove(bullet));
        removeZombieIfDead(zombie);
    }

    private void removeZombieIfDead(Zombie zombie) {
        if (zombie.getHealth() <= 0) {
            Platform.runLater(() -> pvzPane.getZombieLayer().getChildren().remove(zombie));
        }
    }
}
