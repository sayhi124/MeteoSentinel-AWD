# Alert / Redis

rule:{id} HASH ; subscription:{subject}:{id} HASH ; alert:{id} HASH ; dedup:{eventId}:{ruleVersion} STRING SET NX ; user-alerts:{subject} ZSET.

Redis : ensembles et hashes pour état actif/TTL et contrôle atomique de doublons. AOF activé, rétention explicite ; archive durable des changements par événement vers DataHub. Limite : sans archive acquittée, ne pas purger.
