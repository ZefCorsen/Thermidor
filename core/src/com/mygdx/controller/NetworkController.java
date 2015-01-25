package com.mygdx.controller;

/**
 * Created by Julie on 25/01/2015.
 */
public class NetworkController {

    final static int UDP = 6969;
    final static int TCP = 6868;

    private NetworkController(){

    }

    private static class NetworkControllerHolder{

        private final static NetworkController instance = new NetworkController();


    }

    public static NetworkController getInstance(){

        return NetworkControllerHolder.instance;
    }

}
