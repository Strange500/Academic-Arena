Academic Arena
===========
Ce jeu est un projet réaliser dans le cadre de la SAE S1.02 du BUT Informatique de l'IUT de villeneuve d'Asq.


# Présentation de Academic Arena

Academix Arena est un jeu ludo-pédagogique destiné aux élèves d'école primaire.<br>
Ce jeu propose des combats tour par tour dans lesquels vous affrontez plusieurs vagues d'ennemis par niveaux.Pour les combattre, vous utilisez les opérateurs de base de mathématiques. <br>
Entre les niveaux, une question bonus portant sur l'histoire vous est posée, vous permettant d'améliorer votre personnage.
Des boss pourront apparaître pour challenger le joueur.
Ce jeu propose un tableau des scores, vous permettant de stimuler votre esprit de compétition.

Des captures d'écran illustrant le fonctionnement du logiciel sont proposées dans le répertoire shots.


# Utilisation de Academic Arena

Afin d'utiliser le projet, il suffit de taper les commandes suivantes dans un terminal :

```
./compile.sh
```
Permet la compilation des fichiers présents dans 'src' et création des fichiers '.class' dans 'classes'

```
./run.sh
```
Permet le lancement du jeu
<br>
Lors du lancement du jeu, si vous rencontrez des problèmes d'affichage, essayez de dézoomer la fenêtre (ctr + « - »).

<hr>

Vous pouvez configurer le jeux en modifiant le fichier AcademicArena.conf
<br>Voici la liste de quelques élements configurable: 
    <ul>
        <li>
            L'affichage ou non des réponse en cas de mauvaise réponse.
        </li>
        <li>
            La difficulté du jeu.
        </li>
        <li>
            Tout les ASCII founit de base.
        </li>
        <li>
            Vous pouvez ajouter de nouveaux Mob, boss via les fichier `mobs.csv` et `boss.csv`. <br>
            Vous pouvez donc aussi ajouter des visuel.
        </li>
        <li>
            Possibilité d'ajout de question bonus via le fichier `bonus.csv`.
        </li>
        <li>
            Vous pouvez changer les son et music du jeux .
        </li>
        <li>
            Vous pouvez modifier les visuel des personnage disponible.
        </li>
        <li>
            Vous pouvez modifier la police d'écriture ASCII en changeant les ASCII.
        </li>
    </ul>
<hr>

Développé par Florian Pollet & Roget Benjamin
Contacts : 
 - florian.pollet.etu@univ-lille.fr 
 - benjamin.roget.etu@univ-lille.fr

<img src="https://iut-b.univ-lille.fr/wp-content/uploads/2022/01/ulille.footer.png" height=30>

