package val.bond.resources.models;

import val.bond.applogic.Clothes.Clothes;
import val.bond.applogic.Clothes.Costume;
import val.bond.applogic.Enums.Color;
import val.bond.applogic.Enums.Material;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleClothes{
    private String clothesPartName;

    private int size;
    private Color color;
    private boolean isForMan;
    private Material material;
    private String name;

    private Integer legLengthSm;
    private Integer diametrLegSm;

    private Integer growthSm;
    private Integer handSmLength;
    private Boolean isHood;

    private Integer sexLvl;

    private Integer cylinderHeightSm;
    private Integer visorHeightSm;

    private Boolean isShoelaces;
    private Material outsoleMaterial;

    public SimpleClothes(Clothes clothes){
        size = clothes.getSize();
        color = clothes.getColor();
        isForMan = clothes.getIsForMan();
        material = clothes.getMaterial();
        name = clothes.getName();
    }

    public String getClothesPartName() {
        return clothesPartName;
    }

    public Boolean getIsHood() {
        return isHood;
    }

    public Color getColor() {
        return color;
    }

    public Integer getCylinderHeightSm() {
        return cylinderHeightSm;
    }

    public Integer getGrowthSm() {
        return growthSm;
    }

    public boolean getIsForMan(){
        return isForMan;
    }

    public Integer getDiametrLegSm() {
        return diametrLegSm;
    }

    public Integer getHandSmLength() {
        return handSmLength;
    }

    public Integer getLegLengthSm() {
        return legLengthSm;
    }

    public Boolean getIsShoelaces() {
        return isShoelaces;
    }

    public Integer getSexLvl() {
        return sexLvl;
    }

    public int getSize() {
        return size;
    }

    public Integer getVisorHeightSm() {
        return visorHeightSm;
    }

    public Material getMaterial() {
        return material;
    }

    public Material getOutsoleMaterial() {
        return outsoleMaterial;
    }

    public String getName() {
        return name;
    }

    public static ArrayList<SimpleClothes> devideCostumeOnSimple(Costume costume){
        SimpleClothes topClothes = new SimpleClothes(costume.getTopClothes());
        topClothes.growthSm = costume.getTopClothes().getGrowth_sm();
        topClothes.handSmLength = costume.getTopClothes().getHand_sm_length();
        topClothes.isHood = costume.getTopClothes().getIsHood();
        topClothes.clothesPartName = "topClothes";

        SimpleClothes downClothes = new SimpleClothes(costume.getDownClothes());
        downClothes.legLengthSm = costume.getDownClothes().getLeg_length_sm();
        downClothes.diametrLegSm = costume.getDownClothes().getDiametr_leg_sm();
        downClothes.clothesPartName = "downClothes";

        SimpleClothes underwear = new SimpleClothes(costume.getUnderwear());
        underwear.sexLvl = costume.getUnderwear().getSex_lvl();
        underwear.clothesPartName = "underwear";

        SimpleClothes hat = new SimpleClothes(costume.getHat());
        hat.cylinderHeightSm = costume.getHat().getCylinder_height_sm();
        hat.visorHeightSm = costume.getHat().getVisor_length_sm();
        hat.clothesPartName = "hat";

        SimpleClothes shoes = new SimpleClothes(costume.getShoes());
        shoes.isShoelaces = costume.getShoes().getIsShoelaces();
        shoes.outsoleMaterial = costume.getShoes().getOutsole_material();
        shoes.clothesPartName = "shoes";

        return new ArrayList<>(Arrays.asList(topClothes, downClothes, underwear, hat, shoes));
    }
}
