# 2.4.0 - NeoForge 1.21.1 Port

### Major Changes
-Updated to NeoForge 1.21.1.

-Now requires Elysium API 2.0.0+ to run this mod

-JNE codebase was rewritten from the ground-up for this port. expect many performance optimizations, bug fixes and a lot of improvements to existing features!

-JNE now requires Lodestone as dependency, used mainly for rendering

### Changes

-Elysium API's Mosaic BiomeSource has been introduced The Nether genereation to prevent the ugly micro biomes that could appear when too many nether mods are active,
This biome source utilizes voronoi grids and is fully automatic, meaning it'll work with literally any nether mod out of the box with highly customizable data-driven capabilites for modpack developers.
It is also fully compatible with Terrablender, Biolith and Blueprint provided biomes

-Every nether biome including some modded ones now have unique lightmap colors and ambient light brightness to better immerse you into the environment

-The Nether world generation has been entirely overhauled from the ground up for a more multilayered and cavernous aesthetic

-New Speleothem worldgen features can now generate in the nether

-The Nether is now 192 blocks tall, with an additional -32 blocks for the future underlava expansion

-Removed emissive built-in resource pack. all emissives are now part of the mod resources and are always active

-Entirely Overhauled the mod's configurations, JNE has never been this customizable before!

-An astonishing number of visual overhauls have been added to the mod

-A fog particle now generates all around the nether air not too dissimilar to the ones from Complimentary shaders to make the dimension's atmosphere feel more humid and alive

-Heat Distortion is also present on far away objects which gets more intense depending on if you're near lava; all heat distortion can be reduced or turned off within the configs

-Silt is a new gravity block that replaces the usual gravel shores and patches in the nether, along with this change nether beaches to add three distinct beach generation types;
which are Coves, Beaches and No Beaches respectively. Coves are the vanilla generation, Beaches are excusive to certain biomes and can encumpass a larger section of the lower regions,
and lastly some biomes will simply not have a beach at all.

-Silt Flint Ores have also been added to still ensure flint is still obtainable in the nether with the removal of gravel shores

-Siltmarrams are beach grass-like "plants" which change their appearance based on biome's humidity; it has Dry, Moist and Damp variants and their states can be locked by shearing them

-Soul Magma Block texture has received a glow-up, it now tiles much better with its surroundings; 
furthermore Soul Magma now emits new particles if you sprint on it to better convey what is damaging you

-Ancient Wax and Ancient Fire blocksets have been renamed to "Treacherous Wax" and "Treacherous Fire" respectively

-Due to the above change the formerly known as Treacherous Candles have been renamed to "Cierge of Treachery" and Treacherous Flames are now called "Spark of Treachery" to avoid confusion

-All soul fire blocks from the sanctum palette have been replaced with treacherous fire to give the structure a distinct visual identity,
The lower catacombs region of the sanctum has been left untouched and will still generate with soul fire blocks as usual

-Chapels have been fully redesigned from the ground up

-Treacherous Gargoyle Statues now has two faces on the front and back, as does the large statue in the sanctum center to reflect Treachery?'s new design

-Treacherous Wax Block texture has received a glow-up, no longer looks like bricks

-Shotgun-Fist now no longer uses wraithing flesh as ammo but rather the new Shotgun Shells instead

-Shotgun Shells are crafted with wraithing flesh, iron nuggets and any coal-like item. It is the new ammunition for shotguns

-Slug Shotgun Shells are a variant of shotgun-shells crafted with blaze powder. It can destroy blocks and easily create new openings

-Phasmo Shotgun Shells are another variant of shotgun-shells crafted with phasmo shards. It can phase through blocks or hit phantasmic mobs much like their arrow counterparts

-Blank Shotgun Shells do not shoot any projectiles but rather exist purely for mobility usage, they also do not increase cooldown when used with Counterforce

-Barrage Enchantment has been renamed to "Volley"

-Artemis Enchantment has been removed

-Cartridge Enchantment has been removed

-Counterforce is a new treasure enchantment for all shotgun weapons that when fired during a jump, reverses one's self-knockback letting you dash forwards.
The dash from a counterforce also provides 1 second of I-Frames indicated by a screen glow; any ammo that is not a blank will add 1.5 seconds of additional cooldown

-Shotguns all now have new particles for firing and when on cooldown; as well as actual pellet trails, impact sounds and visuals. The guns themselves now also have emissive parts

-Shotgun-Fists can now be repaired with Netherite Scraps in an anvil

-All pellets now have a unique 3D model opposed to just reusing the arrow cross with a flat texture

-Ecto Slabs have been entirely overhauled and have a unique stacking mechanic where they can pair up with other slabs to increase their stats and endurance

-Burrowed Ecto Slabs now produce obvious rays of light when they are underground to make them less annoying to keep track of, furthermore you can also force them out prematurely
by simply smacking them with a shovel, doing so will cause them to deal friendly-fire damage to all nearby mobs excluding other slabs

-Ecto Slabs are now a rare spawn within the soul sand valley as opposed to being random encounters when activating soul swirls, as such there is no longer any downsides to using swirls for mobility, Naturally spawning ecto slabs have a roughly 33% chance to spawn in petrified or as a small batch of regular dismantled slabs

-Petrified Ecto Slabs can pair up into much bigger towers but are completely immobile; although when in this state, they will petrify nearby swirls and make them unusable unless dealt with

-Petrified Soul Swirls rather than speeding you up will deal damage when walked through and when hit will lead you to the ecto slab tower that is petrifying it

-Explosion damage can force stacked up ecto slabs to dismantle and go into a cooldown for a few seconds

-Stampedes can now naturally spawn very rarely in the soul sand valley

-Stampedes' hunger meter UI has also received a redesign to make it more obvious as to what it is

-Thin Black Ice can now shatter in a chain reaction if one breaks, and certain mobs (usually light ones) can now stand on thin black ice without shattering it 

-Banshees had small visual tweaks to their textures and now produce smoke particles behind their orbit

-Left-handed banshees rod now orbits counter-clockwise instead of the usual clockwise rotation

-Will O' Wisps seeking logic has been overhauled, it now gradually gets faster overtime and less accurate with sharp turns

-Will O' Wisps now also have a 3D model with new and improved particles

-Will O' Wisps can be propelled to reach their maximum speed immediately by hitting them with a wind charge

-Apparitions, Wisps, Banshees and Blazes no longer have shading turned on their models and look far omre similar to how bedrock edition emissive mobs render

-Many nether biomes had their fog colors slightly tweaked to make them look brighter and appealing, this can be disabled within the configs to bring back their old colors

-The Nether Wastes fog is now orange instead of red to further differentiate from crimson forests, this feature too is configurable

-Unbounded Speed has been renamed to "Soul Speed" and has gained the same properties as the enchantment, due to this change the enchantment itself has been removed
in order to make soul speed far more accessible right from the get-go and reduce boots encahntment bloat

-Soul Speed (the effect) increases its amplification if the effect was reapplied whilst soul speed was already previously active

-Soul Swirls no longer drop themselves if sheared when activated to prevent abuse of Soul Speed

-Bone Rod has been renamed to "Bone Pike"

-Bone Corticals have been removed. all instances of this block from older worlds will automatically convert to regular vanilla bone blocks

-Tethered Bone Blocks are no longer a blockstate but rather a seperate block for better compatibility

-New Advancement "Why are you hitting yourself?" juke a will o' wisp into hitting the banshee which shot it

-New Advancement "Guns for Hands" kill a vessel with a bed explosion

-New Advancement "Ghosts Busted" kill one of each possessed and ghost mob

-New Advancement "Involuntary Eviction" forcefully remove a wisp out of ecto soul sand by brushing it

-New Advancement "99 Blue Balloons" leash an apparition and wait until it has calmed down

-All music tracks composed by Shroomaniac have been removed due to being problematic medias

-The Buckshot Wonderland Music Disc can no longer be played and is now a "Cracked Music Disc" until a replacement OST is made

-Cracked Music Disc is now much rarer in brazier chests

-Soul Sand Valleys and their sub-biomes now spawn cold frog variants opposed to warm ones

-Snow Golems no longer melt in the Soul Sand Valley or its sub-biomes either

-2 New Immunities have been added, Slow Falling Immunity and Infested Immunity

-Antidotes now show up in the JEI and REI search menus, alongside this change some antidotes had their recipes tweaked 

-Sorrowsquash stem hitboxes now extend to encompasses the entire thing when attached

-Beacon glass texture has been updated to incorporate better shading

-Breeze Rod texture has been redesigned to be consistent with the new blaze rod and banshee rod

-Fire particles have been entirely overhauled to have more appealing smoke and embers; they are fully resource-pack json driven and more can be added as mod compat very easily

-Vanilla Flame particles have been replaced with a palette permutated burn particle from Minecraft: Dungeons that is animated and easy to add similarly resource-pack json driven

-Apparitions can now also turn into a banshee if they happen to possess a Breeze

-Apparition Aggressions is a new data-driven registry which allows you to define custom hostility towards certain mobs depending on the apparition's personality 

-The Apparition Aggression system also allows you to specify custom possessions when an apparition kills a certain mob

-Apparition Gargoyle Statues is also a new data-driven registry which allows you to define custom gargoyle statue-like possessions for apparitions

-Apparition now has a new attack animation and particles for when it possesses a mob

-Apparitions can now be salted to prevent it from possessing mobs or gargoyle statues; Honeycombs are a temporary item until actual Nether Salt is added in the future

-Possessed mobs made from an apparition possessing an entity now retain the information of what EntityType they use to be for reverting in exorcisms
To make it easier for people to make possessions, apparitions can be "transported" with leashes. and will calm down after a while of being leashed if not already docile

-Discernment Glass now emits a comparator output if it has a filter item

-Weeping and Twisting Blackstone Bricks were missing slabs, stairs and wall variants due to an oversight. these have now been implemented

-Warped Forests, Basalt Deltas and Black Ice Glaciers now have slightly denser fog

-Soul Glass & Discernment Glass have received new sounds, the former also getting brand-new entering, exiting and submerged sounds,
alongside this a new post-shader screen filter has also been introduced when you are inside soul glass giving it a rippling frosted glass look.
If the rippling is too disorientating then you can disable it within configs

-Both Soul Glass & Discernment Glass now drop themselves when broken with your bare fists much like tinted glass

-Netherite Grate and Rusty Netherite Grate have received new sounds

-Treacherous Wax Block and Treacherous Candle (not to be confused with Cierge of Treachery) has received new sounds

-Wither Bone Blocks have received new sounds

-Bone Pike and Bone Fence have received new sounds

-Immunity Effects now have a distinct new sound effect for when they run out

-Geysers have received new sounds and shoot out particles at high velocity when stepped on

-Apparitions now have new possession sounds

-Obsidian, Crying Obsidian, Enchanting Table, Ender Chest and Respawn Anchors now have sounds

-Nether Portals now have a brighter texture, brand new particles, glowing rim texture alongside activation and destruction sounds,
as a result of these new portal breaking sounds, they no longer rupture eardrums like in vanilla on larger portals when there is usually a lot of frames breaking

-Brewing Stand now has an emissive texture and new particles

-Entities on fire now have a new effect that is more akin to Bedrock Edition and Minecraft: Dungeons, with support for burning colors based on the last fire you were in

-Black Ice Glaciers particles have been improved segnificantly to make the biome feel way colder

-Blotted Netherrack is a new block based on the old programmer art netherrack texture that generates in patches all throughout the nether to diversify ground variation

-Added Cerebrage Kale blocks which act like coral fans and generate naturally when growing cerebrage brain trees

-Chiseled/Etched Soul Slate Bricks and Tiles can now be lathered with treacherous wax to make it burn red

-Ectoplasm texture and particles have been updated and the fluid itself is a lot more brighter

-Pyroclast Crusts now generate on the lava surface and can be briefly stood on before shattering, they can be collected by right clicking with an empty hand

-A Pyroclast blockset can be crafted with crusts, it does not naturally generate and is purely there for decorative purposes... at least at the moment

-Magma Cream Block has been completely redone and now acts as a sided sticky block, the slick side behaves like glazed terracotta and will prevent sticking to other blocks,
you can use axe to scrape a slick side to sticky or use flint and steel to burn a sticky side to slick. the latter also has zero-friction behavior while the former is like a slime block

-Added Phasmo Slab block, it acts as a phasmo shard storage block

-Updated Soul Glass recipe to be more consistent with tinted glass

-Added Waxen Soul Glass, a red variant of the block that acts identically to the base one and is purely a visual difference

-Wisp has recieved new animations

-Magma Block has been given a brand new retexture and better emissives to make it look more consistent with soul magma blocks

-Treacherous Fire still cannot hurt you, nothing changed :)

-Vessels become completely stationary and staggered after firing their shotgun, leaving themselves vulnerable to attacks

-Vessels' bullet precision has been tweaked to be much more lenient, but being close to it makes the mob always land a perfect point-blank that is indecated with a yellow flash\

-Ectoplasm transforming blocks sorrounding it has been made data-driven and you can easily add more hauntings this way

-Updated Russian Translation

-Updated Argentine Spanish Translation

-Updated German Translation

### Mod Compatibility

-Possessed and Ghost mobs now take 1.5x damage modifier if hurt with any modded silver weapon

-To further convey that possessions and ghost mobs are weak to silver, new particles show up when they are damaged with it

-Item Tags defining Silver Armors and Weapons are now located under the `c:` common tags namespace shared across NeoForge and Fabric for better compatibility

-All Mod Compat registries are no longer present unless that mod is active during runtime; 

-JNE's built-in mod compat datapacks have also been done away with in-favor of using NeoForge's conditionally loaded data jsons to fill the same role

-Quite a few modded Quartz, Glowstone, Obsidian and Blackstone blocks have received the new JNE sound types 

-If Caverns & Chasms is installed, Blunt Shotgun Shells can now be crafted with spinel and when hit does not deal any damage but instead a ludicrous amount of knockback

### Fixes

-As mentioned earlier, a disgusting amount of bugs were fixed due to the total code rewrite, so much so that I cannot list all of them out

-Ecto Slabs can now be exorcized with water, this was bugged prior

-Cierge of Treachery spawn and round sounds didn't have subtitles, this has now been fixed

-Claret was missing from the #minecraft:log and #minecraft:non_flammable_wood tags, this has now been fixed

-Sporeshrooms were unable to be duplicated with bone meal when hanging upside-down, this has now been fixed

-Suspicious Soul Sand had a chance to immediately decay upon being created, now there is an actual decay counter for tracking and preventing immediate decay

-Immunity Effect particles now show up on other entities inflicted with immunities and not just yourself

-Items cooking on an treacherous campfire now display properly

-Banshees now instantly die if they are hit with their own will o wisp

-Fixed a long-standing animation bug which made cranking your pump-charge shotgun less... "satisfying" ...you can now crank it till your hearts content