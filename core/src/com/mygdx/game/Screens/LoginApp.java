package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.lwjgl3.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MySQL.Account;

import java.util.ArrayList;

public class LoginApp extends ApplicationAdapter implements Screen {

    Fasaar game;
    private Stage stage;
    private Skin skin;

    public static boolean appExiting = false;

    public LoginApp(Fasaar game) {
        this.game = game;
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label usernameLabel = new Label("Username:", skin);
        TextField usernameField = new TextField("", skin);
        Label passwordLabel = new Label("Password:", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        TextButton loginButton = new TextButton("Login", skin);
        TextButton registerButton = new TextButton("Register Here", skin);


        loginButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String username = usernameField.getText();
                int password = passwordField.getText().hashCode();;
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                Account account = new Account();
                ArrayList<String> res;
                res = account.findUser(username,password);

                    if (!res.isEmpty()){
                        int id = Integer.parseInt(res.get(0));
                        switchToNewWindow(new FasaarGame(id,res.get(1)));
                    }
                    else{
                        System.out.println("Something went wrong, please check your credentials.");
                    }
                    return true;
            }
        });
        registerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                game.setScreen(new RegisterApp(game));
                return true;
            }
        });

        table.add(usernameLabel).pad(10);
        table.add(usernameField).width(200).pad(10);
        table.row();
        table.add(passwordLabel).pad(10);
        table.add(passwordField).width(200).pad(10);
        table.row();
        table.add(loginButton).colspan(2).center().pad(10);
        table.add(registerButton).colspan(2).pad(10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void hide() {

    }
    public static void switchToNewWindow(ApplicationListener newListener) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
            config.setTitle("Fasaar");
            config.setWindowedMode(800, 600);
            config.setBackBufferConfig(8, 8, 8, 8, 24, 0,4);
            new Lwjgl3Application(newListener, config);
        }));
        appExiting = true;
        Gdx.app.exit();
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

