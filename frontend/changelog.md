# 2.4.0 - NeoForge 1.21.1 Port

### Major Changes
-Updated to NeoForge 1.21.1

-Now requires Elysium API 1.2.0+ to run this mod

-JNE codebase was rewritten from the ground-up for this port. expect many performance improvements and bug fixes!

### Changes
-Removed emissive built-in resource pack. all emissives are now part of the mod resources and always active

-Geysers have received new sounds and shoot out particles at high velocity when stepped on

-Soul Magma Block texture has received a glow-up, it now tiles much better with its surroundings

-Soul Magma also emits new particles if you sprint on it to convey that is what's damaging you

-Ancient Wax Block texture has received a glow-up, no longer looks like bricks

-Shotgun-Fist now no longer uses wraithing flesh as ammo but rather the new Shotgun-Shells instead -X

-Shotgun-Shells are crafted with wraithing flesh and any coal-like item. It is the new ammunition for shotguns -X

-Slug-Shells are a variant of shotgun-shells crafted with gunpowder. It can destroy weak blocks and easily create openings -X

-Phasmo-Shells are another variant of shotgun-shells crafted with phasmo shards. It can phase through blocks -X

-Ecto Slabs now produce obvious rays of light when they are underground

-Ecto Slabs now rarely spawn naturally in the soul sand valley, to accommodate their detection radius has been greatly reduced -X

-Ecto Slabs can however still detect entities inflicted with unbounded speed from as far as 64 blocks away and B-line towards them if possible -X

-Stampedes can now naturally spawn very rarely in the soul sand valley -X

-Stampedes' hunger meter UI has also received a redesign to make it more obvious as to what it is

-Thin Black Ice can now shatter in a chain reaction if one breaks

-Certain mobs (usually lit ones) have also been made to support standing on thin black ice without shattering it 

-Banshees now produce new breathing particles from their mouths -X

-Apparitions, Wisps, Banshees and Blazes no longer have shade and look similar to bedrock edition emissive mobs -X

-New Particles have been given to the soul sand valley to make it appear more windy -X

-Striders now have new Damp, Moist and Dry variants depending on which biome they're found in -X

-Soul Swirls no longer drop themselves if sheared during a cooldown

-Bone Rod has been renamed to "Bone Spike"

-Wretched Gargoyle Statue has received a new model -X

-New Advancement "Guns for Hands" obtained when killing a vessel with a shotgun-fist -X

-New Advancement "Ghosts Busted" kill one of each new hostile soul sand valley mob -X

-Soul Glass & Discernment Glass has received new sounds -X

-Buckshot Wonderland Music Disc is now much rarer in brazier chests and has an alternative obtaining method when a vessel kills a creeper -X

-Soul Sand Valleys and their sub-biomes now spawn cold frog variants opposed to warm ones

-Snow Golems no longer melt in the Soul Sand Valley or its sub-biomes either

-Immunity Effects have a distinct new sound effect for when they run out

-2 New Immunities were added, Slow Falling Immunity and Infesting Immunity brewed with Wind Charge and Magma Block respectively

-Sorrowsquash stem hitboxes now extend to encompasses the entire thing

-Beacon glass texture has been updated with better shading

-Breeze Rod texture has been redesigned to be consistent with the new blaze rod and banshee rod

-Apparitions can now also turn into a banshee if they happen to possess a breeze -X

-Apparition possession conversions are now data-driven and allows you to define what they turn into upon killing a certain mob -X

-Similarly what mobs the Apparition attacks is too data-driven now -X

-Discernment Glass now emits a comparator output if it has a filter item

-Weeping and Twisting Blackstone Bricks were missing slabs, stairs and wall variants due to an oversight. this has now been fixed

### Fixes

-Ecto Slabs can now be exorcised with water, this was bugged prior -X

-Treacherous Candle spawn and round sounds didn't have subtitles, this has now been fixed

-Claret was missing from the #minecraft:log and #minecraft:non_flammable_wood tags, this has now been fixed

-Sporeshrooms can now be duplicated with bone meal even when hanging upside-down

-Suspicious Soul Sand had a chance to immediately decay upon being created, now there is an actual decay counter to prevent immediate decay

-Immunity Effect particles now show up on other entities and not just yourself

