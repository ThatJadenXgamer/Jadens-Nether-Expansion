package net.jadenxgamer.netherexp.core.entity

import net.jadenxgamer.netherexp.config.JNEConfigs
import net.jadenxgamer.netherexp.core.entity.interfaces.Bottleable
import net.jadenxgamer.netherexp.registry.JNEBlocks
import net.jadenxgamer.netherexp.registry.JNEItems
import net.jadenxgamer.netherexp.registry.JNEParticleTypes
import net.jadenxgamer.netherexp.registry.JNESoundEvents
import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.tags.ItemTags
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.damagesource.DamageSource
import net.minecraft.world.entity.AnimationState
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.PathfinderMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.control.FlyingMoveControl
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation
import net.minecraft.world.entity.ai.navigation.PathNavigation
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos
import net.minecraft.world.entity.ai.util.HoverRandomPos
import net.minecraft.world.entity.animal.FlyingAnimal
import net.minecraft.world.entity.monster.Monster
import net.minecraft.world.entity.monster.hoglin.Hoglin
import net.minecraft.world.entity.monster.piglin.Piglin
import net.minecraft.world.entity.monster.piglin.PiglinBrute
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.level.Level
import net.minecraft.world.level.LevelReader
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.pathfinder.PathType
import net.minecraft.world.phys.Vec3
import net.neoforged.api.distmarker.Dist
import net.neoforged.api.distmarker.OnlyIn
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder
import team.lodestar.lodestone.systems.particle.data.GenericParticleData
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType
import java.util.*

class Wisp(entityType: EntityType<out PathfinderMob>, level: Level) : ExorcismMob(entityType, level), FlyingAnimal, Bottleable {

    @JvmField val idleAnimation = AnimationState()
    private var idleAnimationTimeout = 0
    var boredCounter = 0
    var isSalted = false

    init {
        xpReward = 1
        setPathfindingMalus(PathType.LAVA, -1.0f)
        setPathfindingMalus(PathType.WATER, -1.0f)
        moveControl = FlyingMoveControl(this, 20, true)
    }

    override fun registerGoals() {
        goalSelector.addGoal(2, TemptGoal(this, 1.0, Ingredient.of(ItemTags.PIGLIN_REPELLENTS), false))
        goalSelector.addGoal(3, AvoidEntityGoal(this, Piglin::class.java, 8.0f, 0.4, 0.8))
        goalSelector.addGoal(3, AvoidEntityGoal(this, PiglinBrute::class.java, 8.0f, 0.4, 0.8))
        goalSelector.addGoal(3, AvoidEntityGoal(this, Hoglin::class.java, 8.0f, 0.4, 0.8))
        goalSelector.addGoal(4, BurrowInSoulSandGoal(this, 16))
        goalSelector.addGoal(4, WispWanderAroundGoal())
        goalSelector.addGoal(5, RandomLookAroundGoal(this))
    }

    override fun tick() {
        super.tick()
        if (level().isClientSide) setupAnimationStates()
    }

    override fun aiStep() {
        super.aiStep()
        if (level().isClientSide) {
            Client.trailParticle(JNEParticleTypes.WISP.get(), level(), random,
                getRandomX(0.5), randomY - 0.25, getRandomZ(0.5))
        } else if (canGetBored() && tickCount % 20 == 0
            && random.nextDouble() < JNEConfigs.WISP_BOREDOM_CHANCE.get()
            && boredCounter < 6) boredCounter++
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult =
        player.getItemInHand(hand).let { stack ->
            if (!isSalted && stack.`is`(Items.HONEYCOMB)) {
                (level() as? ServerLevel)?.run {
                    isSalted = true
                    stack.shrink(1)
                    repeat(4) {
                        sendParticles(ParticleTypes.WAX_ON, getRandomX(0.5), randomY - 0.25, getRandomZ(0.5), 1, 0.0, 0.0, 0.0, 0.0)
                    }
                }
                InteractionResult.SUCCESS
            } else {
                Bottleable.bottleMobPickup(player, hand, this) { Items.GLASS_BOTTLE }.orElse(super.mobInteract(player, hand))
            }
        }

    override fun canBeLeashed() = true
    override fun requiresCustomPersistence() = super.requiresCustomPersistence() || isSalted
    override fun removeWhenFarAway(distanceToClosestPlayer: Double) = canGetBored()
    override fun getWalkTargetValue(pos: BlockPos, level: LevelReader) = if (level.getBlockState(pos).isAir) 10.0f else 0.0f
    private fun canGetBored() = !hasCustomName() && !isSalted && !isLeashed
    override fun isFlying() = true
    override fun checkFallDamage(y: Double, onGround: Boolean, state: BlockState, pos: BlockPos) {}

    override fun createNavigation(level: Level): PathNavigation =
        object : FlyingPathNavigation(this, level) {
            override fun isStableDestination(pos: BlockPos) = !this.level.getBlockState(pos.below()).isAir
        }.apply {
            setCanOpenDoors(false)
            setCanFloat(false)
            setCanPassDoors(true)
        }

    // DATA //

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        super.defineSynchedData(builder)
        builder.define(FROM_BOTTLE, false)
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        super.addAdditionalSaveData(nbt)
        nbt.putBoolean("FromBottle", fromBottle())
        nbt.putInt("BoredCounter", boredCounter)
        nbt.putBoolean("Salted", isSalted)
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        super.readAdditionalSaveData(nbt)
        setFromBottle(nbt.getBoolean("FromBottle"))
        boredCounter = nbt.getInt("BoredCounter")
        isSalted = nbt.getBoolean("Salted")
    }

    override fun fromBottle(): Boolean = entityData.get(FROM_BOTTLE)
    override fun setFromBottle(fromBottle: Boolean) { entityData.set(FROM_BOTTLE, fromBottle) }
    override fun saveToBottleTag(stack: ItemStack?) = Bottleable.saveDefaultDataToBottleTag(this, stack)
    override fun loadFromBottleTag(tag: CompoundTag?) = Bottleable.loadDefaultDataFromBottleTag(this, tag)
    override fun getBottleItemStack() = ItemStack(JNEItems.WISP_BOTTLE.get())
    override fun getPickupSound(): SoundEvent? = JNESoundEvents.WISP_BOTTLE_FILL.get()

    // ANIMATIONS //

    private fun setupAnimationStates() {
        idleAnimationTimeout = if (idleAnimationTimeout <= 0) {
            idleAnimation.startIfStopped(tickCount); 80
        } else --idleAnimationTimeout
    }

    companion object {
        private val FROM_BOTTLE = SynchedEntityData.defineId(Wisp::class.java, EntityDataSerializers.BOOLEAN)

        @JvmStatic
        fun createAttributes(): AttributeSupplier.Builder =
            Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 2.0)
                .add(Attributes.FLYING_SPEED, 1.2)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 16.0)
    }

    // SOUNDS //

    override fun getAmbientSound() = JNESoundEvents.WISP_AMBIENT.get()
    protected override fun getHurtSound(damageSource: DamageSource) = JNESoundEvents.WISP_HURT.get()
    override fun getDeathSound() = JNESoundEvents.WISP_DEATH.get()

    // AI //

    inner class WispWanderAroundGoal : Goal() {
        init { flags = EnumSet.of(Flag.MOVE) }

        override fun canUse() = this@Wisp.navigation.isDone && this@Wisp.random.nextInt(10) == 0
        override fun canContinueToUse() = this@Wisp.navigation.isInProgress

        override fun start() {
            randomLocation?.let { navigation.moveTo(navigation.createPath(BlockPos.containing(it), 1), 1.0) }
        }

        private val randomLocation: Vec3?
            get() {
                val vec3 = this@Wisp.getViewVector(0.0f)
                return HoverRandomPos.getPos(this@Wisp, 8, 7, vec3.x, vec3.z, 1.5707964f, 3, 1)
                    ?: AirAndWaterRandomPos.getPos(this@Wisp, 8, 4, -2, vec3.x, vec3.z, 1.5707963705062866)
            }
    }

    inner class BurrowInSoulSandGoal(private val wisp: Wisp, range: Int) :
        MoveToBlockGoal(wisp, 1.0, range, range) {

        override fun acceptedDistance() = 2.0
        override fun isValidTarget(level: LevelReader, pos: BlockPos) = level.getBlockState(pos).`is`(Blocks.SOUL_SAND)
        override fun getMoveToTarget(): BlockPos = blockPos

        override fun tick() {
            super.tick()
            if (isReachedTarget) {
                val level = wisp.level()
                level.setBlock(getMoveToTarget(), JNEBlocks.ECTO_SOUL_SAND.get().defaultBlockState(), Block.UPDATE_ALL)
                (level as ServerLevel).sendParticles(
                    ParticleTypes.SOUL,
                    wisp.getRandomX(0.5), wisp.randomY - 0.25, wisp.getRandomZ(0.5),
                    5, 0.0, 0.0, 0.0, 0.0
                )
                wisp.remove(RemovalReason.DISCARDED)
            }
        }

        override fun canUse() = wisp.boredCounter > 5 && !wisp.isSalted && super.canUse()
        override fun canContinueToUse() = wisp.level().getBlockState(getMoveToTarget()).`is`(Blocks.SOUL_SAND) && super.canContinueToUse()
        override fun start() = super.start()
    }

    @OnlyIn(Dist.CLIENT)
    object Client {
        @JvmStatic
        fun trailParticle(particle: LodestoneWorldParticleType?, level: Level?, random: RandomSource, x: Double, y: Double, z: Double) {
            repeat(2) {
                WorldParticleBuilder.create(particle)
                    .setFullBrightLighting()
                    .setScaleData(GenericParticleData.create(0.13f).build())
                    .setTransparencyData(GenericParticleData.create(1f).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setLifetime(random.nextInt(40, 60))
                    .enableNoClip()
                    .spawn(level, x, y, z)
            }
        }
    }
}