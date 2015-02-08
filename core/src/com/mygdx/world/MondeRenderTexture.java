package com.mygdx.world;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.player.Bomb;
import com.mygdx.player.Bullet;
import com.mygdx.player.Player;

/**
 * Created by Jerem on 21/01/2015.
 */
public class MondeRenderTexture {


    private WorldImpl world;
    private SpriteBatch spriteBatch;
    private String gameID;

    public MondeRenderTexture(SpriteBatch spriteBatch, String gameID) {
        this.world = WorldImpl.getInstance();
        this.spriteBatch = spriteBatch;
        this.gameID = gameID;
        spriteBatch.enableBlending();

    }

    public void render() {
        spriteBatch.draw(Assets.backgroundRegionGame, -Assets.ppuX / 2 / Assets.PIXELS_TO_METERS, -Assets.ppuY / 2 / Assets.PIXELS_TO_METERS, Assets.ppuX / Assets.PIXELS_TO_METERS, Assets.ppuY / Assets.PIXELS_TO_METERS);
        spriteBatch.draw(Assets.bomb, Assets.actionBordX, Assets.actionBordY, Assets.tailleActionX, Assets.tailleActionY);
        spriteBatch.draw(Assets.musket, Assets.actionBordX, Assets.actionBordY + Assets.tailleActionY, Assets.tailleActionX, Assets.tailleActionY);
        drawLife();
        drawBombs();
        drawBullet();
        drawPlayer();

    }


    private void drawPlayer() {
        for (Player player : world.getPlayers()) {
            spriteBatch.draw(Assets.player, (player.getBody().getPosition().x - Assets.sprite.getWidth() / 2), (player.getBody().getPosition().y - Assets.sprite.getHeight() / 2), Assets.widthPlayer, Assets.heightPlayer);
        }
    }

    private void drawBombs() {
        for (Bomb bomb : world.getBombs()) {
            spriteBatch.draw(Assets.bombGame, (bomb.getBody().getPosition().x - Assets.spriteBomb.getWidth() / 2), (bomb.getBody().getPosition().y - Assets.spriteBomb.getHeight() / 2), Assets.spriteBomb.getWidth(), Assets.spriteBomb.getHeight());
        }
    }

    private void drawBullet() {
        for (Bullet bullet : world.getBullets()) {
            Sprite spriteBullet = new Sprite(Assets.bullet);
            spriteBullet.setRotation((float) Math.toDegrees((bullet.getBody().getAngle())));
            spriteBatch.draw(spriteBullet, (bullet.getBody().getPosition().x - Assets.spriteBullet.getWidth() / 2), (bullet.getBody().getPosition().y - Assets.spriteBullet.getHeight() / 2), Assets.spriteBullet.getWidth(), Assets.spriteBullet.getHeight());
        }
    }

    private void drawLife() {
        int life = 0;
        try {
            life =  world.getPlayer(gameID).getLife();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (life >0){
            spriteBatch.draw(Assets.life, Assets.actionBordX, Assets.actionBordY + 2*Assets.tailleActionY, Assets.tailleActionX/4, Assets.tailleActionY/4);
        }
        if (life >1){
            spriteBatch.draw(Assets.life, Assets.actionBordX+Assets.tailleActionX/4, Assets.actionBordY + 2*Assets.tailleActionY, Assets.tailleActionX/4, Assets.tailleActionY/4);
        }
        if (life >2){
            spriteBatch.draw(Assets.life, Assets.actionBordX+Assets.tailleActionX/2, Assets.actionBordY + 2*Assets.tailleActionY, Assets.tailleActionX/4, Assets.tailleActionY/4);
        }
        if (life >0){
            spriteBatch.draw(Assets.life, Assets.actionBordX, Assets.actionBordY + 2*Assets.tailleActionY+Assets.tailleActionY/4, Assets.tailleActionX/4, Assets.tailleActionY/4);
        }
        if (life >1){
            spriteBatch.draw(Assets.life, Assets.actionBordX+Assets.tailleActionX/4, Assets.actionBordY + 2*Assets.tailleActionY+Assets.tailleActionY/4, Assets.tailleActionX/4, Assets.tailleActionY/4);
        }
        if (life >2){
            spriteBatch.draw(Assets.life, Assets.actionBordX+Assets.tailleActionX/2, Assets.actionBordY + 2*Assets.tailleActionY+Assets.tailleActionY/4, Assets.tailleActionX/4, Assets.tailleActionY/4);
        }


    }

}