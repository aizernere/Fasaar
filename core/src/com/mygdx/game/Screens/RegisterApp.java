package com.mygdx.game.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MySQL.Account;

public class RegisterApp extends ApplicationAdapter implements Screen {
    Fasaar game;
    private Stage stage;
    private Skin skin;


    public RegisterApp(Fasaar game) {
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
        Label emailLabel = new Label("Email:", skin);
        TextField emailField = new TextField("", skin);
        Label colorLabel = new Label("Color:", skin);
        SelectBox<String> selectBox=new SelectBox<String>(skin);
        selectBox.setItems("normal","pixel","red","yellow");
        passwordField.setPasswordCharacter('*');
        passwordField.setPasswordMode(true);
        TextButton registerButton = new TextButton("Register!", skin);
        TextButton loginButton = new TextButton("Login Instead", skin);


        registerButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String email = emailField.getText();
                String color = selectBox.getSelected();
                    switch (color) {
                        case "normal":
                            color = "";
                            break;
                        default:
                            color = "_" + color;
                            break;
                    }
                System.out.println("Username: " + username);
                System.out.println("Password: " + password);
                System.out.println("Email: " + email);
                Account account = new Account();
                account.createUser(username, password, email, color);
                return true;
            }
        });
        loginButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new LoginApp(game));

                return true;
            }
        });

        table.add(usernameLabel).pad(10);
        table.add(usernameField).width(200).pad(10);
        table.row();
        table.add(passwordLabel).pad(10);
        table.add(passwordField).width(200).pad(10);
        table.row();
        table.add(emailLabel).pad(10);
        table.add(emailField).width(200).pad(10);
        table.row();
        table.add(colorLabel).pad(10);
        table.add(selectBox).width(200).pad(10);
        table.row();
        table.add(registerButton).colspan(2).center().pad(10);
        table.add(loginButton).colspan(2).pad(10);
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

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
