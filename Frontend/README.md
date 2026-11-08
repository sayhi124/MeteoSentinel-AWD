# Portail React de conception

Ce client présente le projet, les frontières métier, les bases prévues et la préparation à la validation. Il interroge les sept routes `/api/*/design` via le vrai Gateway ; il n’affiche aucune donnée météo inventée. Un statut Accessible ne signifie pas que le métier ni la base sont implémentés.

`npm ci` puis `npm run build` écrit les fichiers statiques dans les ressources de `Backend/api-gateway`. Recompiler ensuite le Gateway avec Maven. `npm run dev` utilise un proxy local vers le Gateway. Le profil local est sans authentification et lié à 127.0.0.1.

La connexion OIDC/PKCE et les écrans métier seront ajoutés après validation. Le profil secure du backend refuse les APIs sans JWT valide ; le portail ne simule pas cette authentification.
