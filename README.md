# StarCraft 2 Player Stats

This application allows you to receive and save user StarCraft 2 game statistics using the public Battle Net API

Main advantages:
1. Battle Net gives only the last 25 played matches. The application stores the entire history of matches from the moment the information from Battle Net began to be received
2. The application calculates daily statistics (how many matches per day were won or lost in each of the modes)
3. Visual display of results and MMR

| ![Screenshot](misc/screen3.png) | ![Screenshot](misc/screen4.png) |
|---------------------------------|---------------------------------|

## How to run

##### 1. Dependencies: Java 17 or or higher

##### 2. Set up next environment variables:
```
export BATTLENET_CLIENTID=
export BATTLENET_CLIENTSECRET=
export SC_PLAYERID=
```

You should get BATTLENET_CLIENTID, BATTLENET_CLIENTSECRET, SC_PLAYERID via this guide: https://develop.battle.net/documentation/guides/getting-started

##### 3. Run backend and frontend

```
chmod +x sc2stats-backend-2.0.0.jar
./sc2stats-backend-2.0.0.jar &
chmod +x ./sc2stats-frontend-2.0.0.jar 
./sc2stats-frontend-2.0.0.jar &
```
