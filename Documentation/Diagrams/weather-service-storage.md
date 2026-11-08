# Weather / PostgreSQL

WeatherSnapshot(id UUID PK, cityCode, fetchedAt, source); WeatherReading(snapshotId FK, temperatureC, windKmh, humidityPct).

PostgreSQL : contraintes, index ville/date et transactions pour snapshots et outbox. Aucune lecture interbase.
