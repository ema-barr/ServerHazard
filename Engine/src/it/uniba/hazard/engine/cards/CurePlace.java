package it.uniba.hazard.engine.cards;


/**
 * Cure complete for one place.
 * @author Donato
 */
public class CurePlace extends BonusCard{

    private String objectID;




    public CurePlace(String bonusType) {
        super(bonusType);
        this.objectID = this.getClass().getSuperclass().getName() + "_" + this.getClass().getName();
    }

    /**
     *
     * @return identification of objectID
     */
    public String getObjectID() {
        return objectID;
    }

    /**
     *
     * @return instance of CurePlace
     */
    public CurePlaceInstance getCurePlace(){
        return new CurePlaceInstance(bonusType);
    }
}
