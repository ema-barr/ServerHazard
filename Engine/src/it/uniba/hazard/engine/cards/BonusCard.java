package it.uniba.hazard.engine.cards;

/**
 * Created by andrea_iovine on 24/12/2016.
 */
public class BonusCard implements Card {

    public String bonusType;
    public String descriptionBonus;

    public BonusCard(String bonusType, String descriptionBonus) {
        this.bonusType = bonusType;
        this.descriptionBonus = descriptionBonus;
    }



    @Override
    public void executeAction() {
        if(bonusType.contains("bonus 1")) {

        }else if(bonusType.contains("bonus 2")) {

        }
    }




}
