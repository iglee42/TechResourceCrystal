package fr.iglee42.techresourcecrystal.customize.generation;

public record TextureKey(String key, String object) {

    public String toJson(){
        return "        \""+key+"\": \""+object+"\"";
    }
}