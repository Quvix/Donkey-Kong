package world;

import core.Renderable;
import utils.Direction;
import world.entities.Entity;
import world.entities.EntityHandler;
import world.entities.creatures.RollingBarrel;
import world.entities.statics.Barrel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class World implements Renderable {

    private GameMap map;
    private EntityHandler entities;

    public World(String mapName) {
        this.map = new GameMap(mapName, this);
        this.entities = new EntityHandler(this);

        spawnPlayer();
        spawnDonkeyKong();
        spawnPrincess();
    }

    @Override
    public void render(Graphics2D g) {
        map.render(g);
        entities.render(g);
    }

    public void update() {
        map.update();
        entities.update();
    }

    public void spawnPlayer() {
        entities.spawnPlayer(map.getPlayerSpawnPosition());
    }

    public void spawnDonkeyKong() {
        entities.spawnDonkeyKong(map.getDonkeyKongSpawnPosition());
    }

    public void spawnPrincess() {
        entities.spawnPrincess(map.getPrincessSpawnPosition());
    }

    public List<Entity> getCollidingBeams(Rectangle rect) {
        List<Entity> collidingBeams = new ArrayList<>();
        for(Entity beam : map.getBeams()) {
            if(beam.intersects(rect)) {
                collidingBeams.add(beam);
            }
        }
        return collidingBeams;
    }

    public List<Entity> getCollidingBeams(Entity e) {
        return getCollidingBeams(e.getBounds());
    }

    public Entity getCollidingLadder(Rectangle rect) {
        for(Entity ladder : map.getLadders()) {
            if(ladder.intersects(rect)) {
                return ladder;
            }
        }
        return null;
    }

    public Entity getPlayer() {
        return entities.getPlayer();
    }

    public void killPlayer() {
        getPlayer().destroy();
    }

    public int getLowestMapY() {
        return map.getLowestBeamHeight();
    }

    public void spawnBarrel(int x, int y, Direction direction) {
        entities.addEnemy(new RollingBarrel(x, y, this, direction));
    }

    public boolean isPlayerInWinDest() {
        return getPlayer().intersects(map.getWinningDestination());
    }

    public void destroyEnemies() {
        entities.destroyEnemies();
    }

}
