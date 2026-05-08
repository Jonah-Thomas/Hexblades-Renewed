package alexthw.hexblades.spells;

import elucent.eidolon.api.deity.Deity;
import elucent.eidolon.api.spells.Sign;
import elucent.eidolon.common.spell.PrayerSpell;
import net.minecraft.resources.ResourceLocation;

/**
 * In Eidolon-Repraised, PrayerSpell already provides the full prayer logic
 * (finding the effigy, checking cooldown, granting reputation). HexPrayerSpell
 * simply delegates to PrayerSpell with the given deity.
 *
 * The 1.16 version manually reimplemented all of this; the 1.20.1 port just
 * extends PrayerSpell directly so the superclass handles everything.
 */
public class HexPrayerSpell extends PrayerSpell {

    public HexPrayerSpell(ResourceLocation name, Deity deity, Sign... signs) {
        super(name, deity, signs);
    }

}
