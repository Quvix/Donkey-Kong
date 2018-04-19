package world;

import core.Renderable;
import utils.ResourceReader;
import world.entities.Entity;
import world.entities.statics.Barrel;
import world.entities.statics.Beam;
import world.entities.statics.Ladder;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameMap implements Renderable {

    private List<Entity> beams;
    private List<Entity> ladders;
    private Point donkeyKongSpawn = null;
    private Point playerSpawn = null;
    private Point princessSpawn = null;
    private World world;
    private int lowestBeamY;
    private Rectangle winningDestination = null;

    public GameMap(String filename, World world) {
        beams = new ArrayList<>();
        ladders = new ArrayList<>();
        loadMap(filename);
        this.world = world;
    }

    private void loadMap(String filename) {
        List<String> lines = ResourceReader.parseLines(filename);
        try {
            for(String line : lines) {
                String[] tokens = line.split(";");

                if(line.startsWith("BEAM")) {
                    parseBeam(tokens);
                } else if(line.startsWith("LADDER")) {
                    parseLadder(tokens);
                } else if(line.startsWith("DONKEY_KONG_SPAWN")) {
                    parseDKSpawn(tokens);
                } else if(line.startsWith("PLAYER_SPAWN")) {
                    parsePlayerSpawn(tokens);
                } else if(line.startsWith("BARREL")) {
                    parseBarrel(tokens);
                } else if(line.startsWith("DESTINATION")) {
                    parseWinningDest(tokens);
                } else if(line.startsWith("PRINCESS_SPAWN")) {
                    parsePrincess(tokens);
                }

                for(Entity e : beams) {
                    if(e.getY() > lowestBeamY) {
                        lowestBeamY = (int)e.getY();
                    }
                }
            }

            if(playerSpawn == null) {
                throw new IOException("Missing player spawn!");
            }
            if(donkeyKongSpawn == null) {
                throw new IOException("Missing Donkey Kong spawn!");
            }
            if(princessSpawn == null) {
                throw new IOException("Missing princess spawn!");
            }
            if(winningDestination == null) {
                throw new IOException("Missing winnning destination!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseBeam(String[] tokens) throws IOException {
        if(tokens.length < 3) {
            throw new IOException();
        }
        beams.add(new Beam(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), world));
    }

    private void parseLadder(String[] tokens) throws IOException {
        if(tokens.length < 4) {
            throw new IOException();
        }
        ladders.add(new Ladder(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), world));
    }

    private void parseDKSpawn(String[] tokens) throws IOException {
        if(tokens.length < 3) {
            throw new IOException();
        }
        donkeyKongSpawn = new Point(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
    }

    private void parsePlayerSpawn(String[] tokens) throws IOException {
        if(tokens.length < 3) {
            throw new IOException();
        }
        playerSpawn = new Point(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
    }

    private void parseBarrel(String[] tokens) throws IOException {
        if(tokens.length < 3) {
            throw new IOException();
        }
        beams.add(new Barrel(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), world));
    }

    private void parseWinningDest(String[] tokens) throws IOException {
        if(tokens.length < 5) {
            throw new IOException();
        }
        winningDestination = new Rectangle(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
    }

    private void parsePrincess(String[] tokens) throws IOException {
        if(tokens.length < 3) {
            throw new IOException();
        }
        princessSpawn = new Point(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
    }

    @Override
    public void render(Graphics2D g) {
        for(Entity e : beams) {
            e.render(g);
        }
        for(Entity e : ladders) {
            e.render(g);
        }
    }

    public void update() {
        for(Entity e : beams) {
            e.update();
        }
        for(Entity e : ladders) {
            e.update();
        }
    }

    public Point getPlayerSpawnPosition() {
        return playerSpawn;
    }

    public Point getDonkeyKongSpawnPosition() {
        return donkeyKongSpawn;
    }

    public Point getPrincessSpawnPosition() {
        return princessSpawn;
    }

    public List<Entity> getBeams() {
        return beams;
    }

    public List<Entity> getLadders() {
        return ladders;
    }

    public int getLowestBeamHeight() {
        return lowestBeamY;
    }

    public Rectangle getWinningDestination() {
        return winningDestination;
    }
}
