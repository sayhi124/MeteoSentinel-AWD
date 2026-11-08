# Vérification du package S4 — 08/11/2026

- Maven clean verify : 10 modules Spring Boot compilés ; 14 tests JUnit réussis.
- Dix processus démarrés en mode local ; health UP sur tous.
- Config Server fournit design.stage=VALIDATION_1.
- Eureka enregistre 7 services et le Gateway.
- Gateway atteint les 7 routes /design ; routes métier renvoient 501.
- Frontend React : npm build réussi, audit npm sans vulnérabilités signalées au moment de l’installation.
- GitHub Actions : compilation/tests Java et build React réussis.
- PowerPoint : 21 slides, validation de structure et réimport réussi ; pas de débordement géométrique détecté.
- PDFs vérifiés visuellement ; source LaTeX compilée avec succès.
- Dépôt public, PPTX, PDF et diagramme téléchargés sans authentification et comparés aux fichiers locaux.
- ZIPs contrôlés par testzip.

Non vérifié : démarrage Docker, connexions bases hétérogènes, pipeline RabbitMQ métier, intégration OIDC complète et recherche vectorielle. Le profil local est un squelette sans persistance métier.
