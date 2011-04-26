InvZones Inventory Segregation
==============================

A [bukkit] (http://www.bukkit.org) plugin that adds the ability to define "zones" that have unique inventories.


Stability
---------

This code is in active use on the mc.obsoleet.org minecraft server, however, if it breaks your server, you get all the pieces.


Requirements
------------

This code has been tested on the following craftbukkit builds:
* 0 - do some testing

Other than craftbukkit, you will need some other plugin to allow users to teleport between worlds.

Realistically, the API use is very minimal, so it should keep working unless drastic changes are made.


Configuration
-------------


### plugin.yml

* `entering-message` -- A message to be printed to the user upon entering a zone. %z will be replaced with the destination zone.
* `leaving-message` -- A message to be printed to the user upon leaving a zone. %z will be replaced with the source zone.
* `worlds` -- A list of worlds and their zones in world: zone format. This will be automatically updated to include new worlds, with them set in the default zone.


### inv-<username>-<zone>.yml

These files are the storage location for saved inventories. I do not recommend modifying these files directly.
