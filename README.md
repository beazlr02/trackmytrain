# trackmytrain

[![Build Status](https://travis-ci.org/rossbeazley/trackmytrain.svg?branch=master)](https://travis-ci.org/rossbeazley/trackmytrain)


Another experiment, pushing the boundaries on ports and adapters. The network is the port, not the repo as i may have done in the past.


The big mistake I made was pulling the presenters into the black box side of the boundary, think they are gone now.


Todo list:

When tracking starts, watch slow to update until next refresh has happened.....

Favourites screen.

Widget?

point to point comms from wearable, stop broadcast - probably not that useful
- Wearable can send broadcast message to "find home"
- Mobile can send a "I am base" message for wearables so then can register node id
- Mobile can respond with to "find home" with "i am base"
- Wearable can send a attach service tracking ui message.
- Mobile will store the node id of the wearable that has attached ui
- Wearable can send a detach service tracking ui message.

Re-implement rum stuff, pull "rum controller" within boundary. extract rum client interface
Tracking rum
Tracking error

Buzz only if train "lateness" changes

colour train row if "very late"

optional to field

choose direction to/from

show no results

Fix API so two query operations use same "train times"

query multiple stations

view stopping points of a train


