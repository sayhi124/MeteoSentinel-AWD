# User / MariaDB

UserProfile(subject VARCHAR PK, displayName, consentVersion, consentAt); Preference(subject FK, activityType, maxWind, maxAqi); FavoriteCity(subject FK, cityCode).

MariaDB : relations profil/préférences/favoris, transactions et contraintes d’unicité. Même famille SQL que MySQL mais instance et credentials distincts.
