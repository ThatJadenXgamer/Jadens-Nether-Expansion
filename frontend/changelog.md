# 2.4.0 - NeoForge 1.21.1 Port

### Major Changes
-Updated to NeoForge 1.21.1

-Now requires Elysium API 1.2.0+ to run this mod

-JNE codebase was rewritten from the ground-up for this port. expect many performance improvements and bug fixes!

### Changes

-The Nether world generation has been entirely overhauled from the ground up

-The Nether is now 192 blocks tall, with an additional -32 blocks for the future underlava expansion

-The dimension is also much more multilayered and less floaty overall

-Removed emissive built-in resource pack. all emissives are now part of the mod resources and always active

-Entirely Overhauled every single configuration in the mod and added several new ones

-Geysers have received new sounds and shoot out particles at high velocity when stepped on

-Soul Magma Block texture has received a glow-up, it now tiles much better with its surroundings

-Soul Magma also emits new particles if you sprint on it to better convey that is what's damaging you

-Ancient Wax Block texture has received a glow-up, no longer looks like bricks

-Shotgun-Fist now no longer uses wraithing flesh as ammo but rather the new Shotgun-Shells instead -X

-Shotgun-Shells are crafted with wraithing flesh and any coal-like item. It is the new ammunition for shotguns -X

-Slug-Shells are a variant of shotgun-shells crafted with gunpowder. It can destroy weak blocks and easily create new openings -X

-Phasmo-Shells are another variant of shotgun-shells crafted with phasmo shards. It can phase through blocks -X

-Ecto Slabs now produce obvious rays of light when they are underground to make them less annoying

-Ecto Slabs now rarely spawn naturally in the soul sand valley instead of soul swirls. To accommodate this, their detection radius has been greatly reduced -X

-Ecto Slabs can however still detect entities inflicted with unbounded speed from as far as 64 blocks away and B-line towards them if possible -X

-Stampedes can now naturally spawn very rarely in the soul sand valley -X

-Stampedes' hunger meter UI has also received a redesign to make it more obvious as to what it is

-Thin Black Ice can now shatter in a chain reaction if one breaks

-Certain mobs (usually light ones) have also been made to support standing on thin black ice without shattering it 

-Banshees now produce new breathing particles from their mouths -X

-Apparitions, Wisps, Banshees and Blazes no longer have shade and look similar to bedrock edition emissive mobs

-New Particles and Fog Color have been given to the soul sand valley to make it appear more windy and ethereal -X

-Striders now have new Damp, Moist and Dry variants depending on which biome they're found in -X

-Soul Swirls no longer drop themselves if sheared during a cooldown

-Bone Rod has been renamed to "Bone Pike"

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

-Apparitions can now also turn into a banshee if they happen to possess a breeze

-Apparition Aggressions is a new data-driven registry which allows you to define custom hostility towards certain mobs depending on the apparition's personality 

-Apparition now has a new attack animation

-Apparitions can now be salted with wax to prevent it from possessing mobs or gargoyle statues

-The above-mentioned registry also optionally lets you define custom possessions for certain mobs the apparition kills 

-Discernment Glass now emits a comparator output if it has a filter item

-Weeping and Twisting Blackstone Bricks were missing slabs, stairs and wall variants due to an oversight. these have now been implemented

-Warped Forests now have slightly denser fog

### Mod Compatibility

-Possessed and Ghost mobs take 1.5x modifier of damage if hurt with any modded silver weapon

-To further reflect the possessed and ghost's their weakness to silver, new particles show up when they are damaged with it

-Item Tags defining Silver Armors and Weapons are now located under the `c:` common tags namespace shared across NeoForge and Fabric

-To make it easier for people to make possessions, apparitions can be "transported" with leashes. although unless it's docile it'll still fight back

-Galosphere's Sterling armor pieces are now counted as silver armor and thus are effective against ghosts and possessions

### Fixes

-Ecto Slabs can now be exorcised with water, this was bugged prior -X

-Treacherous Candle spawn and round sounds didn't have subtitles, this has now been fixed

-Claret was missing from the #minecraft:log and #minecraft:non_flammable_wood tags, this has now been fixed

-Sporeshrooms can now be duplicated with bone meal even when hanging upside-down

-Suspicious Soul Sand had a chance to immediately decay upon being created, now there is an actual decay counter to prevent immediate decay

-Immunity Effect particles now show up on other entities and not just yourself

