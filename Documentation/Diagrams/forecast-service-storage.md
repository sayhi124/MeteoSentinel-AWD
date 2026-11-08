# Forecast / MongoDB

ForecastRun(_id, cityCode, issuedAt?, fetchedAt, source, hours:[{validAt,temperatureC,windKmh,rainMm,rainProbabilityPct}]).

MongoDB : document ForecastRun avec tableau hours ; index unique ville/source/version, TTL seulement sur caches, jamais sur références évaluées.
