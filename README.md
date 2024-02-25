# YATPA
Website: https://www.spigotmc.org/resources/yatpa.115050/

# Official Discord 

https://discord.gg/aT9z7q7hX8

## Building instructions

mvn clean install
 
## Description

This allows people to tpa to each other. This is meant to be a simple plugin for servers. It is highly optimized and heavily tested. Feel free to constribute.

### Player commands 

> /tpa 
>
> /tpahere.
>
> /tpaccept
>
> /tpyes
>
> /tpdeny
>
> /tpno

## Default Config

```
 teleport:
   effect:
     value: 
     - DAMAGE_RESISTANCE, 0, 4
     period: 5
   cooldown: 0 
   delay: 0  
 request: 
   error-if-too-many: true  
   timeout: 60
```
## Default Messeges

```
teleport:
  teleporting:
    from-player:
      instant: '&aYou are being teleported to &f{player}'
      delayed: '&aYou will be teleported to &f{player} &ain &f{delay} &aseconds'
    to-player:
      delayed: '&a{player} will be teleported to you in &f{delay} &aseconds'
      instant: '&a{player} is being teleported to you'
error:
  teleport:
    in-teleport: '&cThe player is in teleport'
    offline: '&cThe player is offline'
  no-request: '&cYou don''t have any request'
  yourself: '&cYou cannot send a request to yourself'
  player-not-found: '&cPlayer not found'
  player-only: '&cYou must be a player to use this command'
  too-many-requests: '&cYou have too many requests. Please specify whos request you
    want to accept by doing tpyes (username).'
  cooldown: '&cYou need to wait &f{cooldown} &cmore seconds to send a request'
  already-sent: '&cYou have already sent a request to this player'
request:
  deny:
    from: '&c{player} &chas denied your request'
    to: '&cYou have denied the request from &f{player}'
  sent: '&aYou have sent a request to &f{player}'
  received:
    here: '&f{player} &ahas requested you to teleport to them'
    note: '&aType &f/tpaccept &ato accept or &f/tpdeny &ato deny'
    normal: '&f{player} &ahas requested to teleport to you'
prefix: '&e[&6TPA Feature&e] '
```

### Folia inquisitors

[<img src="https://github.com/Folia-Inquisitors.png" width=80 alt="Folia-Inquisitors">](https://github.com/orgs/Folia-Inquisitors/repositories)
[<img src="https://github.com/HSGamer.png" width=80 alt="HSGamer">](https://github.com/HSGamer)
