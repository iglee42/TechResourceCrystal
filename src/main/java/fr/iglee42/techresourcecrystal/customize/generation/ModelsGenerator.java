package fr.iglee42.techresourcecrystal.customize.generation;

import fr.iglee42.techresourcecrystal.TechResourcesCrystal;
import fr.iglee42.techresourcecrystal.customize.Crystal;
import fr.iglee42.techresourcecrystal.customize.TypesConstants;
import fr.iglee42.techresourcecrystal.customize.custompack.PathConstant;

import java.io.File;
import java.io.FileWriter;

import static fr.iglee42.techresourcecrystal.TechResourcesCrystal.MODID;

public class ModelsGenerator {


    public static void generate() {
        for (Crystal c : TypesConstants.TYPES){
            if (c.isInModBase()) continue;
            blockFromParent("fragmented_"+c.name()+"_crystal_core","minecraft:block/cube_all",new TextureKey("all",MODID + ":"+c.name()+"/core/empty"));
            blockFromParent("fragmented_"+c.name()+"_crystal","minecraft:block/cube_all",new TextureKey("all",MODID + ":"+c.name()+"/block"));
            blockFromParent("fragment_"+c.name()+"_crystal_layer_1","minecraft:block/cube_all",new TextureKey("all",MODID+ ":"+c.name()+"/core/crystal_1"));
            blockFromParent("fragment_"+c.name()+"_crystal_layer_2","minecraft:block/cube_all",new TextureKey("all",MODID+ ":"+c.name()+"/core/crystal_2"));
            blockFromParent("fragment_"+c.name()+"_crystal_layer_3","minecraft:block/cube_all",new TextureKey("all",MODID+ ":"+c.name()+"/core/crystal_3"));
            itemFromBlock("fragmented_"+c.name()+"_crystal_core");
            itemFromBlock("fragmented_"+c.name()+"_crystal");
            itemFromParent("fragmented_"+ c.name()+"_crystal_item",MODID+ ":item/fragmented_crystal_item",new TextureKey("1",MODID+":"+c.name()+"/item"));
            itemFromParent(c.name()+"_crystal",MODID+ ":item/crystal",new TextureKey("1",MODID+":"+c.name()+"/item"));
            itemFromParent(c.name()+"_plate","minecraft:item/generated",new TextureKey("layer0",MODID+":"+c.name()+"/plate"));
        }
    }

    private static void itemFromBlock(String name){
        itemFromParent(name,MODID + ":block/" + name);
    }
    private static void itemFromParent(String name,String parent,TextureKey... textureKeys){
        String jsonBase =   "{\n"+
                "   \"parent\": \""+ parent +"\""+(textureKeys.length > 0 ? ",":"")+"\n";
        StringBuilder builder = new StringBuilder(jsonBase);
        if (textureKeys.length > 0){
            builder.append("   \"textures\": {\n");
            for (int i = 0; i < textureKeys.length; i++){
                builder.append(textureKeys[i].toJson());
                if (i != textureKeys.length - 1) builder.append(",");
                builder.append("\n");
            }
            builder.append("    }\n");
        }
        builder.append("}");
        generateItem(name,builder.toString());
    }
    private static void generateItem(String name, String fileText) {
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.ITEM_MODELS_PATH.toFile(), name+".json"));
            writer.write(fileText);
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when models generating",exception);
        }

    }
    private static void blockFromParent(String name,String parent,TextureKey... textureKeys){
        String jsonBase =   "{\n"+
                "   \"parent\": \""+ parent +"\",\n";
        StringBuilder builder = new StringBuilder(jsonBase);
        if (textureKeys.length > 0){
            builder.append("   \"textures\": {\n");
            builder.append("        \"particle\": \"").append(textureKeys[0].object()).append("\",\n");
            for (int i = 0; i < textureKeys.length; i++){
                builder.append(textureKeys[i].toJson());
                if (i != textureKeys.length - 1) builder.append(",");
                builder.append("\n");
            }
            builder.append("    }\n");
        }
        builder.append("}");
        generateBlock(name,builder.toString());
    }
    private static void generateBlock(String name, String fileText) {
        try {
            FileWriter writer = new FileWriter(new File(PathConstant.BLOCK_MODELS_PATH.toFile(), name+".json"));
            writer.write(fileText);
            writer.close();
        } catch (Exception exception){
            TechResourcesCrystal.LOGGER.error("An error was detected when models generating",exception);
        }

    }
}
