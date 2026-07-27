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
import net.minecraft.network.syncher.EntityDataAccessor
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvent
import net.minecraft.tags.ItemTags
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.AnimationState
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
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

class Wisp(entityType: EntityType<out PathfinderMob>, level: Level): ExorcismMob(entityType, level), FlyingAnimal, Bottleable {

    @JvmField var idleAnimation: AnimationState = AnimationState()
    private var idleAnimationTimeout = 0
    var boredCounter: Int = 0
    var isSalted: Boolean = false

    init {
        this.xpReward = 1
        this.setPathfindingMalus(PathType.LAVA, -1.0f); this.setPathfindingMalus(PathType.WATER, -1.0f)
        this.moveControl = FlyingMoveControl(this, 20, true)
    }

    override fun registerGoals() {
        this.goalSelector.addGoal(2, TemptGoal(this, 1.0, Ingredient.of(ItemTags.PIGLIN_REPELLENTS), false))
        this.goalSelector.addGoal(3, WispAvoidEntityGoal<Piglin>(this, Piglin::class.java, 8.0f, 0.4, 0.8))
        this.goalSelector.addGoal(3, WispAvoidEntityGoal<PiglinBrute>(this, PiglinBrute::class.java, 8.0f, 0.4, 0.8))
        this.goalSelector.addGoal(3, WispAvoidEntityGoal<Hoglin>(this, Hoglin::class.java, 8.0f, 0.4, 0.8))
        this.goalSelector.addGoal(4, BurrowInSoulSandGoal(this, 16))
        this.goalSelector.addGoal(4, WispWanderAroundGoal())
        this.goalSelector.addGoal(5, RandomLookAroundGoal(this))
    }

    override fun tick() { super.tick(); if (level().isClientSide) setupAnimationStates() }
    override fun aiStep() {
        super.aiStep()
        if (this.level().isClientSide()) {
            Wisp.Client.trailParticle(
                JNEParticleTypes.WISP.get(), level(), random,
                this.getRandomX(0.5), this.randomY - 0.25, this.getRandomZ(0.5)
            )
        } else {
            if (!canGetBored()) return
            if (this.tickCount % 20 == 0 && random.nextDouble() < JNEConfigs.WISP_BOREDOM_CHANCE.get() && boredCounter < 6) ++boredCounter
        }
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {
        return player.getItemInHand(hand).let { stack ->
            if (!isSalted && stack.`is`(Items.HONEYCOMB)) {
                (level() as? ServerLevel)?.let { serverLevel ->
                    isSalted = true
                    stack.shrink(1)
                    repeat(4) {
                        serverLevel.sendParticles(ParticleTypes.WAX_ON,
                            getRandomX(0.5), randomY - 0.25, getRandomZ(0.5),
                            1, 0.0, 0.0, 0.0, 0.0
                        )
                    }
                }
                InteractionResult.SUCCESS
            } else Bottleable.bottleMobPickup(player, hand, this) { Items.GLASS_BOTTLE }.orElse(super.mobInteract(player, hand))
        }
    }

    override fun canBeLeashed(): Boolean = true
    override fun requiresCustomPersistence(): Boolean =super.requiresCustomPersistence() || isSalted
    override fun removeWhenFarAway(distanceToClosestPlayer: Double): Boolean = canGetBored()
    override fun getWalkTargetValue(pos: BlockPos, level: LevelReader): Float = if (level.getBlockState(pos).isAir) 10.0f else 0.0f
    private fun canGetBored(): Boolean = !hasCustomName() && !isSalted && !isLeashed
    override fun isFlying(): Boolean = true

    override fun checkFallDamage(y: Double, onGround: Boolean, state: BlockState, pos: BlockPos) {}

    override fun createNavigation(level: Level): PathNavigation {
        val navigation = object : FlyingPathNavigation(this, level) {
            override fun isStableDestination(pos: BlockPos): Boolean = !this.level.getBlockState(pos.below()).isAir
        }
        navigation.setCanOpenDoors(false)
        navigation.setCanFloat(false)
        navigation.setCanPassDoors(true)
        return navigation
    }

    // DATA //

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        super.defineSynchedData(builder); builder.define(FROM_BOTTLE, false)
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

    override fun fromBottle(): Boolean = this.entityData.get<Boolean>(FROM_BOTTLE)
    override fun setFromBottle(fromBottle: Boolean) = this.entityData.set<Boolean>(FROM_BOTTLE, fromBottle)
    override fun saveToBottleTag(stack: ItemStack?) = Bottleable.saveDefaultDataToBottleTag(this, stack)
    override fun loadFromBottleTag(tag: CompoundTag?) = Bottleable.loadDefaultDataFromBottleTag(this, tag)
    override fun getBottleItemStack(): ItemStack = ItemStack(JNEItems.WISP_BOTTLE.get())
    override fun getPickupSound(): SoundEvent? = JNESoundEvents.WISP_BOTTLE_FILL.get()

    // ANIMATIONS //

    private fun setupAnimationStates() {
        idleAnimationTimeout = when {
            idleAnimationTimeout <= 0 -> { idleAnimation.start(tickCount); 80 }
            else -> --idleAnimationTimeout
        }
    }

    companion object {
        private val FROM_BOTTLE: EntityDataAccessor<Boolean?> = SynchedEntityData.defineId<Boolean?>(Wisp::class.java, EntityDataSerializers.BOOLEAN)

        @JvmStatic
        fun createAttributes(): AttributeSupplier.Builder {
            return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 2.0)
                .add(Attributes.FLYING_SPEED, 1.2)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.FOLLOW_RANGE, 16.0)
        }
    }

    // AI //

    class WispAvoidEntityGoal<T : LivingEntity>(wisp: Wisp, fleeFromType: Class<T>, distance: Float, slowSpeed: Double, fastSpeed: Double) :
        AvoidEntityGoal<T>(wisp, fleeFromType, distance, slowSpeed, fastSpeed)

    inner class WispWanderAroundGoal internal constructor() : Goal() {
        init {
            this.flags = EnumSet.of(Flag.MOVE)
        }

        override fun canUse(): Boolean = this@Wisp.navigation.isDone && this@Wisp.random.nextInt(10) == 0

        override fun canContinueToUse(): Boolean = this@Wisp.navigation.isInProgress

        override fun start() {
            val vec3 = this.randomLocation
            if (vec3 != null) this@Wisp.navigation.moveTo(this@Wisp.navigation.createPath(BlockPos.containing(vec3), 1), 1.0)
        }

        private val randomLocation: Vec3?
            get() {
                val vec3 = this@Wisp.getViewVector(0.0f)
                val vec32 = HoverRandomPos.getPos(this@Wisp, 8, 7, vec3.x, vec3.z, 1.5707964f, 3, 1)
                return vec32 ?: AirAndWaterRandomPos.getPos(this@Wisp, 8, 4, -2, vec3.x, vec3.z, 1.5707963705062866)
            }
    }

    inner class BurrowInSoulSandGoal(private val wisp: Wisp, range: Int) :
        MoveToBlockGoal(wisp, 1.0, range, range) {

        override fun acceptedDistance(): Double = 2.0
        override fun isValidTarget(level: LevelReader, pos: BlockPos): Boolean = level.getBlockState(pos).`is`(Blocks.SOUL_SAND)
        override fun getMoveToTarget(): BlockPos = blockPos

        override fun tick() {
            super.tick()
            val target = getMoveToTarget()
            val level = wisp.level()
            if (isReachedTarget) {
                level.setBlock(target, JNEBlocks.ECTO_SOUL_SAND.get().defaultBlockState(), Block.UPDATE_ALL)
                (level as ServerLevel).sendParticles(
                    ParticleTypes.SOUL,
                    wisp.getRandomX(0.5),
                    wisp.randomY - 0.25,
                    wisp.getRandomZ(0.5),
                    5, 0.0, 0.0, 0.0, 0.0
                )
                wisp.remove(RemovalReason.DISCARDED)
            }
        }

        override fun canUse(): Boolean = wisp.boredCounter > 5 && !wisp.isSalted && super.canUse()
        override fun canContinueToUse(): Boolean = wisp.level().getBlockState( getMoveToTarget()).`is`(Blocks.SOUL_SAND) && super.canContinueToUse()
        override fun start() = super.start()
    }

    @OnlyIn(Dist.CLIENT)
    object Client {

        @JvmStatic
        fun trailParticle(particle: LodestoneWorldParticleType?, level: Level?, random: RandomSource,
            x: Double, y: Double, z: Double) {
            for (i in 0 until 2) {
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