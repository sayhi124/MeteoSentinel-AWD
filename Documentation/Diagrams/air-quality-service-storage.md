# Air Quality / MySQL

AirQualitySnapshot(id UUID PK, cityCode, validAt, fetchedAt, source); PollutantReading(snapshotId FK, europeanAqi, pm25, pm10, ozone).

MySQL : séries structurées, contraintes et index ville/validAt. AQI européen conservé comme indice fournisseur ; ne pas le présenter comme classe officielle du projet.
