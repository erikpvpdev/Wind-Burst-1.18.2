package code.erikpvp.windburstmod.event;


import code.erikpvp.windburstmod.Windburstmod;
import code.erikpvp.windburstmod.enchant.Enchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Windburstmod.MOD_ID)
public class Events {
    @SubscribeEvent
    public static void onPlayerAttackEntity(AttackEntityEvent event) {
        Player player = event.getPlayer();
        Level level = player.level;
        ItemStack stack = player.getMainHandItem();

        int enchantLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchants.WIND_BURST.get(), stack);

        if (enchantLevel > 0) {
            Entity target = event.getTarget();
            Vec3 hitPos = target.position();

            // Radius
            double radius = 5.0D;
            AABB area = new AABB(
                    hitPos.x - radius, hitPos.y - radius, hitPos.z - radius,
                    hitPos.x + radius, hitPos.y + radius, hitPos.z + radius
            );

            // Alle Mobs im Umkreis wegschleudern
            for (Entity e : level.getEntities(player, area, ent -> ent instanceof LivingEntity && ent != player)) {
                Vec3 kb = e.position().subtract(hitPos).normalize().scale(1.5).add(0, 0.6, 0);
                e.setDeltaMovement(kb);
                e.hurtMarked = true;
            }

            // Spieler selbst hochlaunchen -> Stärke abhängig von Enchant-Level
            double launchY = switch (enchantLevel) {
                case 1 -> 0.8D;  // Wind Burst I
                case 2 -> 1.2D;  // Wind Burst II
                case 3 -> 1.6D;  // Wind Burst III
                default -> 0.8D;
            };

            Vec3 currentMotion = player.getDeltaMovement();
            player.setDeltaMovement(currentMotion.x, launchY, currentMotion.z);
            player.hurtMarked = true;

            // Sound & Partikel
            level.playSound(null, hitPos.x, hitPos.y, hitPos.z,
                    SoundEvents.ENDER_DRAGON_FLAP, SoundSource.PLAYERS, 1.0F, 1.0F);

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(
                        ParticleTypes.CLOUD,
                        hitPos.x, hitPos.y, hitPos.z,
                        40,
                        1.0, 1.0, 1.0,
                        0.02
                );
            }
        }
    }
}
