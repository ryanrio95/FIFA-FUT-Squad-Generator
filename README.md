# FIFA 23 FUT Ultimate Team Automated Squad Generator
This project makes it possible to generate FIFA Ultimate Team squads with Kotlin.

You can write your own implementation of the SquadGenerator by extending the SquadGenerator or ExampleSquadGenerator.

The getPlayers method can be overwritten to for example get players from a local database.

The Player class can be extended to add more attributes to players that can be used to filter and create a heuristic for the 'best squad'.

There are helper methods in SquadGenerator that can be used to create your own Squad Generator implementation.