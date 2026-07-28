package net.jadenxgamer.netherexp.core.entity

import net.jadenxgamer.netherexp.NetherExp
import net.jadenxgamer.netherexp.config.JNEConfigs
import net.jadenxgamer.netherexp.core.keys.JNETags
import net.jadenxgamer.netherexp.registry.JNEItems
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.syncher.EntityDataSerializers
import net.minecraft.network.syncher.SynchedEntityData
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.util.Mth
import net.minecraft.util.TimeUtil
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.*
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.ai.goal.*
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.gameevent.GameEvent
import net.minecraft.world.level.pathfinder.PathType
import net.minecraft.world.phys.Vec2
import net.minecraft.world.phys.Vec3
import net.neoforged.neoforge.common.CommonHooks
import net.neoforged.neoforge.common.ItemAbilities
import java.util.*
import java.util.function.Predicate
import kotlin.math.min
import kotlin.math.sqrt

class Stampede(entityType: EntityType<out PathfinderMob>, level: Level) :
    PossessedMob(entityType, level, NetherExp.minecraftPath("strider")),
    NeutralMob, Saddleable, PlayerRideableJumping {

    @JvmField val idleAnimation = AnimationState()
    @JvmField val grinAnimation = AnimationState()
    @JvmField val chewAnimation = AnimationState()

    private var idleAnimationTimeout = 0
    private var angerTime = 0
    private var angryAt: UUID? = null
    var playerJumpPendingScale = 0f

    var isStampedeJumping = false

    init {
        setPathfindingMalus(PathType.WATER, -1.0f)
        setPathfindingMalus(PathType.LAVA, 8.0f)
        setPathfindingMalus(PathType.DANGER_FIRE, 0.0f)
        xpReward = 28
    }

    override fun registerGoals() {
        goalSelector.addGoal(1, MeleeAttackGoal(this, 1.0, false))
        goalSelector.addGoal(2, MoveTowardsTargetGoal(this, 1.2, 32.0f))
        goalSelector.addGoal(3, RandomStrollGoal(this, 1.0))
        goalSelector.addGoal(3, PickupItemGoal())
        goalSelector.addGoal(4, RandomLookAroundGoal(this))
        goalSelector.addGoal(6, LookAtPlayerGoal(this, Player::class.java, 6.0f))
        targetSelector.addGoal(1, HurtByTargetGoal(this))
        targetSelector.addGoal(3, ResetUniversalAngerTargetGoal<Stampede?>(this, false))
    }

    override fun tick() {
        super.tick()
        if (level().isClientSide) this.setupAnimationStates()
    }

    override fun aiStep() {
        super.aiStep()
        if (isMoving()) trampleEntities()
    }

    private fun trampleEntities() {
        val box = boundingBox
        val height = box.ysize
        level().getEntities(this, box, EntitySelector.NO_CREATIVE_OR_SPECTATOR)
            .filterIsInstance<LivingEntity>()
            .filterNot { it === this || passengers.contains(it) }
            .forEach { victim ->
                if (victim.boundingBox.ysize < height) {
                    val vel = deltaMovement
                    val speed = sqrt(vel.x * vel.x + vel.z * vel.z)
                    val damage = (getAttributeValue(Attributes.ATTACK_DAMAGE) * (0.5 + speed * 2)).toFloat()
                    victim.hurt(damageSources().mobAttack(this), damage)
                    if (speed > 0.1) {
                        val knockback = getAttributeValue(Attributes.ATTACK_KNOCKBACK)
                        victim.push(vel.x * knockback, 0.3, vel.z * knockback)
                    }
                    level().playSound(null, blockPosition(), SoundEvents.RAVAGER_STEP, soundSource, 1.0f, 0.8f + random.nextFloat() * 0.4f)
                }
            }
    }

    override fun mobInteract(player: Player, hand: InteractionHand): InteractionResult {
        val stack = player.getItemInHand(hand)
        if (level().isClientSide) return super.mobInteract(player, hand)
        return when {
            stack.`is`(JNETags.Items.STAMPEDE_EDIBLE) && getItemBySlot(EquipmentSlot.MAINHAND).isEmpty -> {
                setItemSlot(EquipmentSlot.MAINHAND, stack.copyWithCount(1))
                if (!player.abilities.instabuild) stack.shrink(1)
                InteractionResult.SUCCESS
            }
            isSaddled -> when {
                stack.canPerformAction(ItemAbilities.SHEARS_REMOVE_ARMOR) -> {
                    isSaddled = false
                    player.level().playSound(null, player.blockPosition(), SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0f, 1.0f)
                    if (!player.abilities.instabuild) stack.hurtAndBreak(1, player, getSlotForHand(hand))
                    spawnAtLocation(Items.SADDLE)
                    InteractionResult.SUCCESS
                }
                !isVehicle && !player.isSecondaryUseActive -> {
                    player.startRiding(this)
                    InteractionResult.SUCCESS
                }
                else -> super.mobInteract(player, hand)
            }
            isSaddleable && !isSaddled && stack.`is`(Items.SADDLE) -> {
                equipSaddle(stack.split(1), SoundSource.NEUTRAL)
                level().gameEvent(this, GameEvent.EQUIP, this.position())
                InteractionResult.SUCCESS
            }
            else -> super.mobInteract(player, hand)
        }
    }

    override fun dropEquipment() = run { super.dropEquipment(); if (isSaddled) spawnAtLocation(Items.SADDLE) }

    override fun getPassengerAttachmentPoint(entity: Entity, dimensions: EntityDimensions, partialTick: Float): Vec3 {
        val f = walkAnimation.position()
        val g = 0.12f * Mth.cos(f * 1.5f) * 2.0f * min(0.25f, walkAnimation.speed())
        return super.getPassengerAttachmentPoint(entity, dimensions, partialTick).add(0.0, (g * partialTick).toDouble(), 0.0)
    }

    override fun requiresCustomPersistence() = super.requiresCustomPersistence() || isTamed()
    override fun removeWhenFarAway(pDistanceToClosestPlayer: Double) = !isTamed() && !hasCustomName()

    override fun doHurtTarget(target: Entity): Boolean {
        if (random.nextDouble() < JNEConfigs.STAMPEDE_STRIDITE_SHEDDING_CHANCE.get()) dropStridite()
        return super.doHurtTarget(target)
    }

    private fun dropStridite() {
        val count = Mth.randomBetweenInclusive(random, JNEConfigs.MIN_STAMPEDE_STRIDITE_DROPS.get(), JNEConfigs.MAX_STAMPEDE_STRIDITE_DROPS.get())
        spawnAtLocation(ItemStack(JNEItems.STRIDITE.get(), count))
    }

    // RIDING //

    override fun onPlayerJump(jumpPower: Int) {
        if (isSaddled()) {
            val p = jumpPower.coerceAtLeast(0)
            playerJumpPendingScale = if (p >= 90) 1.0f else 0.4f + 0.4f * p / 90.0f
        }
    }

    override fun tickRidden(player: Player, travelVector: Vec3) {
        super.tickRidden(player, travelVector)
        val vec2 = getRiddenRotation(player)
        setRot(vec2.y, vec2.x)
        yHeadRot = yRot
        yBodyRot = yHeadRot
        yRotO = yBodyRot
        if (isControlledByLocalInstance && onGround()) {
            isStampedeJumping = false
            if (playerJumpPendingScale > 0.0f) executeRidersJump(playerJumpPendingScale, travelVector)
            playerJumpPendingScale = 0.0f
        }
    }

    override fun getRiddenInput(player: Player, travelVector: Vec3): Vec3 {
        val x = player.xxa * 0.5f
        var z = player.zza
        if (z <= 0.0f) z *= 0.25f
        return Vec3(x.toDouble(), 0.0, z.toDouble())
    }

    fun executeRidersJump(playerJumpPendingScale: Float, travelVector: Vec3) {
        val jumpPower = getJumpPower(playerJumpPendingScale).toDouble()
        val vel = deltaMovement
        setDeltaMovement(vel.x, jumpPower, vel.z)
        isStampedeJumping = true
        hasImpulse = true
        CommonHooks.onLivingJump(this)
        if (travelVector.z > 0.0) {
            val f = Mth.sin(yRot * 0.017453292f)
            val scale = -0.4f * f * playerJumpPendingScale
            deltaMovement = vel.add(scale.toDouble(), 0.0, scale.toDouble())
        }
    }

    // DATA //

    override fun defineSynchedData(builder: SynchedEntityData.Builder) {
        super.defineSynchedData(builder)
        builder.define(SADDLED, false)
        builder.define(TAMED, false)
        builder.define(ANGRY, false)
        builder.define(EATING, false)
        builder.define(IS_PATRICK, false)
        builder.define(HUNGER, 0)
    }

    override fun addAdditionalSaveData(nbt: CompoundTag) {
        super.addAdditionalSaveData(nbt)
        nbt.putBoolean("Saddled", entityData.get(SADDLED))
        nbt.putBoolean("Tamed", entityData.get(TAMED))
        nbt.putBoolean("Angry", entityData.get(ANGRY))
        nbt.putBoolean("IsPatrick", entityData.get(IS_PATRICK))
        nbt.putInt("Hunger", entityData.get(HUNGER))
    }

    override fun readAdditionalSaveData(nbt: CompoundTag) {
        super.readAdditionalSaveData(nbt)
        entityData.set(SADDLED, nbt.getBoolean("Saddled"))
        entityData.set(TAMED, nbt.getBoolean("Tamed"))
    }

    override fun isPushable() = false
    override fun doPush(entity: Entity) {}

    fun isMoving() = deltaMovement.horizontalDistance() > 0.02f
    override fun isSaddled(): Boolean = entityData.get(SADDLED)
    fun setSaddled(saddled: Boolean) = entityData.set(SADDLED, saddled)
    fun isTamed(): Boolean = entityData.get(TAMED)
    fun setTamed(tamed: Boolean) = entityData.set(TAMED, tamed)
    fun getRiddenRotation(entity: LivingEntity) = Vec2(entity.xRot * 0.5f, entity.yRot)

    override fun isSaddleable() = isAlive && isTamed()
    override fun equipSaddle(stack: ItemStack, soundSource: SoundSource?) { setSaddled(true) }
    override fun canJump(): Boolean = isSaddled()
    override fun handleStartJump(jumpPower: Int) = playJumpSound()
    override fun handleStopJump() {}

    override fun getRemainingPersistentAngerTime() = angerTime
    override fun setRemainingPersistentAngerTime(angerTime: Int) { this.angerTime = angerTime }
    override fun getPersistentAngerTarget() = angryAt
    override fun setPersistentAngerTarget(angryAt: UUID?) { this.angryAt = angryAt }
    override fun startPersistentAngerTimer() { remainingPersistentAngerTime = ANGER_TIME_RANGE.sample(random) }

    override fun apparitionPersonality(): Int = 2
    override fun apparitionUnleashingOdds(): Double = JNEConfigs.STAMPEDE_UNLEASHING_ODDS.get()

    // ANIMATIONS //

    private fun setupAnimationStates() {
        idleAnimationTimeout = if (idleAnimationTimeout <= 0) {
            idleAnimation.startIfStopped(tickCount); 40
        } else --idleAnimationTimeout
    }

    override fun updateWalkAnimation(partialTick: Float) =
        walkAnimation.update(if (pose == Pose.STANDING) min(partialTick * 6.0f, 1.0f) else 0.0f, 0.2f)

    // SOUNDS //

    fun playJumpSound() = playSound(SoundEvents.HORSE_JUMP, 0.4f, 1.0f)

    companion object {
        private val PREDICATE_DROP_FILTER = Predicate<ItemEntity> {
            !it.hasPickUpDelay() && it.isAlive && it.item.`is`(JNETags.Items.STAMPEDE_EDIBLE)
        }

        private val SADDLED = SynchedEntityData.defineId(Stampede::class.java, EntityDataSerializers.BOOLEAN)
        private val TAMED = SynchedEntityData.defineId(Stampede::class.java, EntityDataSerializers.BOOLEAN)
        private val HUNGER = SynchedEntityData.defineId(Stampede::class.java, EntityDataSerializers.INT)
        private val ANGRY = SynchedEntityData.defineId(Stampede::class.java, EntityDataSerializers.BOOLEAN)
        private val EATING = SynchedEntityData.defineId(Stampede::class.java, EntityDataSerializers.BOOLEAN)
        private val IS_PATRICK = SynchedEntityData.defineId(Stampede::class.java, EntityDataSerializers.BOOLEAN)
        private val ANGER_TIME_RANGE = TimeUtil.rangeOfSeconds(20, 39)

        @JvmStatic
        fun createAttributes(): AttributeSupplier.Builder =
            createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80.0)
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.ATTACK_DAMAGE, 8.0)
                .add(Attributes.ATTACK_KNOCKBACK, 4.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 3.0)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.STEP_HEIGHT, 2.5)
    }

    // AI //

    inner class PickupItemGoal(): Goal() {

        var targetItem: ItemEntity? = null;

        init { flags = EnumSet.of(Flag.MOVE) }

        override fun canUse() = nearbyGroundItems().isNotEmpty() && with(this@Stampede) {
            !isVehicle && target == null && random.nextInt(10) == 0 && getItemBySlot(EquipmentSlot.MAINHAND).isEmpty
        }

        override fun start() = run { targetItem = nearbyGroundItems()[0] }
        override fun canContinueToUse(): Boolean = targetItem?.isAlive == true && super.canContinueToUse()

        override fun tick() {
            if (targetItem == null) return;
            this@Stampede.getNavigation().moveTo(targetItem!!, 1.2000000476837158)
        }

        private fun nearbyGroundItems(): MutableList<ItemEntity?> = with(this@Stampede) {
            level().getEntitiesOfClass(ItemEntity::class.java,
                boundingBox.inflate(8.0, 8.0, 8.0),
                PREDICATE_DROP_FILTER
            )
        }
    }
}