package com.mygdx.game.screen;


import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.assets.Assets;
import com.mygdx.dragNdrop.InventoryScreen;

public class LevelScreen implements Screen {

	private Stage stage;
	private Table table;
	private Skin skin;
	private SpriteBatch batch;
	private Sprite sprite;

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
//		stage.setViewport(width, height, false);
		table.invalidateHierarchy();
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		batch = new SpriteBatch();
	    sprite = new Sprite(Assets.manager.get(Assets.splashScreen, Texture.class));
		sprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		sprite.setAlpha(0.7f);
		
		skin = new Skin(Gdx.files.internal("data/uiskin.json"), new TextureAtlas("data/uiskin.pack"));
		
		table = new Table(skin);
		table.setFillParent(true);

		List<String> list = new List<String>(skin);
		list.setItems(new String[] {"Niveau 1", "Niveau 2", "Niveau 3"});

		ScrollPane scrollPane = new ScrollPane(list, skin);
		
		TextButton play = new TextButton("PLAY", skin);
		play.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
//				((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
				((Game) Gdx.app.getApplicationListener()).setScreen(new InventoryScreen());
			}

		});
		play.pad(15);

		ImageButton back = new ImageButton(skin, "back");
		back.addListener(new ClickListener() {

			@Override
			public void clicked(InputEvent event, float x, float y) {
				stage.addAction(sequence(moveTo(0, stage.getHeight(), .5f), run(new Runnable() {

					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new MenuScreen());
					}

				})));
			}
		});
		back.pad(10);

		table.add(new Label("SELECT LEVEL", skin)).colspan(3).expandX().spaceBottom(50).row();
		table.add(scrollPane).uniformX().expandY().top().left();
		table.add(play).uniformX();
		table.add(back).uniformX().bottom().right();

		stage.addActor(table);

		stage.addAction(sequence(moveTo(0, stage.getHeight()), moveTo(0, 0, .5f))); // coming in from top animation
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}

}
