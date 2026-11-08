# DataHub / ClickHouse

EventLog(eventId UUID, eventType, occurredAt, source, aggregateId, schemaVersion, payload, receivedAt) ENGINE ReplacingMergeTree(receivedAt) ORDER BY eventId ; ForecastEvaluation(runId, validAt, horizonHours, variable, referenceKind, absError).

ClickHouse : stockage en colonnes et agrégats par ville/date/horizon sur événements append-only. Déduplication logique par eventId via ReplacingMergeTree et lecture FINAL/argMax pour les rapports ; pas de garantie d’unicité OLTP.
