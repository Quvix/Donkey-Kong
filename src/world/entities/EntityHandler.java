package world.entities;

import core.Renderable;
import utils.Direction;
import world.World;
import world.entities.creatures.DonkeyKong;
import world.entities.creatures.Player;
import world.entities.creatures.Princess;
import world.entities.creatures.RollingBarrel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityHandler implements Renderable {

    private List<Entity> enemies;
    private Entity player;
    private Entity dk;
    private Entity princess;
    private World world;

    public EntityHandler(World world) {
        this.world = world;

        enemies = new ArrayList<>();
    }

    @Override
    public void render(Graphics2D g) {
        dk.render(g);
        player.render(g);
        for(Entity e : enemies) {
            e.render(g);
        }
        princess.render(g);
    }

    public void update() {
        dk.update();
        player.update();
        if(!player.isDestroyed()) {
            for(Entity e : enemies) {
                e.update();
            }
        }
        enemies.removeIf(i -> i.isDestroyed());
        princess.update();
    }

    public void spawnPlayer(Point position) {
        player = new Player((int)position.getX(), (int)position.getY(), world);
    }

    public void spawnDonkeyKong(Point position) {
        dk = new DonkeyKong((int)position.getX(), (int)position.getY(), world);
    }

    public void spawnPrincess(Point position) {
        princess = new Princess((int)position.getX(), (int)position.getY(), world);
    }


    public Entity getPlayer() {
        return player;
    }

    public void addEnemy(Entity entity) {
        enemies.add(entity);
    }

    public void destroyEnemies() {
        enemies.clear();
    }
}
