package it.uniba.hazard.engine.test;

import it.uniba.hazard.engine.main.GameInitialization;

public class InitializationTest {
    public static void main(String[] args){
        GameInitialization gi = new GameInitialization("C:/Users/MANU/IdeaProjects/ServerHazard/strutturaxml.xml");
        gi.initialization();
    }
}
