package net.jadenxgamer.netherexp.client.assetdriven;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public record BurnPalettes(Set<ResourceLocation> blocks, Color palette1, Color palette2, Color palette3, Color palette4, Color palette5, Color palette6) {

    public static BurnPalettes parseSetting(JsonObject json) {
        Set<ResourceLocation> blocks = new HashSet<>();
        JsonElement blocksElement = json.get("blocks");
        if (blocksElement.isJsonPrimitive()) {
            blocks.add(ResourceLocation.parse(blocksElement.getAsString()));
        } else if (blocksElement.isJsonArray()) {
            JsonArray array = blocksElement.getAsJsonArray();
            for (JsonElement elem : array) {
                if (elem.isJsonPrimitive()) {
                    blocks.add(ResourceLocation.parse(elem.getAsString()));
                }
            }
        }
        Color p1 = hexToColor(json.get("palette1").getAsString());
        Color p2 = hexToColor(json.get("palette2").getAsString());
        Color p3 = hexToColor(json.get("palette3").getAsString());
        Color p4 = hexToColor(json.get("palette4").getAsString());
        Color p5 = hexToColor(json.get("palette5").getAsString());
        Color p6 = hexToColor(json.get("palette6").getAsString());
        return new BurnPalettes(blocks, p1, p2, p3, p4, p5, p6);
    }

    private static Color hexToColor(String hex) {
        if (hex.startsWith("#")) hex = hex.substring(1);
        if (hex.length() != 6) throw new IllegalArgumentException("Hex color must be in #RRGGBB format");
        return new Color(Integer.parseInt(hex, 16));
    }
}