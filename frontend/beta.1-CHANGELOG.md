# 2.4.0-BETA.1 - [EARLY ACCESS VERSION OF THE 1.21.1 PORT]

### Major Changes
-Updated to NeoForge 1.21.1

-Now requires Elysium API 1.2.0+ to run this mod

-JNE codebase was rewritten from the ground-up for this port. expect many performance improvements and bug fixes!

### Changes

-The Nether world generation has been entirely overhauled from the ground up

-Speleothems can now generate in the nether to make it look more cavernous

-The Nether is now 192 blocks tall, with an additional -32 blocks for the future underlava expansion

-The dimension is also much more multilayered and less floaty overall

-Removed emissive built-in resource pack. all emissives are now part of the mod resources and always active

-Entirely Overhauled the mod's configurations, JNE has never been this customizable before!

-Soul Magma Block texture has received a glow-up, it now tiles much better with its surroundings

-Soul Magma also emits new particles if you sprint on it to better convey what's damaging you

-Ancient Wax Block texture has received a glow-up, no longer looks like bricks

-Shotgun-Fist now no longer uses wraithing flesh as ammo but rather the new Shotgun-Shells instead

-Shotgun Shells are crafted with wraithing flesh, iron nuggets and any coal-like item. It is the new ammunition for shotguns

-Slug Shotgun Shells are a variant of shotgun-shells crafted with blaze powder. It can destroy blocks and easily create new openings; deals knockback to entities

-Phasmo Shotgun Shells are another variant of shotgun-shells crafted with phasmo shards. It can phase through blocks

-Thin Black Ice can now shatter in a chain reaction if one breaks

-Certain mobs (usually light ones) can now stand on thin black ice without shattering it 

-Banshees now produce smoke particles behind their orbit

-Will O' Wisps seeking logic has been overhauled, it now gradually gets faster overtime and less accurate with sharp turns

-Will O' Wisps now also have a 3D model with new and improved particles!

-Apparitions, Wisps, Banshees and Blazes no longer have shade and look similar to bedrock edition emissive mobs

-Banshees now get stunned upon getting hit with their own projectiles to make encounters with them more stragetic

-Several new visual effects have been added to the nether to make it more atmospheric, alongside changes to their fog colors to further add to their etheral look

-Soul Swirls no longer drop themselves if sheared when activated

-Bone Rod has been renamed to "Bone Pike"

-Bone Corticals have been removed. all instances of this block from older worlds will automatically convert to regular vanilla bone blocks

-New Advancement "Guns for Hands" duel a vessel with your very own shotgun

-New Advancement "Involuntary Eviction" forcefully remove a wisp out of ecto soul sand by brushing it

-Soul Sand Valleys and their sub-biomes now spawn cold frog variants opposed to warm ones

-Snow Golems no longer melt in the Soul Sand Valley or its sub-biomes either

-2 New Immunities were added, Slow Falling Immunity and Infesting Immunity, currently unbrewable due to antidotes not being implemented

-Sorrowsquash stem hitboxes now extend to encompasses the entire thing when attached

-Beacon glass texture has been updated to incorporate better shading

-Breeze Rod texture has been redesigned to be consistent with the new blaze rod and banshee rod

-Apparitions can now also turn into a banshee if they happen to possess a breeze

-Apparition Aggressions is a new data-driven registry which allows you to define custom hostility towards certain mobs depending on the apparition's personality 

-The Apparition Aggression system also allows you to specify custom possessions when an apparition kills a certain mob

-Apparition Gargoyle Statues is also a new data-driven registry which allows you to define custom gargoyle statue-like possessions for apparitions

-Apparition now has a new attack animation and particles for when it possesses a mob

-Apparitions can now be waxed to prevent it from possessing mobs or gargoyle statues

-Possessed mobs made from an apparition possessing an entity now retain the information of what EntityType they use to be for reverting in exorcisms

-To make it easier for people to make possessions, apparitions can be "transported" with leashes. although unless it's docile it'll still fight back

-Discernment Glass now emits a comparator output if it has a filter item

-Weeping and Twisting Blackstone Bricks were missing slabs, stairs and wall variants due to an oversight. these have now been implemented

-Warped Forests and Basalt Deltas now have slightly denser fog

-Soul Glass & Discernment Glass have received new sounds

-Netherite Grate and Rusty Netherite Grate have received new sounds

-Ancient Wax Block has received new sounds

-Ancient Candle has received new sounds

-Wither Bone Blocks have received new sounds

-Bone Pike and Bone Fence have received new sounds

-Immunity Effects now have a distinct new sound effect for when they run out

-Geysers have received new sounds and shoot out particles at high velocity when stepped on

-Apparitions now have new possession sounds

-Lava now has a perlin noise gradient applied to it, making it look less mentonious

-New windy dust particles have been added to the soul sand valley which replace the usual static ash particles now

-Fire has received new particles and their each indevidual colors can be edited through a Resource Pack, allowing for mod-compat

-Vessel's AI has been overhauled, their aim is now more perfect the closer they are to the target; as indicated by a yellow flash before firing

-To accomdiate for the above change, their aim is also now worse at medium-to-far ranges, they now also become stationary upon firing to allow for easier hits

-Many new particles such as a cooldown smoke, shotgun shoot flash, pellet impacts, trails and more have been given to both the Vessel and Shotgun-Fist

-Updated German Translation

-***AND MORE I PROBABLY FORGOT TO MENTION LMAO***

### Mod Compatibility

-Possessed and Ghost mobs now take 1.5x damage modifier if hurt with any modded silver weapon

-To further convey that possessions and ghost mobs are weak to silver, new particles show up when they are damaged with it

-Item Tags defining Silver Armors and Weapons are now located under the `c:` common tags namespace shared across NeoForge and Fabric for better compatibility

-Galosphere's Sterling armor pieces are now counted as silver armor and thus effective against ghosts and possessions

### Fixes

-Treacherous Candle spawn and round sounds didn't have subtitles, this has now been fixed

-Claret was missing from the #minecraft:log and #minecraft:non_flammable_wood tags, this has now been fixed

-Sporeshrooms were unable to be duplicated with bone meal when hanging upside-down, this has now been fixed

-Suspicious Soul Sand had a chance to immediately decay upon being created, now there is an actual decay counter for tracking and preventing immediate decay

-Immunity Effect particles now show up on other entities inflicted with immunities and not just yourself

-Items cooking on an ancient campfire now display properly

-Banshees now instantly die if they are hit with their own will o wisp

