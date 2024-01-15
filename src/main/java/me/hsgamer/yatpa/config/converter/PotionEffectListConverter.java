package me.hsgamer.yatpa.config.converter;

import com.cryptomorin.xseries.XPotion;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.annotation.converter.Converter;

import java.util.ArrayList;
import java.util.List;

public class PotionEffectListConverter implements Converter {
    @Override
    public Object convert(Object o) {
        if (o == null) return null;
        return XPotion.parseEffects(CollectionUtils.createStringListFromObject(o, true));
    }

    @Override
    public Object convertToRaw(Object o) {
        if (o instanceof List) {
            List<String> list = new ArrayList<>();
            ((List<?>) o).forEach(o1 -> {
                if (o1 instanceof XPotion.Effect) {
                    XPotion.Effect effect = (XPotion.Effect) o1;
                    StringBuilder builder = new StringBuilder();
                    builder
                            .append(effect.getXPotion().name()).append(", ")
                            .append(effect.getEffect().getDuration() / 20).append(", ")
                            .append(effect.getEffect().getAmplifier());
                    if (effect.getChance() < 100) {
                        builder.append(" ").append(effect.getChance()).append("%");
                    }
                    list.add(builder.toString());
                }
            });
            return list;
        }
        return null;
    }
}
