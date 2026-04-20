# 2.4.0 - NeoForge 1.21.1 Port

### Major Changes
-Updated to NeoForge 1.21.1

-Now requires Elysium API 1.2.0+ to run this mod

-JNE codebase was rewritten from the ground-up for this port. expect many performance improvements and bug fixes!

-JNE now depends on Lodestone for its rendering!

### Changes

-The Nether world generation has been entirely overhauled from the ground up

-Speleothems can now generate in the nether to make it look more cavernous

-The Nether is now 192 blocks tall, with an additional -32 blocks for the future underlava expansion

-The dimension is also much more multilayered and less floaty overall

-Removed emissive built-in resource pack. all emissives are now part of the mod resources and always active

-Entirely Overhauled the mod's configurations, JNE has never been this customizable before!

-An astonishing number of visual overhauls have been added to the mod to make the nether truly feel breathtaking

-A mist particle now generates all around the nether air not too dissimilar to the ones from Complimentary shaders to make the dimension's atmosphere feel more humid and alive

-Heat Distortion is also present on far away objects which gets more intense depending on if you're near lava; all heat distortion can be reduced or turned off within the configs

-Humidifier is a new redstone block that allows you to either disable or enable heat distortion in a given area, the range is dependent on the signal strength being inputted into the block while the heat state can be toggled

-Silt is a new gravity block that replaces the usual gravel shores and patches in the nether, along with this change nether beaches also encompass more of the lower regions

-Silt Flint Ores have also been added to still ensure flint is obtainable in the nether with the removal of gravel shores

-Siltbarrams are beach grass-like "plants" which change their appearance based on biome's humidity; it has Dry, Moist and Damp variants and their states can be locked by shearing them

-Soul Magma Block texture has received a glow-up, it now tiles much better with its surroundings

-Soul Magma also emits new particles if you sprint on it to better convey what's damaging you

-Ancient Wax Block texture has received a glow-up, no longer looks like bricks

-Shotgun-Fist now no longer uses wraithing flesh as ammo but rather the new Shotgun-Shells instead

-Shotgun Shells are crafted with wraithing flesh, iron nuggets and any coal-like item. It is the new ammunition for shotguns

-Slug Shotgun Shells are a variant of shotgun-shells crafted with blaze powder. It can destroy blocks and easily create new openings; deals knockback to entities

-Phasmo Shotgun Shells are another variant of shotgun-shells crafted with phasmo shards. It can phase through blocks

-Blank Shotgun Shells do not shoot any projectiles but rather exist purely for mobility usage, they also do not increase cooldown when used with Counterforce

-Barrage Enchantment has been renamed to "Volley"

-Artemis Enchantment has been renamed to "Longshot"

-Cartridge Enchantment has been removed

-Counterforce is a new treasure enchantment for all shotgun weapons that when used during a jump reverses one's self-knockback when the weapon is fired, letting you dash forwards

-The dash also provides 1 second of I-Frames indicated by a screen glow; any ammo that is not a blank will add 1.5 seconds of additional cooldown

-Shotguns all now have new particles for firing and when on cooldown; as well as actual pellet trails, impact sounds and visuals

-Shotgun-Fists can now be repaired with Netherite Scraps in an anvil

-All pellets now have a unique 3D model opposed to just reusing the arrow cross with a flat texture

-Ecto Slabs now produce obvious rays of light when they are underground to make them less annoying to keep track of

-Ecto Slabs now rarely spawn naturally in the soul sand valley instead of soul swirls. To accommodate this, their detection radius has also been greatly reduced -X

-Ecto Slabs can however still detect entities inflicted with unbounded speed from as far as 128 blocks away and B-line towards them

-Stampedes can now naturally spawn very rarely in the soul sand valley -X

-Stampedes' hunger meter UI has also received a redesign to make it more obvious as to what it is -X

-Thin Black Ice can now shatter in a chain reaction if one breaks

-Certain mobs (usually light ones) can now stand on thin black ice without shattering it 

-Banshees now produce smoke particles behind their orbit

-Left-handed banshees rod now orbits counter-clockwise instead of the usual clockwise

-Will O' Wisps seeking logic has been overhauled, it now gradually gets faster overtime and less accurate with sharp turns

-Will O' Wisps now also have a 3D model with new and improved particles!

-Will O' Wisps can be propelled to reach their maximum speed immediately by hitting them with a wind charge

-Apparitions, Wisps, Banshees and Blazes no longer have shade and look similar to bedrock edition emissive mobs

-Many nether biomes had their fog colors slightly tweaked to make them look brighter and appealing, this can be disabled within the configs to bring back their old colors

-The Nether Wastes fog is now orange instead of red to further differentiate from crimson forests

-Striders now have new humidity based skin variants depending on which biome they're found in; the variants are Damp, Moist and Dry -X

-Baby Striders are now Straggots, a much more maggot-like creature that have yet to grow legs so they wiggle around or seek transport on their parents backs

-Breeding Striders now instead produce larvae that they grow on their backs, these larvae can be harvested with shears to independently grow on lava or let them naturally grow on their parents

-A "Pregnant Strider" cannot be ridden as it'll break the larvae

-Striders can be sheared for their string; sheared striders will not grow back their hair to discourage farming string this way; it is merely a pacifist alternative for the resource

-Very rarely a Straggot can grow up into an adult that hasn't shed its skin properly leading to a White "Flakey" variant that sheds particles whenever it moves

-Walking striders on rough surfaces can occasionally cause them to shed Stridite; this method of obtaining is far less superior to a stampede but is an alternative

-Soul Swirls no longer drop themselves if sheared when activated to prevent abuse of Unbounded Speed

-Bone Rod has been renamed to "Bone Pike"

-Bone Corticals have been removed. all instances of this block from older worlds will automatically convert to regular vanilla bone blocks

-Wretched Gargoyle Statue has received a new model -X

-New Advancement "Why are you hitting yourself?" juke a will o' wisp into hitting the banshee which shot it

-New Advancement "Guns for Hands" kill a vessel with a bed explosion

-New Advancement "Ghosts Busted" kill one of each possessed and ghost mob

-New Advancement "Involuntary Eviction" forcefully remove a wisp out of ecto soul sand by brushing it

-New Advancement "99 Blue Balloons" leash an apparition and have fun with your new sentient balloon

-The Buckshot Wonderland Music Disc has been renamed to just "Buckshot" as per the request of the composer <3 -X

-Buckshot Music Disc is now much rarer in brazier chests and has an alternative obtaining method when a vessel kills a creeper -X

-Soul Sand Valleys and their sub-biomes now spawn cold frog variants opposed to warm ones

-Snow Golems no longer melt in the Soul Sand Valley or its sub-biomes either

-2 New Immunities have been added, Slow Falling Immunity and Infested Immunity brewed with Wool and Obsidian respectively

-Sorrowsquash stem hitboxes now extend to encompasses the entire thing when attached

-Beacon glass texture has been updated to incorporate better shading

-Breeze Rod texture has been redesigned to be consistent with the new blaze rod and banshee rod

-Fire particles have been entirely overhauled to have more appealing smoke and embers; they are fully resource-pack driven and more can be added as mod compat very easily!

-Apparitions can now also turn into a banshee if they happen to possess a Breeze

-Apparition Aggressions is a new data-driven registry which allows you to define custom hostility towards certain mobs depending on the apparition's personality 

-The Apparition Aggression system also allows you to specify custom possessions when an apparition kills a certain mob

-Apparition Gargoyle Statues is also a new data-driven registry which allows you to define custom gargoyle statue-like possessions for apparitions

-Apparition now has a new attack animation and particles for when it possesses a mob

-Apparitions can now be salted to prevent it from possessing mobs or gargoyle statues; Honeycombs are a temporary item until actual Nether Salt is added in the future

-Possessed mobs made from an apparition possessing an entity now retain the information of what EntityType they use to be for reverting in exorcisms

-To make it easier for people to make possessions, apparitions can be "transported" with leashes. although unless it's docile it'll still fight back

-Discernment Glass now emits a comparator output if it has a filter item

-Weeping and Twisting Blackstone Bricks were missing slabs, stairs and wall variants due to an oversight. these have now been implemented

-Warped Forests and Basalt Deltas now have slightly denser fog

-Soul Glass & Discernment Glass have received new sounds, the former also getting brand-new entering, exiting and submerged sounds

-Alongside this a new post-shader screen filter has also been introduced when you are inside soul glass giving it a rippling frosted glass look

-If the rippling is too disorientating then you can disable it within configs

-Both Soul Glass & Discernment Glass now drop themselves when broken with your bare fists

-Netherite Grate and Rusty Netherite Grate have received new sounds

-Ancient Wax Block has received new sounds

-Ancient Candle has received new sounds

-Wither Bone Blocks have received new sounds

-Bone Pike and Bone Fence have received new sounds

-Immunity Effects now have a distinct new sound effect for when they run out

-Geysers have received new sounds and shoot out particles at high velocity when stepped on

-Apparitions now have new possession sounds

-Obsidian, Crying Obsidian and Respawn Anchors now have sounds

-Nether Portals now have brand-new particles alongside activation and destruction sounds

-As a result of these new portal breaking sounds, they no longer rupture eardrums like in vanilla on larger portals when there is usually a lot of frames breaking

-Updated Russian Translation -X

-Updated Argentine Spanish Translation -X

-Updated German Translation

### Mod Compatibility

-Possessed and Ghost mobs now take 1.5x damage modifier if hurt with any modded silver weapon

-To further convey that possessions and ghost mobs are weak to silver, new particles show up when they are damaged with it

-Item Tags defining Silver Armors and Weapons are now located under the `c:` common tags namespace shared across NeoForge and Fabric for better compatibility

-All Mod Compat registries are no longer present unless that mod is active during runtime; 

-JNE's built-in mod compat datapacks have also been done away with in-favor of using NeoForge's conditionally loaded data jsons to fill the same role

-Quite a few modded Quartz, Glowstone, Obsidian and Blackstone blocks have received the new JNE sound types 

### Fixes

-Ecto Slabs can now be exorcised with water, this was bugged prior -X

-Treacherous Candle spawn and round sounds didn't have subtitles, this has now been fixed

-Claret was missing from the #minecraft:log and #minecraft:non_flammable_wood tags, this has now been fixed

-Sporeshrooms were unable to be duplicated with bone meal when hanging upside-down, this has now been fixed

-Suspicious Soul Sand had a chance to immediately decay upon being created, now there is an actual decay counter for tracking and preventing immediate decay

-Immunity Effect particles now show up on other entities inflicted with immunities and not just yourself

-Items cooking on an ancient campfire now display properly

-Banshees now instantly die if they are hit with their own will o wisp

-Fixed a long-standing animation bug which made cranking your pump-charge shotgun less... "satisfying" ...you can now crank it till your hearts content

