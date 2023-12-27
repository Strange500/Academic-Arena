class AcademicArena extends Program {



    final char EMPTY = ' ';
    final Pixel EMPTY_PIXEL = newPixelEmpty();
    final String RESSOURCES_DIR = "ressources";
    final String GAMEOVER = RESSOURCES_DIR + "/" + "gameOver.txt";
    final String OPERATOR_DIR = RESSOURCES_DIR + "/" + "operators";
    final String LOGO_PART_A = RESSOURCES_DIR + "/" + "academic.txt";
    final String LOGO_PART_B = RESSOURCES_DIR + "/" + "arena.txt";
    final String PLAYER = RESSOURCES_DIR + "/" + "player.txt";
    final String PLAYER_2 = RESSOURCES_DIR + "/" + "player2.txt";
    final String PLAYER_3 = RESSOURCES_DIR + "/" + "player3.txt";
    final String PLAYER_4 = RESSOURCES_DIR + "/" + "player4.txt";
    final String SWORD = RESSOURCES_DIR + "/" + "swords.txt";
    final String HEART = RESSOURCES_DIR + "/" + "heart.txt";
    final String NUMBERS_DIR = RESSOURCES_DIR + "/" + "numbers";
    final String MOB_DIR = RESSOURCES_DIR + "/" + "mobs";
    final String BOSS_DIR = RESSOURCES_DIR + "/" + "boss";
    final String PLAYERS_FILE = RESSOURCES_DIR + "/" + "players.csv";
    final String SCORE_FILE = RESSOURCES_DIR + "/" + "scores.csv";
    final String MOBS_FILE = RESSOURCES_DIR + "/" + "mob.csv";
    final String BONUS_FILE = RESSOURCES_DIR + "/" + "bonus.csv";
    final String BOSS_FILE = RESSOURCES_DIR + "/" + "boss.csv";

    final char[] LIST_EMPTY = new char[]{' ', ' ', ' ', ' ', ' ',' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '　', '⠀'};
    final Screen[] LIST_OPERATOR = new Screen[]{loadASCII(OPERATOR_DIR + "/" + "plus.txt", ANSI_RED), loadASCII(OPERATOR_DIR + "/" + "moins.txt", ANSI_GREEN), loadASCII(OPERATOR_DIR + "/" + "fois.txt", ANSI_YELLOW), loadASCII(OPERATOR_DIR + "/" + "division.txt", ANSI_BLUE)};
    Question[] listQuestion;
    Mob[] listMob ;
    Mob[] listBoss ;
    Score[] listScore;
    Screen main = newScreen(51,250);
    Player player;
    

    // function to load ressources

    
    /**
     * Loads the mob data from a CSV file and initializes the list of mobs.
     */
    void loadMob() {
        extensions.CSVFile f = loadCSV(MOBS_FILE);
        listMob = new Mob[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listMob[i-1] = newMob(StringToInt(getCell(f, i, 0)), StringToInt(getCell(f, i, 1)), getOperation(getCell(f, i, 2)), loadASCII(MOB_DIR + "/" + getCell(f, i, 3), getWeaknessColor(getCell(f, i, 2))), 0, 0);
        }

    }

    /**
     * Loads the scores from a CSV file and initializes the list of scores.
     */
    void loadScores() {
        extensions.CSVFile f = loadCSV(SCORE_FILE);
        listScore = new Score[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listScore[i-1] = newScore(getCell(f, i, 0), StringToInt(getCell(f, i, 1)));
        }
    }

    /**
     * Loads the boss data from a CSV file and initializes the list of bosses.
     */
    void loadBoss() {
        extensions.CSVFile f = loadCSV(BOSS_FILE);
        listBoss = new Mob[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listBoss[i-1] = newMob(StringToInt(getCell(f, i, 0)), StringToInt(getCell(f, i, 1)), getOperation(getCell(f, i, 2)), loadASCII(BOSS_DIR + "/" + getCell(f, i, 3), getWeaknessColor(getCell(f, i, 2))), 0, 0);
        }

    }

    /**
     * Loads the questions from a CSV file and initializes the list of questions.
     */
    void loadQuestion() {
        extensions.CSVFile f = loadCSV(BONUS_FILE);
        listQuestion = new Question[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listQuestion[i-1] = newQuestion(getCell(f, i, 0), getCell(f, i, 1));
        }

    }


    // function to init types

    /**
     * Creates a new question object.
     *
     * @param question the question text
     * @param reponse the answer to the question
     * @return the newly created question object
     */
    Question newQuestion(String question, String reponse) {
        Question q = new Question();
        q.question = question;
        q.reponse = reponse;
        return q;
    }


    /**
     * Creates a new score with the specified pseudo and score.
     *
     * @param pseudo The pseudo of the player.
     * @param score The score achieved by the player.
     * @return The newly created score.
     */
    Score newScore(String pseudo, int score) {
        Score s = new Score();
        s.pseudo = pseudo;
        s.score = score;
        return s;
    }
    

    /**
     * Creates a new pixel with the specified character and color.
     *
     * @param c     The character of the pixel.
     * @param color The color of the pixel.
     * @return The newly created pixel.
     */
    Pixel newPixel(char c, String color) {
        Pixel p = new Pixel();
        p.c = color + c;
        return p;
    }



    /**
     * Creates a new empty pixel.
     *
     * @return The new empty pixel.
     */
    Pixel newPixelEmpty() {
        Pixel p = new Pixel();
        p.c = "" + EMPTY;
        return p;
    }

    /**
     * Creates a new screen with the specified height and width.
     *
     * @param height the height of the screen
     * @param width the width of the screen
     * @return the newly created screen
     */
    Screen newScreen(int height, int width) {
        Screen sr = new Screen();
        sr.height = height;
        sr.width = width;
        sr.screen = new Pixel[height][width];
        for (int i = 0; i < length(sr.screen, 1); i++) {
            for (int j = 0; j < length(sr.screen, 2); j++) {
                sr.screen[i][j] = newPixelEmpty();
            }
        }
        return sr;
    }
    
    /**
     * Creates a new instance of the Mob class.
     *
     * @param hp       the health points of the mob
     * @param atk      the attack points of the mob
     * @param faiblesse the weakness of the mob
     * @param visuel   the visual representation of the mob
     * @param posx     the x-coordinate of the mob's position
     * @param posy     the y-coordinate of the mob's position
     * @return a new instance of the Mob class
     */
    Mob newMob(int hp, int atk, Operation faiblesse, Screen visuel, int posx, int posy) {
        Mob m = new Mob();
        m.hp = hp;
        m.initialHp = hp;
        m.dead = false;
        m.atk = atk;
        m.faiblesse = faiblesse;
        m.visuel = visuel;
        m.posx = posx;
        m.posy = posy;
        return m;
    }

    /**
     * Creates a new player with the specified pseudo and character.
     *
     * @param pseudo    the pseudo of the player
     * @param character the character screen of the player
     * @return the newly created player
     */
    Player newPlayer(String pseudo, Screen character) {
        Player p = new Player();
        p.hp = 100;
        p.atk = 5;
        p.pseudo = pseudo;
        p.character = character;
        return p;
    }


    void testNewPixel() {
        Pixel p = newPixel('c', ANSI_RED);
        assertEquals(ANSI_RED + 'c', p.c);
        p = newPixel('c', ANSI_GREEN);
        assertEquals(ANSI_GREEN + 'c', p.c);
        p = newPixel('c', ANSI_YELLOW);
        assertEquals(ANSI_YELLOW + 'c', p.c);
        p = newPixel('c', ANSI_BLUE);
        assertEquals(ANSI_BLUE + 'c', p.c);
        p = newPixel('c', ANSI_PURPLE);
        assertEquals(ANSI_PURPLE + 'c', p.c);
        p = newPixel('c', ANSI_CYAN);
        assertEquals(ANSI_CYAN + 'c', p.c);
        p = newPixel('c', ANSI_WHITE);
        assertEquals(ANSI_WHITE + 'c', p.c);
        p = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        assertEquals(ANSI_TEXT_DEFAULT_COLOR + 'c', p.c);
    }

    void testNewScreen() {
        Screen sr = newScreen(2, 2);
        assertEquals(2, sr.height);
        assertEquals(2, sr.width);
        assertEquals(toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" +  ANSI_TEXT_DEFAULT_COLOR + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_TEXT_DEFAULT_COLOR + ANSI_TEXT_DEFAULT_COLOR  , toString(sr));
        sr = newScreen(3, 3);
        assertEquals(3, sr.height);
        assertEquals(3, sr.width);
        assertEquals(toString(newPixelEmpty()) + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_TEXT_DEFAULT_COLOR + toString(newPixelEmpty()) + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_TEXT_DEFAULT_COLOR + toString(newPixelEmpty()) + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_TEXT_DEFAULT_COLOR + ANSI_TEXT_DEFAULT_COLOR  , toString(sr));
    }

    void testNewMob() {
        int hp = 10;
        int atk = 12;
        Operation faiblesse = Operation.ADDITION;
        Screen visuel = newScreen(10, 10);
        int posx = 0;
        int posy = 10;
        Mob m = newMob(hp, atk, faiblesse, visuel, posx, posy)  ;
        assertEquals(hp, m.hp);
        assertEquals(atk, m.atk);
        assertEquals(visuel, m.visuel);
        assertEquals(posx, m.posx);
        assertEquals(faiblesse, m.faiblesse);
        assertEquals(posy, m.posy);
    }

    void testNewPlayer() {
        String pseudo = "toto";
        Screen character = newScreen(10, 10);
        Player p = newPlayer(pseudo, character);
        assertEquals(100, p.hp);
        assertEquals(5, p.atk);
        assertEquals(pseudo, p.pseudo);
        assertEquals(character, p.character);
    }

    void testNewScore() {
        String pseudo = "toto";
        int score = 10;
        Score s = newScore(pseudo, score);
        assertEquals(pseudo, s.pseudo);
        assertEquals(score, s.score);
    }

    void testNewQuestion() {
        String question = "2 + 2";
        String reponse = "4";
        Question q = newQuestion(question, reponse);
        assertEquals(question, q.question);
        assertEquals(reponse, q.reponse);
    }



    //function to print types

    /**
     * Converts a Pixel object to a string representation.
     *
     * @param pixel the Pixel object to convert
     * @return the string representation of the Pixel object
     */
    String toString(Pixel pixel) {
        return pixel.c;
    }

    /**
     * Returns a string representation of the given Mob object.
     *
     * @param m the Mob object to convert to a string
     * @return the string representation of the Mob object
     */
    String toString(Mob m) {
        return m.hp + " " + m.atk + " " + m.faiblesse + " " + m.posx + " " + m.posy;
    }

    /**
     * Converts the given Screen object to a string representation.
     *
     * @param screen the Screen object to convert
     * @return the string representation of the Screen object
     */
    String toString(Screen screen) {
        String result = "";
        for (int i = 0; i < length(screen.screen, 1); i++) {

            for (int j = 0; j < length(screen.screen, 2); j++) {
                result = result + toString(screen.screen[i][j]);
            }
            result = result + '\n' + ANSI_RESET;
        }
        return result + ANSI_RESET;
    }

    void testToStringPixel() {
        Pixel p = newPixel('c', ANSI_RED);
        assertEquals(ANSI_RED + 'c', toString(p));
        p = newPixel('c', ANSI_GREEN);
        assertEquals(ANSI_GREEN + 'c', toString(p));
        p = newPixel('c', ANSI_YELLOW);
        assertEquals(ANSI_YELLOW + 'c', toString(p));
        p = newPixel('c', ANSI_BLUE);
        assertEquals(ANSI_BLUE + 'c', toString(p));
        p = newPixel('c', ANSI_PURPLE);
        assertEquals(ANSI_PURPLE + 'c', toString(p));
        p = newPixel('c', ANSI_CYAN);
        assertEquals(ANSI_CYAN + 'c', toString(p));
        p = newPixel('c', ANSI_WHITE);
        assertEquals(ANSI_WHITE + 'c', toString(p));
        p = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        assertEquals(ANSI_TEXT_DEFAULT_COLOR + 'c', toString(p));
    }

    void testToStringScreen() {
        Screen sr = newScreen(2, 2);
        sr.screen[0][0] = newPixel('H', "");
        sr.screen[1][1] = newPixel('Y', "");
        assertEquals(toString(sr.screen[0][0]) + toString(sr.screen[0][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + toString(sr.screen[1][0]) + toString(sr.screen[1][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + ANSI_TEXT_DEFAULT_COLOR  , toString(sr));
        sr.screen[0][0] = newPixel('H', ANSI_RED);
        sr.screen[1][1] = newPixel('Y', ANSI_GREEN);
        assertEquals(toString(sr.screen[0][0]) + toString(sr.screen[0][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + toString(sr.screen[1][0]) + toString(sr.screen[1][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + ANSI_TEXT_DEFAULT_COLOR  , toString(sr));
    }

    void testToStringMob() {
        Mob m = newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10);
        assertEquals("10 12 ADDITION 0 10", toString(m));
        m = newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10);
        assertEquals("10 12 MULTIPLICATION 0 10", toString(m));
        m = newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10);
        assertEquals("10 12 DIVISION 0 10", toString(m));
        m = newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10);
        assertEquals("10 12 SOUSTRACTION 0 10", toString(m));
    }

    // I/O functions

    /**
     * Reads the contents of a file and returns it as a string.
     *
     * @param filePath the path of the file to be read
     * @return the contents of the file as a string
     */
    String fileAsString(String filePath) {
        extensions.File f = newFile(filePath);
        String result = "";
        if (ready(f)) {
            String line = "f";
            while (line != null) {
                line = readLine(f);
                result = result + '\n' + line;
            }
        }
        return substring(result, 1, length(result));
    }

    /**
     * Saves the scores to a CSV file(SCORE_FILE).
     * 
     * @param listScore the array of Score objects containing the scores to be saved
     * @return void
     */
    void saveScores() {
        String[][] csvTable = new String[length(listScore)+1][2];
        csvTable[0][0] = "pseudo";
        csvTable[0][1] = "score";
        for (int i = 1; i < length(listScore)+1; i++) {
            csvTable[i][0] = listScore[i-1].pseudo;
            csvTable[i][1] = "" + listScore[i-1].score;
        }
        saveCSV(csvTable, SCORE_FILE);
    }


    // copy functions

    

    /**
     * Creates a copy of the given Pixel object.
     *
     * @param p1 the Pixel object to be copied
     * @return a new Pixel object with the same character and color as the original
     */
    Pixel copy(Pixel p1) {
        Pixel p = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        p.c = p1.c;
        return p;
    }

  
    /**
     * Creates a copy of the given Mob object.
     *
     * @param m the Mob object to be copied
     * @return a new Mob object that is a copy of the input Mob
     */
    Mob copy(Mob m) {
        Mob result = newMob(m.hp, m.atk, m.faiblesse, m.visuel, m.posx, m.posy);
        return result;
    }


    /**
     * Copies the contents of a given screen.
     *
     * @param ecran The screen to be copied.
     * @return A new screen with the same contents as the input screen.
     */
    Screen copy(Screen ecran) {
        Screen result = newScreen(ecran.height, ecran.width);
        for (int i = 0; i < result.height; i++) {
            for (int j = 0; j < result.width; j++) {
                result.screen[i][j] = copy(ecran.screen[i][j]);
            }
        }
        return result;
    }

    void testCopyPixel() {
        Pixel p = newPixel('c', ANSI_RED);
        Pixel p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_GREEN);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_YELLOW);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_BLUE);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_PURPLE);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_CYAN);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_WHITE);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
        p = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        p1 = copy(p);
        assertEquals(toString(p), toString(p1));
    }

    void testCopyMob() {
        Mob m = newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10);
        Mob m1 = copy(m);
        assertEquals(toString(m), toString(m1));
        m = newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10);
        m1 = copy(m);
        assertEquals(toString(m), toString(m1));
        m = newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10);
        m1 = copy(m);
        assertEquals(toString(m), toString(m1));
        m = newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10);
        m1 = copy(m);
        assertEquals(toString(m), toString(m1));
    }

    void testCopyScreen() {
        Screen sr = newScreen(2, 2);
        sr.screen[0][0] = newPixel('H', "");
        sr.screen[1][1] = newPixel('Y', "");
        Screen sr1 = copy(sr);
        assertTrue(equals(sr, sr1));
        sr.screen[0][0] = newPixel('H', ANSI_RED);
        sr.screen[1][1] = newPixel('Y', ANSI_GREEN);
        sr1 = copy(sr);
        assertTrue(equals(sr, sr1));
    }


    // random choices

    /**
     * Returns a randomly chosen element from the given list.
     *
     * @param list the array of strings to choose from
     * @return a randomly chosen string from the list
     */
    String randomChoice(String[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }

    /**
     * Returns a randomly chosen element from the given list.
     *
     * @param list the array of mobs to choose from
     * @return a randomly chosen mob from the list
     */
    Mob randomChoice(Mob[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }

    /**
     * Returns a randomly chosen element from the given list.
     *
     * @param list the array of questions to choose from
     * @return a randomly chosen question from the list
     */
    Question randomChoice(Question[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }
    
    void testRandomChoiceString() {
        String[] list = new String[]{"a", "b", "c", "d"};
        String result = randomChoice(list);
        boolean test = false;
        for (int i = 0; i < length(list); i++) {
            if (equals(list[i], result)) {
                test = true;
            }
        }
        assertTrue(test);
    }

    void testRandomChoiceMob() {
        Mob[] list = new Mob[]{newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10), newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10), newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10), newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10)};
        Mob result = randomChoice(list);
        boolean test = false;
        for (int i = 0; i < length(list); i++) {
            if (equals(list[i], result)) {
                test = true;
            }
        }
        assertTrue(test);
    }

    void testRandomChoiceQuestion() {
        Question[] list = new Question[]{newQuestion("2 + 2", "4"), newQuestion("2 * 2", "4"), newQuestion("2 / 2", "1"), newQuestion("2 - 2", "0")};
        Question result = randomChoice(list);
        boolean test = false;
        for (int i = 0; i < length(list); i++) {
            if (equals(list[i], result)) {
                test = true;
            }
        }
        assertTrue(test);
    }

    // equals functions

    /**
     * Compares two Pixel objects and checks if the last character of their 'c' attribute is equal.
     *
     * @param p1 The first Pixel object to compare.
     * @param p2 The second Pixel object to compare.
     * @return true if the last character of 'c' attribute of both Pixel objects is equal, false otherwise.
     */
    boolean equals(Pixel p1, Pixel p2) {
        return charAt(p1.c, length(p1.c) - 1) ==  charAt(p2.c, length(p2.c) - 1);
    }

    /**
     * Compares two Mob objects and checks if all their attributes are equal.
     *
     * @param m1 The first Mob object to compare.
     * @param m2 The second Mob object to compare.
     * @return true if all the attributes of both Mob objects are equal, false otherwise.
     */
    boolean equals(Mob m1, Mob m2) {
        return m1.hp == m2.hp && m1.atk == m2.atk && m1.faiblesse == m2.faiblesse && m1.posx == m2.posx && m1.posy == m2.posy && equals(m1.visuel, m2.visuel);
    }

    /**
     * Compares two Screen objects and checks if all their attributes are equal.
     *
     * @param s1 The first Screen object to compare.
     * @param s2 The second Screen object to compare.
     * @return true if all the attributes of both Screen objects are equal, false otherwise.
     */
    boolean equals(Screen s1, Screen s2) {
        boolean result = true;
        for (int i = 0; i < length(s1.screen, 1); i++) {
            for (int j = 0; j < length(s1.screen, 2); j++) {
                result = result && equals(s1.screen[i][j], s2.screen[i][j]);
            }
        }
        return result;
    }

    /**
     * Compares two Question objects and checks if all their attributes are equal.
     *
     * @param q1 The first Question object to compare.
     * @param q2 The second Question object to compare.
     * @return true if all the attributes of both Question objects are equal, false otherwise.
     */
    boolean equals(Question q1, Question q2) {
        return equals(q1.question, q2.question) && equals(q1.reponse, q2.reponse);
    }

    /**
     * Compares two Score objects and checks if all their attributes are equal.
     *
     * @param s1 The first Score object to compare.
     * @param s2 The second Score object to compare.
     * @return true if all the attributes of both Score objects are equal, false otherwise.
     */
    boolean equals(Score s1, Score s2) {
        return equals(s1.pseudo, s2.pseudo) && s1.score == s2.score;
    }

    /**
     * Compares two Player objects and checks if all their attributes are equal.
     *
     * @param p1 The first Player object to compare.
     * @param p2 The second Player object to compare.
     * @return true if all the attributes of both Player objects are equal, false otherwise.
     */
    boolean equals(Player p1, Player p2) {
        return equals(p1.pseudo, p2.pseudo) && p1.hp == p2.hp && p1.atk == p2.atk && equals(p1.character, p2.character);
    }

    void testEqualsPixel() {
        Pixel p = newPixel('c', ANSI_RED);
        Pixel p1 = newPixel('c', ANSI_RED);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_GREEN);
        p1 = newPixel('c', ANSI_GREEN);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_YELLOW);
        p1 = newPixel('c', ANSI_YELLOW);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_BLUE);
        p1 = newPixel('c', ANSI_BLUE);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_PURPLE);
        p1 = newPixel('c', ANSI_PURPLE);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_CYAN);
        p1 = newPixel('c', ANSI_CYAN);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_WHITE);
        p1 = newPixel('c', ANSI_WHITE);
        assertTrue(equals(p, p1));
        p = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        p1 = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        assertTrue(equals(p, p1));
    }

    void testEqualsMob() {
        Mob m = newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10);
        Mob m1 = newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10);
        assertTrue(equals(m, m1));
        m = newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10);
        m1 = newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10);
        assertTrue(equals(m, m1));
        m = newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10);
        m1 = newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10);
        assertTrue(equals(m, m1));
        m = newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10);
        m1 = newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10);
        assertTrue(equals(m, m1));
    }

    void testEqualsScreen() {
        Screen sr = newScreen(2, 2);
        sr.screen[0][0] = newPixel('H', "");
        sr.screen[1][1] = newPixel('Y', "");
        Screen sr1 = newScreen(2, 2);
        sr1.screen[0][0] = newPixel('H', "");
        sr1.screen[1][1] = newPixel('Y', "");
        assertTrue(equals(sr, sr1));
        sr.screen[0][0] = newPixel('H', ANSI_RED);
        sr.screen[1][1] = newPixel('Y', ANSI_GREEN);
        sr1.screen[0][0] = newPixel('H', ANSI_RED);
        sr1.screen[1][1] = newPixel('Y', ANSI_GREEN);
        assertTrue(equals(sr, sr1));
    }

    void testEqualsQuestion() {
        Question q = newQuestion("2 + 2", "4");
        Question q1 = newQuestion("2 + 2", "4");
        assertTrue(equals(q, q1));
        q = newQuestion("2 * 2", "4");
        q1 = newQuestion("2 * 2", "4");
        assertTrue(equals(q, q1));
        q = newQuestion("2 / 2", "1");
        q1 = newQuestion("2 / 2", "1");
        assertTrue(equals(q, q1));
        q = newQuestion("2 - 2", "0");
        q1 = newQuestion("2 - 2", "0");
        assertTrue(equals(q, q1));
    }

    void testEqualsScore() {
        Score s = newScore("toto", 10);
        Score s1 = newScore("toto", 10);
        assertTrue(equals(s, s1));
        s = newScore("toto", 10);
        s1 = newScore("toto", 10);
        assertTrue(equals(s, s1));
        s = newScore("toto", 10);
        s1 = newScore("toto", 10);
        assertTrue(equals(s, s1));
        s = newScore("toto", 10);
        s1 = newScore("toto", 10);
        assertTrue(equals(s, s1));
    }

    void testEqualsPlayer() {
        Player p = newPlayer("toto", newScreen(10, 10));
        Player p1 = newPlayer("toto", newScreen(10, 10));
        assertTrue(equals(p, p1));
        p = newPlayer("toto", newScreen(10, 10));
        p1 = newPlayer("toto", newScreen(10, 10));
        assertTrue(equals(p, p1));
        p = newPlayer("toto", newScreen(10, 10));
        p1 = newPlayer("toto", newScreen(10, 10));
        assertTrue(equals(p, p1));
        p = newPlayer("toto", newScreen(10, 10));
        p1 = newPlayer("toto", newScreen(10, 10));
        assertTrue(equals(p, p1));
    }


    // basic functions


    
    /**
     * Return the ANSI color code corresponding to the given color.
     * @param color (available colors: black, red, green, yellow, blue, purple, cyan, white)
     * @return string of ANSI color code corresponding to the given color
     */
    String getANSI_COLOR(String color) {
        String result = "";
        switch (color) {
            case "black":
                result = ANSI_BLACK;
                break;
            case "red":
                result = ANSI_RED;
                break;
            case "green":
                result = ANSI_GREEN;
                break;
            case "yellow":
                result = ANSI_YELLOW;
                break;
            case "blue":
                result = ANSI_BLUE;
                break;
            case "purple":
                result = ANSI_PURPLE;
                break;
            case "cyan":
                result = ANSI_CYAN;
                break;
            case "white":
                result = ANSI_WHITE;
                break;
            default:
                result = ANSI_TEXT_DEFAULT_COLOR;
                break;
        }
        return result;
    }

    void testGetANSI_COLOR() {
        assertEquals(ANSI_BLACK, getANSI_COLOR("black"));
        assertEquals(ANSI_RED, getANSI_COLOR("red"));
        assertEquals(ANSI_GREEN, getANSI_COLOR("green"));
        assertEquals(ANSI_YELLOW, getANSI_COLOR("yellow"));
        assertEquals(ANSI_BLUE, getANSI_COLOR("blue"));
        assertEquals(ANSI_PURPLE, getANSI_COLOR("purple"));
        assertEquals(ANSI_CYAN, getANSI_COLOR("cyan"));
        assertEquals(ANSI_WHITE, getANSI_COLOR("white"));
        assertEquals(ANSI_TEXT_DEFAULT_COLOR, getANSI_COLOR("default"));
    }


    /**
     * Returns an int corresponding to the given string.
     * @param text containing only numbers
     * @return int corresponding to the given string
     */
    int StringToInt(String text) {
        int result = 0;
        int cpt = 0;
        while (cpt < length(text)) {
            result = result * 10 + charToInt(charAt(text, cpt));
            cpt = cpt + 1;
        }
        return result;
    }

    void testStringToInt() {
        assertEquals(0, StringToInt("0"));
        assertEquals(1, StringToInt("1"));
        assertEquals(12, StringToInt("12"));
        assertEquals(123, StringToInt("123"));
        assertEquals(1234, StringToInt("1234"));
        assertEquals(12345, StringToInt("12345"));
        assertEquals(123456, StringToInt("123456"));
        assertEquals(1234567, StringToInt("1234567"));
        assertEquals(12345678, StringToInt("12345678"));
        assertEquals(123456789, StringToInt("123456789"));
    }

    /**
     * Look into LIST_EMPTY if the given char is empty. 
     * @param c char to test
     * @return true if the given char is empty, false otherwise
     */
    boolean isEmpty(char c) {
        int cpt = 0;
        boolean result = false;
        while (cpt < length(LIST_EMPTY) && !result) {
            result = c == LIST_EMPTY[cpt];
            cpt = cpt + 1;
        }
        return result;
    }

    void testIsEmpty() {
        assertTrue(isEmpty(' '));
        assertTrue(isEmpty(' '));
        assertTrue(isEmpty(' '));
        assertTrue(isEmpty(' '));
        assertTrue(isEmpty(' '));
        assertTrue(isEmpty(' '));
        assertTrue(isEmpty(' '));
    }

    /**
     * Returns the index of the first occurence of the given char in the given string.
     * @param text string to search in
     * @param target char to search
     * @return index of the first occurence of the given char in the given string, -1 if not found
     */
    int IndexFirst(String text, char target) {
        int result = -1;
        int cpt = 0;
        while (cpt < length(text) && result == -1) {
            if (charAt(text, cpt) == target) {
                result = cpt;
            }
            cpt = cpt + 1;
        }
        return result;
    }

    void testIndexFirst() {
        assertEquals(0, IndexFirst("toto", 't'));
        assertEquals(1, IndexFirst("toto", 'o'));
    }

    /**
     * Resturns an int representing the number of occurences of the given char in the given string.
     * @param text string to search in
     * @param car char to search
     * @return int representing the number of occurences of the given char in the given string
     */
    int count(String text, char car) {
        int result = 0;
        for (int i = 0; i < length(text); i++) {
            if (charAt(text, i) == car) {
                result = result + 1;
            }
        }
        return result;
    }

    void testCount() {
        assertEquals(0, count("toto", 'a'));
        assertEquals(2, count("toto", 't'));
        assertEquals(2, count("toto", 'o'));
    } 

    boolean contains(int[] list, int target) {
        boolean result = false;
        int cpt = 0;
        while (cpt < length(list) && !result) {
            result = list[cpt] == target;
            cpt = cpt + 1;
        }
        return result;
    }

    void testContains() {
        int[] list = new int[]{1, 2, 3, 4, 5};
        assertTrue(contains(list, 1));
        assertTrue(contains(list, 2));
        assertTrue(contains(list, 3));
        assertTrue(contains(list, 4));
        assertTrue(contains(list, 5));
        assertFalse(contains(list, 6));
        assertFalse(contains(list, 7));
        assertFalse(contains(list, 8));
        assertFalse(contains(list, 9));
        assertFalse(contains(list, 10));
    }

    void reverse(Mob[] list) {
        int cpt = 0;
        int last = length(list) - 1;
        Mob temp;
        while (cpt < length(list)/2) {
            temp = list[cpt];
            list[cpt] = list[last];
            list[last] = temp;
            cpt = cpt + 1;
            last = last - 1;
        }
    }

    void testReverse() {
        Mob[] list = new Mob[]{newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10), newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10), newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10), newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10)};
        reverse(list);
        assertEquals(toString(list[0]), toString(newMob(10, 12, Operation.SOUSTRACTION, newScreen(10, 10), 0, 10)));
        assertEquals(toString(list[1]), toString(newMob(10, 12, Operation.DIVISION, newScreen(10, 10), 0, 10)));
        assertEquals(toString(list[2]), toString(newMob(10, 12, Operation.MULTIPLICATION, newScreen(10, 10), 0, 10)));
        assertEquals(toString(list[3]), toString(newMob(10, 12, Operation.ADDITION, newScreen(10, 10), 0, 10)));
    }



    void reverse(Screen[] list) {
        int cpt = 0;
        int last = length(list) - 1;
        Screen temp;
        while (cpt < length(list)/2) {
            temp = list[cpt];
            list[cpt] = list[last];
            list[last] = temp;
            cpt = cpt + 1;
            last = last - 1;
        }
    }

    void testReverseScreen() {
        Screen[] list = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10)};
        reverse(list);
        assertEquals(toString(list[0]), toString(newScreen(10, 10)));
        assertEquals(toString(list[1]), toString(newScreen(10, 10)));
        assertEquals(toString(list[2]), toString(newScreen(10, 10)));
        assertEquals(toString(list[3]), toString(newScreen(10, 10)));
    }

    int chooseNumber(int min, int max) {
        int result = 0;
        do {
            result = readInt();
        } while (result < min || result > max);
        return result;
    }



    // Screen manipulation functions

    /**
     * Applies a patch to the main screen at the specified position.
     * The patch is applied from the top left corner to the bottom right corner.
     * If the patch is bigger than the main screen, it is cropped.
     * 
     *
     * @param mainScreen The main screen to apply the patch to.
     * @param patch The patch screen to apply.
     * @param h The starting row position on the main screen.
     * @param w The starting column position on the main screen.
     */
    void applyPatch(Screen mainScreen, Screen patch, int h, int w) {
        if ((w < mainScreen.width) && h < mainScreen.height) {
            int cptH_main = h;
            int cptH_patch = 0;
            int cptW_main = w;
            int cptW_patch = 0;
            while (cptH_patch < length(patch.screen, 1) && cptH_main < length(mainScreen.screen, 1)) {
                while (cptW_patch < length(patch.screen, 2) && cptW_main < length(mainScreen.screen, 2)) {
                    if (cptH_main >= 0 && cptW_main >= 0) {
                        mainScreen.screen[cptH_main][cptW_main] = patch.screen[cptH_patch][cptW_patch];
                    }
                    cptW_main = cptW_main + 1;
                    cptW_patch = cptW_patch + 1;
                }
                cptH_main = cptH_main + 1;
                cptH_patch = cptH_patch + 1;
                cptW_main = w;
                cptW_patch = 0;
            }
            
        }

    }

    /**
     * Applies a patch to the main screen at the specified position.
     * The patch is applied from the top left corner to the bottom right corner.
     * If the patch is bigger than the main screen, it is cropped.
     * eraseNonEmpty allow to apply the patch as a .png file.
     * meaning that if the pixel of the patch is not empty, it will be applied.
     *
     * @param mainScreen    the main screen to apply the patch to
     * @param patch         the patch screen to apply
     * @param h             the starting height position on the main screen
     * @param w             the starting width position on the main screen
     * @param eraseNonEmpty specifies whether to erase non-empty pixels on the main screen
     */
    void applyPatch(Screen mainScreen, Screen patch, int h, int w, boolean eraseNonEmpty) {
        if ((w < mainScreen.width) && h < mainScreen.height) {
            int cptH_main = h;
            int cptH_patch = 0;
            int cptW_main = w;
            int cptW_patch = 0;
            while (cptH_patch < length(patch.screen, 1) && cptH_main < length(mainScreen.screen, 1)) {
                while (cptW_patch < length(patch.screen, 2) && cptW_main < length(mainScreen.screen, 2)) {
                    if (!eraseNonEmpty && !equals(patch.screen[cptH_patch][cptW_patch], EMPTY_PIXEL)) 
                    if (cptH_main >= 0 && cptW_main >= 0) {
                        mainScreen.screen[cptH_main][cptW_main] = patch.screen[cptH_patch][cptW_patch];
                    }
                    cptW_main = cptW_main + 1;
                    cptW_patch = cptW_patch + 1;
                }
                cptH_main = cptH_main + 1;
                cptH_patch = cptH_patch + 1;
                cptW_main = w;
                cptW_patch = 0;
            }
            
        }

    }

    /**
     * Removes a patch from the main screen at the specified position.
     *
     * @param mainScreen the main screen from which the patch will be removed
     * @param patch the patch to be removed
     * @param h the vertical position of the patch on the main screen
     * @param w the horizontal position of the patch on the main screen
     */
    void removePatch(Screen mainScreen, Screen patch, int h, int w) {
        Screen n = newScreen(patch.height, patch.width);
        for (int i = 0; i < length(n.screen, 1); i++) {
            for (int j = 0; j < length(n.screen, 2); j++) {
                n.screen[i][j] = newPixel(EMPTY, ANSI_TEXT_DEFAULT_COLOR);
            }
        }
        applyPatch(mainScreen, n, h, w);
    }

    /**
     * Removes a patch from the main screen with optional transition effect.
     *
     * @param mainScreen The main screen from which the patch will be removed.
     * @param patch The patch screen to be removed.
     * @param h The horizontal position of the patch on the main screen.
     * @param w The vertical position of the patch on the main screen.
     * @param transition Determines whether to apply a transition effect during removal.
     */
    void removePatch(Screen mainScreen, Screen patch, int h, int w, boolean transistion) {
        if (!transistion) {
            removePatch(mainScreen, patch, h, w);
        }
        else {
            Screen n = newScreen(1, patch.width);
            drawHorizontalLine(n, 0);
            for (int i = 0; i < patch.height+1; i++) {
                removePatch(patch, n, i-1, 0);
                applyPatch(patch, n, i, 0);
                applyPatch(mainScreen, patch, h, w);
                refresh();
            }
        }
        
    }

    

    /**
     * Moves the patch to the right on the main screen.
     *
     * @param mainScreen the main screen where the patch is located
     * @param patch the patch to be moved
     * @param curH the current height position of the patch
     * @param curW the current width position of the patch
     */
    void moveRight(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH, curW+1);     
    }

    /**
     * Moves the patch to the left on the main screen.
     *
     * @param mainScreen the main screen where the patch is located
     * @param patch the patch to be moved
     * @param curH the current height position of the patch
     * @param curW the current width position of the patch
     */
    void moveLeft(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH, curW-1);     
    }

    /**
     * Moves the patch on top of the screen by removing it from the current position and applying it to the position above.
     *
     * @param mainScreen the main screen object
     * @param patch the patch object to be moved
     * @param curH the current height position of the patch
     * @param curW the current width position of the patch
     */
    void moveTop(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH - 1, curW);
    }

    /**
     * Moves the patch to the bottom of the screen.
     *
     * @param mainScreen the main screen object
     * @param patch the patch object to be moved
     * @param curH the current height position of the patch
     * @param curW the current width position of the patch
     */
    void moveBottom(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH + 1, curW);     
    }



    /**
     * Moves the patch on the screen from the current position to the target position.
     *
     * @param mainScreen The main screen where the patch is located.
     * @param patch The patch to be moved.
     * @param curH The current horizontal position of the patch.
     * @param curW The current vertical position of the patch.
     * @param targetH The target horizontal position of the patch.
     * @param targetW The target vertical position of the patch.
     * @param speed The speed at which the patch moves.
     */
    void moveTo(Screen mainScreen, Screen patch, int curH, int curW, int targetH, int targetW, int speed) {
        if (targetH > curH) {
            while (curH != targetH) {
                moveBottom(mainScreen, patch, curH, curW);
                curH = curH + 1;
                if (curH % speed == 0) {
                    refresh();
                }
            }
        }
        else if (targetH < curH) {
            while (curH != targetH) {
                moveTop(mainScreen, patch, curH, curW);
                curH = curH - 1;
                if (curH % speed == 0) {
                    refresh();
                }
            }
        }
        if (targetW > curW) {
            while (curW != targetW) {
                moveRight(mainScreen, patch, curH, curW);
                curW = curW + 1;
                if (curW % speed == 0) {
                    refresh();
                }
            }
        }
        else if (targetW < curW) {
            while (curW != targetW) {
                moveLeft(mainScreen, patch, curH, curW);
                curW = curW - 1;
                if (curW % speed == 0) {
                    refresh();
                }
            }
        }
        refresh();
    }

    

    /**
     * Draws a vertical line on the given screen.
     *
     * @param mainScreen the screen on which the line will be drawn
     * @param w the width of the line
     * @param color the color of the line
     */
    void drawVerticalLine(Screen mainScreen, int w, String color) {
        Screen line = newScreen(mainScreen.height, 1);
        for (int i = 1; i < line.height; i++) {
            line.screen[i][0] = newPixel('▐', color);
        }
        applyPatch(mainScreen, line, 0, w);
    }

    /**
     * Draws a vertical line on the given screen.
     *
     * @param mainScreen the screen on which the line will be drawn
     * @param w the width of the line
     */
    void drawVerticalLine(Screen mainScreen, int w) {
        Screen line = newScreen(mainScreen.height, 1);
        for (int i = 1; i < line.height; i++) {
            line.screen[i][0] = newPixel('▐', "");
        }
        applyPatch(mainScreen, line, 0, w);
    }

    /**
     * Draws a horizontal line on the given screen at the specified height with the specified color.
     *
     * @param mainScreen The main screen on which the line will be drawn.
     * @param h The height at which the line will be drawn.
     * @param color The color of the line.
     */
    void drawHorizontalLine(Screen mainScreen, int h, String color) {
        Screen line = newScreen(1, mainScreen.width);
        for (int i = 0; i < line.width; i++) {
            line.screen[0][i] = newPixel('▁', color);
        }
        applyPatch(mainScreen, line, h, 0);
    }

    /**
     * Draws a horizontal line on the given screen at the specified height.
     *
     * @param mainScreen the main screen on which the line will be drawn
     * @param h the height at which the line will be drawn
     */
    void drawHorizontalLine(Screen mainScreen, int h) {
        Screen line = newScreen(1, mainScreen.width);
        for (int i = 0; i < line.width; i++) {
            line.screen[0][i] = newPixel('▁', "");
        }
        applyPatch(mainScreen, line, h, 0);
    }

    /**
     * Draws borders on the screen with the specified color.
     *
     * @param screen The screen object on which the border will be drawn.
     * @param color The color of the border.
     */
    void drawBorder(Screen screen, String color) {
        drawHorizontalLine(screen, 0, color);
        drawHorizontalLine(screen, screen.height-1, color);
        drawVerticalLine(screen, 0, color);
        drawVerticalLine(screen, screen.width-1, color);
    }

    // screen calculation functions

    /**
     * Calculates the sum of the widths of the screens.
     *
     * @param screens an array of Screen objects
     * @return the sum of the widths of the screens
     */
    int sumWidth(Screen[] screens) {
        int result = 0;
        for (int i = 0; i < length(screens); i++) {
            if (screens[i] != null ) {
                result = result + screens[i].width;
            }
        }
        return result;
    }

    /**
     * Calculates the sum of the widths of the screens. stop at stop index
     *
     * @param screens an array of Screen objects
     * @param stop the index to stop at
     * @return the sum of the widths of the screens
     */
    int sumWidth(Screen[] screens, int stop) {
        int result = 0;
        for (int i = 0; i < stop; i++) {
            if (screens[i] != null ) {
                result = result + screens[i].width;
            }
        }
        return result;
    }

    /**
     * Calculates the sum of the heights of the screens.
     *
     * @param screens an array of Screen objects
     * @return the sum of the heights of the screens
     */
    int sumHeight(Screen[] screens) {
        int result = 0;
        for (int i = 0; i < length(screens); i++) {
            if (screens[i] != null ) {
                result = result + screens[i].height;
            }
        }
        return result;
    }

    /**
     * Calculates the max of the heights of the screens.
     *
     * @param screens an array of Screen objects
     * @return the max of the heights of the screens
     */
    int maxHeight(Screen[] screens) {
        int result = 0;
        for (int i = 0; i < length(screens); i++) {
            if (screens[i] != null && screens[i].height > result) {
                result = screens[i].height;
            }
        }
        return result;
    }

    /**
     * Calculates the max of the widths of the screens.
     *
     * @param screens an array of Screen objects
     * @return the max of the widths of the screens
     */
    int maxWidth(Screen[] screens) {
        int result = 0;
        for (int i = 0; i < length(screens); i++) {
            if (screens[i] != null && screens[i].width > result) {
                result = screens[i].width;
            }
        }
        return result;
    }
    // screen creation/conversion functions

    /**
     * Loads an ASCII file and creates a screen object with the specified color.
     * ASCII must be rectangular.
     * 
     *
     * @param file The path of the ASCII file to load.
     * @param color The color of the ASCII characters.
     * @return A screen object representing the loaded ASCII file.
     */
    Screen loadASCII(String file, String color) {
        String text = fileAsString(file);
        int height = count(text, '\n');
        int width = length(substring(text, 0, IndexFirst(text, '\n')));
        Pixel[][] screen = new Pixel[height][width];
        int cptW = 0;
        char current = '\0';
        int cpt = 0;
        for (int i = 0; i < height; i++) {
            while ( current != '\n' && cpt < length(text) && cptW < width) {
                current = charAt(text, cpt);
                if (isEmpty(current)) {
                    current = ' ';
                }
                screen[i][cptW] = newPixel(current, color);
                cpt = cpt + 1;
                cptW = cptW + 1;
            }
            screen[i][cptW-1].c = screen[i][cptW-1].c + ANSI_RESET;
            cptW = 0;
            cpt = cpt + 1;
        }
        Screen result = newScreen(height, width);
        result.screen = screen;
        return result;
    }

    void setcolor(Screen screen, String color) {
        for (int i = 1; i < length(screen.screen, 1); i++) {
            for (int j = 0; j < length(screen.screen, 2); j++) {
                Pixel p = screen.screen[i][j];
                screen.screen[i][j].c = color + p.c;
            }
            screen.screen[i][length(screen.screen, 2)-1].c = screen.screen[i][length(screen.screen, 2)-1].c + ANSI_RESET;
        }
    }


    /**
     * Retrieves a Screen type based on the given number and color.
     * The number must be under 1000.
     *
     * @param number The number to be displayed on the screen.
     * @param color  The color of the number on the screen.
     * @return The Screen object representing the number on the screen.
     */
    Screen getNumber(int number, String color) {
        if (number < 1000 && number > 0) {
            Screen[] list_nb = new Screen[3];
            for (int i = 0; i < length(list_nb); i++) {
                list_nb[i] = newScreen(0, 0);
            }
            int cpt = 0;
            while (number > 0) {
                int r = number % 10;
                number = number / 10;
                list_nb[cpt] = loadASCII(NUMBERS_DIR + "/" + r + ".txt", color);
                cpt = cpt + 1;
            }
            Screen number_ASCII = newScreen(list_nb[0].height, list_nb[0].width + list_nb[1].width + list_nb[2].width);
            int withDec = 0;
            for (int i = length(list_nb) - 1; i >= 0; i--) {
                applyPatch(number_ASCII, list_nb[i], 0, withDec);
                withDec = withDec + list_nb[i].width;
            }


            return number_ASCII;        }
        else {
            Screen number_ASCII = loadASCII(NUMBERS_DIR + "/" + "0" + ".txt", color);
            return number_ASCII;
        }
    }

    /**
     * Generates a Screen object based on the given text and color.
     *
     * @param text  the text to be displayed on the screen
     * @param color the color of the text (optional, default is ANSI_TEXT_DEFAULT_COLOR)
     * @return a Screen object representing the generated text
     */
    Screen genText(String text, String color) {
        if (equals(color, "")) {
            color = ANSI_TEXT_DEFAULT_COLOR;
        }
        int maxheight = 0;
        char current;
        int lastWith = 0;
        Screen[] listASCII =  new Screen[length(text)];
        for (int i = 0; i < length(listASCII); i++) {
            current = charAt(text, i);
            if ((current >= 'a' && current <= 'z') || (current >= 'A' && current <= 'Z') || current == ' '){
                if (current == ' ') {
                    listASCII[i] = loadASCII(getletterPath(current), color);
                }
                else {
                    current = charAt(toUpperCase(current + ""), 0);
                    listASCII[i] = loadASCII(getletterPath(current), color);
                }
                
                if (listASCII[i].height > maxheight) {
                    maxheight = listASCII[i].height;
                }
            }
        }
        Screen generatedtext = newScreen(maxheight, sumWidth(listASCII));
        for (int i = 0; i < length(listASCII); i++) {
            if (listASCII[i] != null && listASCII[i].screen != null) {
                applyPatch(generatedtext, listASCII[i], 0, lastWith);
                lastWith = lastWith + listASCII[i].width;
            }
        }
         return generatedtext;

    }

    /**
     * Fits the given text onto the screen by creating a new screen object.
     *
     * @param screen The screen object to fit the text onto.
     * @param chaine The text to fit onto the screen.
     * @return A screen object representing the fitted text.
     */
    Screen fitText(Screen screen, String chaine) {
        if (length(chaine)*8 < screen.width) {
            return genText(chaine, ANSI_WHITE);
        }
        else {
            int cpt = 0;
            Screen tempSR = newScreen(screen.height, screen.width);
            Screen result = newScreen(0, 0);
            int curH = 0;
            int curW = 0;
            Screen temp;
            int base_width = genText("a", ANSI_WHITE).width; 
            int base_height = genText("a", ANSI_WHITE).height; 
            while (cpt < length(chaine) ) {
                temp = genText(charAt(chaine, cpt)+"", ANSI_WHITE);
                tempSR = copy(result);
                result = newScreen(curH+base_height, screen.width);
                applyPatch(result, tempSR, 0, 0);
                applyPatch(result, temp, curH, curW);
                curW = curW + temp.width;
                if (curW > screen.width - base_width) {
                    curH = curH + temp.height+2;
                    curW = 0;
                }
                cpt = cpt + 1;
            }
            return result;
        }
    }

    /**
     * Retrieves the screen associated with the specified operation.
     *
     * @param op The operation for which to retrieve the screen.
     * @return The screen associated with the specified operation, or null if no screen is found.
     */
    Screen getOpScreen(Operation op) {
        Screen result = null;
        switch (op) {
            case ADDITION:
                result = LIST_OPERATOR[0];
                break;
            case SOUSTRACTION:
                result = LIST_OPERATOR[1];
                break;
            case MULTIPLICATION:
                result = LIST_OPERATOR[2];
                break;
            case DIVISION:
                result = LIST_OPERATOR[3];
                break;
            default:
                result = null;
                break;
        }
        return result;
    }

    Screen genHorizontalList(Screen[] list, int gap) {
        Screen result = newScreen(maxHeight(list)+2, sumWidth(list) + length(list)*gap);
        int cpt = 0;
        int curW = gap;
        while (cpt < length(list)) {
            if (list[cpt] != null) {
                applyPatch(result, list[cpt], 2, curW);
                applyPatch(result, getNumber(cpt+1, ANSI_WHITE), 1, curW, false);
                curW = curW + list[cpt].width + gap;
            }
            cpt = cpt + 1;
        }
        return result;
    }

    void removeHorizontalList(Screen mainScreen, Screen[] list, int gap, int h, int w) {
        Screen result = newScreen(maxHeight(list)+2, sumWidth(list) + length(list)*gap);
        removePatch(mainScreen, result, h, w);
    }
    void removeHorizontalList(Screen mainScreen, Screen[] list, int gap, int h, int w, boolean transistion) {
        if (!transistion) {
            removeHorizontalList(mainScreen, list, gap, h, w);
        }
        int cpt = 0;
        int curW = gap;
        while (cpt < length(list)) {
            if (list[cpt] != null) {
                removePatch(mainScreen, list[cpt], h+2, curW, true);
                removePatch(mainScreen, getNumber(cpt+1, ANSI_WHITE), h+1, curW);
                curW = curW + list[cpt].width + gap;
            }
            cpt = cpt + 1;
        }
    }


    void removeHorizontalList(Screen mainScreen, Screen[] list, int gap, int h, int w, boolean transistion, int[] excludeIndexs) {
        if (!transistion) {
            removeHorizontalList(mainScreen, list, gap, h, w);
        }
        int cpt = 0;
        int curW = gap;
        while (cpt < length(list)) {
            if (list[cpt] != null && !contains(excludeIndexs, cpt)) {
                removePatch(mainScreen, list[cpt], h+2, curW, true);
                removePatch(mainScreen, getNumber(cpt+1, ANSI_WHITE), h+1, curW);
                curW = curW + list[cpt].width + gap;
            }
            else if (contains(excludeIndexs, cpt)) {
                removePatch(mainScreen, getNumber(cpt+1, ANSI_WHITE), h+1, curW);
                applyPatch(mainScreen, list[cpt], h+2, curW);
                curW = curW + list[cpt].width + gap;

            }
            cpt = cpt + 1;
        }
    }

    Screen genVerticalList(Screen[] list, int gap) {
        Screen result = newScreen(sumHeight(list) + length(list)*gap, maxWidth(list)+2);
        int cpt = 0;
        int curH = gap;
        while (cpt < length(list)) {
            if (list[cpt] != null) {
                applyPatch(result, list[cpt], curH, 1);
                applyPatch(result, getNumber(cpt+1, ANSI_WHITE), curH, 0, false);
                curH = curH + list[cpt].height + gap;
            }
            cpt = cpt + 1;
        }
        return result;
    }

    void removeVerticalList(Screen mainScreen, Screen[] list, int gap, int h, int w) {
        Screen result = newScreen(sumHeight(list) + length(list)*gap, maxWidth(list)+2);
        removePatch(mainScreen, result, h, w);
    }

    void removeVerticalList(Screen mainScreen, Screen[] list, int gap, int h, int w, boolean transistion) {
        if (!transistion) {
            removeVerticalList(mainScreen, list, gap, h, w);
        }
        int cpt = 0;
        int curH = h+gap;
        while (cpt < length(list)) {
            if (list[cpt] != null) {
                removePatch(mainScreen, list[cpt], curH, w+1, true);
                removePatch(mainScreen, getNumber(cpt+1, ANSI_WHITE), curH, w);
                curH = curH + list[cpt].height + gap;
            }
            cpt = cpt + 1;
        }
    }

    void removeVerticalList(Screen mainScreen, Screen[] list, int gap, int h, int w, boolean transistion, int[] excludeIndexs) {
        if (!transistion) {
            removeVerticalList(mainScreen, list, gap, h, w);
        }
        int cpt = 0;
        int curH = h + gap;
        while (cpt < length(list)) {
            if (list[cpt] != null && !contains(excludeIndexs, cpt)) {
                removePatch(mainScreen, list[cpt], curH, w+1, true);
                removePatch(mainScreen, getNumber(cpt+1, ANSI_WHITE), curH, w);
                curH = curH + list[cpt].height + gap;
            }
            else if (contains(excludeIndexs, cpt)) {
                curH = curH + list[cpt].height + gap;
            }
            cpt = cpt + 1;
        }
    }

    Screen genLittleText(String text, String color) {
        Screen result = newScreen(1, length(text));
        for (int i = 0; i < length(text); i++) {
            result.screen[0][i] = newPixel(charAt(text, i), color);
        }
        return result;
    }


    /////////////////////////////
    //// Operation funtions /////
    /////////////////////////////

    Operation getOperation(String operation) {
        if (equals(operation, "addition")) {
            return Operation.ADDITION;
        }
        else if (equals(operation, "multiplication")) {
            return Operation.MULTIPLICATION;
        }
        else if (equals(operation, "division")) {
            return Operation.DIVISION;
        }
        else if (equals(operation, "soustraction")) {
            return Operation.SOUSTRACTION;
        }
        else {
            return Operation.ADDITION;
        }
        
    }

    void testGetOperation() {
        assertEquals(getOperation("addition"), Operation.ADDITION);
        assertEquals(getOperation("multiplication"), Operation.MULTIPLICATION);
        assertEquals(getOperation("division"), Operation.DIVISION);
        assertEquals(getOperation("soustraction"), Operation.SOUSTRACTION);
    }

    String getWeaknessColor(String faiblesse) {
        String result = "";
        switch (faiblesse) {
            case "addition":
                result = ANSI_RED;
                break;
            case "multiplication":
                result = ANSI_YELLOW;
                break;
            case "division":
                result = ANSI_BLUE;
                break;
            case "soustraction":
                result = ANSI_GREEN;
                break;
            default:
                result = ANSI_TEXT_DEFAULT_COLOR;
                break;
        }
        return result;
    }

    void testGetWeaknessColor() {
        assertEquals(getWeaknessColor("addition"), ANSI_RED);
        assertEquals(getWeaknessColor("multiplication"), ANSI_YELLOW);
        assertEquals(getWeaknessColor("division"), ANSI_BLUE);
        assertEquals(getWeaknessColor("soustraction"), ANSI_GREEN);
    }



    /////////////////////////////////////
    //////     game functions       /////
    /////////////////////////////////////

    Screen mobEntrance(Mob[] mobs) {
        int gap = 5;
        Screen result = genVerticalList(getMobScreens(mobs), gap);
        moveTo(main, result, main.height/2 - result.height/2, main.width, main.height/2 - result.height/2, main.width-maxWidth(getMobScreens(mobs)) - 30, 5);
        mobs[0].posx = main.width-maxWidth(getMobScreens(mobs)) - 29;
        mobs[0].posy = main.height/2 - result.height/2 + gap;
        for (int i = 1; i < length(mobs); i++) {
            mobs[i].posx = main.width-maxWidth(getMobScreens(mobs)) - 29;
            mobs[i].posy = mobs[i-1].posy + mobs[i-1].visuel.height + gap;
        }
        return result;
    }

    void killMobAnim(Screen[] mobs, int indexKilled) {
        int[] alive = new int[length(mobs) - 1];
        indexKilled = length(mobs) - indexKilled - 1;
        int cpt =0;
        for (int i = 0; i < length(alive); i++) {
            if (i != indexKilled) {
                alive[cpt] = i;
                cpt = cpt + 1;
            }
        }
        Screen list = genVerticalList(mobs, 10);
        removeVerticalList(main, mobs, 10, main.height/2-list.height/2, main.width-maxWidth(mobs) - 30, true, alive);
    }

    Mob[] genMob(int nombre) {
        loadMob();
        Mob[] lsMob = new Mob[nombre];
        Mob mob ;
        int posy_last = 0;
        int cpt = 0;
        while (cpt < nombre && posy_last < main.height) {
            mob = copy(randomChoice(listMob));
            // mob.posx = main.width - mob.visuel.width - 30;
            // mob.posy = posy_last + main.height/(nombre * 2) - mob.visuel.height/2;
            // applyPatch(main, mob.visuel, mob.posy, main.height+mob.posx);
            // moveTo(main, mob.visuel, mob.posy, main.height+mob.posx, mob.posy, mob.posx, 5);
            lsMob[cpt] = mob;
            cpt = cpt + 1;
            // posy_last = posy_last + main.height/nombre;
            refresh();
        }
        return lsMob;
    }

    boolean allDead(Mob[] list) {
        boolean result = true;
        int cpt = 0;
        while (cpt < length(list) && result) {
            result = list[cpt].dead;
            cpt = cpt + 1;
        }
        return result;
    }
    
    void updateMobHpBar(Mob mob) {       
        Screen hpBar = newScreen(1, mob.hp*20/mob.initialHp);

        removePatch(main, newScreen(1, 20), mob.posy + mob.visuel.height, mob.posx);
        
        if (mob.hp > (mob.initialHp/4)*3) {
            drawHorizontalLine(hpBar, 0, ANSI_GREEN);
        }
        else if (mob.hp > (mob.initialHp/4)) {
            drawHorizontalLine(hpBar, 0, ANSI_YELLOW);
        }
        else {
            drawHorizontalLine(hpBar, 0, ANSI_RED);
        }
        applyPatch(main, hpBar, mob.posy + mob.visuel.height, mob.posx);
    }

    Screen[] getMobScreens(Mob[] list) {
        Screen[] result = new Screen[length(list)];
        for (int i = 0; i < length(list); i++) {
            result[i] = list[i].visuel;
        }
        return result;
    }

    void updateMobBattle(Mob[] lsMob) {
        Mob mob;
        for (int i = 0; i < length(lsMob); i++) {
            mob = lsMob[i];
            if (mob.hp <= 0 && !mob.dead) {
                removePatch(main, mob.visuel, mob.posy, mob.posx, true);
                removePatch(main, newScreen(mob.visuel.height+2, mob.visuel.width+2), mob.posy-1, mob.posx-1);
                refresh();
                mob.dead = true;
                mob.hp = 0;
            }
            else {
                updateMobHpBar(mob);
            }
            
        }
    }

    void printPlayerHp() {
        Screen hp = newScreen(9, 50);
        String color;
        if (player.hp > 75) {
            color = ANSI_GREEN;
        }
        else if (player.hp > 50) {
            color = ANSI_YELLOW;
        }
        else {
            color = ANSI_RED;
        }
        Screen text = genText("hp ", ANSI_WHITE);
        applyPatch(hp, text, 2, 2);
        applyPatch(hp, getNumber(player.hp, color), 2, text.width + 2);
        applyPatch(main, hp, main.height - hp.height, 50);
        
    }

    void printAttack() {
        int width = 50;
        int height = 20;
        int dec = 10;
        Screen attack = newScreen(height, width);
        drawHorizontalLine(attack, 0, "");
        drawVerticalLine(attack, width -1, "");
        applyPatch(attack, LIST_OPERATOR[0], dec/2, dec);
        applyPatch(attack, LIST_OPERATOR[1], dec/2 + LIST_OPERATOR[0].height + dec/2, dec);
        applyPatch(attack, LIST_OPERATOR[2], dec/2, dec + LIST_OPERATOR[0].width + dec*2);
        applyPatch(attack, LIST_OPERATOR[3], dec/2 + LIST_OPERATOR[2].height + dec/2, dec + LIST_OPERATOR[1].width + dec*2);

        applyPatch(main, attack, main.height - height, 0);
        refresh();

    }


    Operation selectAttaque() {
        int choice = 0;
        Operation op = null;
        do {
            choice = readChar();
            switch (choice) {
                case '+':
                    op = Operation.ADDITION;
                    break;
                case '-':
                    op = Operation.SOUSTRACTION;
                    break;
                case '*':
                    op = Operation.MULTIPLICATION;
                    break;
                case '/':
                    op = Operation.DIVISION;
                    break;
                default:
                    op = null;
                    break;
            }
        } while (op == null);
        return op;
    }

    void updateBattle(int waweNumber, Mob[] listToDefeat) {
        Screen wawe = newScreen(10, 30);
        applyPatch(wawe, getNumber(waweNumber, ANSI_WHITE), 2,  5);
        applyPatch(wawe, getOpScreen(Operation.DIVISION), 3, 12);
        applyPatch(wawe, getNumber(2, ANSI_WHITE), 2, 19);
        applyPatch(main, wawe, 0, 55);
        updateMobBattle(listToDefeat);
        printPlayerHp();
        refresh();
    }


    boolean genWawe( int waweNumber, int difficulty) {
        Mob[] listToDefeat = genMob( waweNumber);
        boolean gameOver = false;
        int cpt = 0;
        Operation op;
        mobEntrance(listToDefeat);
        while (!allDead(listToDefeat) && !gameOver ) {
            updateBattle(waweNumber, listToDefeat);
            print("quelle attaque voulez vous faire ? (+ - * /)");
            op = selectAttaque();
            println("Qui voulez vous attaquer ? ");
            int choice = chooseNumber(1, length(listToDefeat)) - 1;
            print(choice);
            if (!listToDefeat[choice].dead) {
                attaquerMob(op, difficulty, listToDefeat[choice]);
                updateMobBattle(listToDefeat);
                if (!listToDefeat[choice].dead) {
                    player.hp = player.hp - damageToPlayer(difficulty, listToDefeat[choice]);
                }
                cpt = cpt + 1;
            }
            else {
                print("Ce mob est deja mort ! ");
                delay(1000);
            }
            
            refresh();

            gameOver = player.hp <= 0;
            
            
        }
        if (gameOver) {
            player.hp = 0;
            
        }
        updateMobBattle(listToDefeat);
        return gameOver;


    }

    void attaquerMob(Operation op, int level, Mob mob) {
        int nb1 = (int) ((random() * 10) * (level+1));
        int nb2 = (int) ((random() * 10) * (level+1));
        if (op == Operation.DIVISION && nb2 == 0) {
            nb2 = 1;
        }
        int response;
        Screen text;
        printCalcul(nb1, nb2, op);
        response = readInt();
        removecalcul(nb1, nb2, op);
        if (calculReussi(nb1, nb2, op, response)) {
            text = genText("OK", ANSI_GREEN);
            applyPatch(main, text,  main.height/2 - text.height/2, main.width/2 - text.width/2);
            mob.hp = mob.hp - damageDoneToMob(mob, op);
            refresh();
            delay(1000);
            removePatch(main, text, main.height/2 - text.height/2, main.width/2 - text.width/2);

        }
        else {
            text = genText("X", ANSI_RED);
            applyPatch(main, text, main.height/2 - text.height/2, main.width/2 - text.width/2);
            refresh();
            delay(1000);
            removePatch(main, text, main.height/2 - text.height/2, main.width/2 - text.width/2);
        }
        if (mob.hp <= 0) {
            mob.hp = 0;
        }
        
    }

   boolean questionBonus() {
        Question q = randomChoice(listQuestion);
        Screen saveScreen = copy(main);
        removePatch(main, main, 0, 0);
        Screen text = fitText(main, q.question);
        Screen title = genText("Question Bonus", ANSI_WHITE);
        applyPatch(main, title, 2, main.width/2-title.width/2);
        applyPatch(main, text, main.height/2-text.height/2, main.width/2-text.width/2);
        refresh();
        String response = readString();
        applyPatch(main, saveScreen, 0, 0);
        //return equals(response, q.reponse);
        if(equals(response,q.reponse)){ //Changement ici
            return true;   
        }else{
            removePatch(main, main, 0, 0);
            Screen title2=genText("Dommage",ANSI_RED); 
            applyPatch(main, title2, main.height/2-title2.height/2, main.width/2-title.width/2);
            refresh();
            delay(3000);
            applyPatch(main,saveScreen,0,0);
            return false;
        }
   }

    void printLevel(int level) {
        Screen levelScreen = newScreen(9, 55);
        Screen text = genText("Level ", ANSI_WHITE);
        applyPatch(levelScreen, text, 2, 2);
        applyPatch(levelScreen, getNumber(level, ANSI_WHITE), 2, text.width);
        applyPatch(main, levelScreen, 0, 0);
        refresh();
    }

    boolean genLevel(int level) {
        int cpt = 0;
        boolean gameOver = false;
        printLevel(level);
        while (cpt < 2 && !gameOver) {
            gameOver = genWawe( cpt+1, level);
            cpt = cpt + 1;
        }
        return gameOver;
    }

    boolean genBoss(int level) {
        boolean gameOver = false;
        Screen levelScreen = newScreen(9, 55);
        applyPatch(levelScreen, genText("BOSS", ANSI_BLUE), 2 , 2);
        applyPatch(main, levelScreen, 0, 0);
        Mob boss = randomChoice(listBoss);
        boss.posx= main.width/2 + main.width/4-boss.visuel.width/2;
        boss.posy = main.height/2-boss.visuel.height/2;
        applyPatch(main, boss.visuel, main.height/2-boss.visuel.height/2, main.width/2 + main.width/4-boss.visuel.width/2);
        while (!gameOver && !boss.dead) {
            updateMobHpBar(boss);
            updateBattle(2, new Mob[]{boss});
            print("quelle attaque voulez vous faire ? (+ - * /)");
            Operation op = selectAttaque();
            attaquerMob(op, level, boss);
            updateMobHpBar(boss);
            updateBattle(2, new Mob[]{boss});
            if (!boss.dead) {
                player.hp = player.hp - damageToPlayer(level, boss);
            }
            refresh();
            gameOver = player.hp <= 0;
            
        }
        refresh();
        return gameOver;
    }

    

    void printCalcul(int nb1, int nb2, Operation op) {
        Screen[] listNb = new Screen[2];
        listNb[0] = getNumber(nb1, ANSI_TEXT_DEFAULT_COLOR);
        listNb[1] = getNumber(nb2, ANSI_TEXT_DEFAULT_COLOR);
        Screen calcul = newScreen(listNb[0].height + 4, listNb[0].width + listNb[1].width + getOpScreen(op).width + 12);
        applyPatch(calcul, listNb[0], 2, 2);
        applyPatch(calcul, getOpScreen(op), 2, listNb[0].width + 4);
        applyPatch(calcul, listNb[1], 2, listNb[0].width + getOpScreen(op).width + 8);
        drawBorder(calcul, ANSI_TEXT_DEFAULT_COLOR);
        applyPatch(main, calcul, main.height/2 - calcul.height/2, main.width/2 - calcul.width/2);
        refresh();
    }

    void removecalcul(int nb1, int nb2, Operation op) {
        Screen[] listNb = new Screen[2];
        listNb[0] = getNumber(nb1, ANSI_TEXT_DEFAULT_COLOR);
        listNb[1] = getNumber(nb2, ANSI_TEXT_DEFAULT_COLOR);
        Screen calcul = newScreen(listNb[0].height + 4, listNb[0].width + listNb[1].width + getOpScreen(op).width + 12);
        applyPatch(calcul, listNb[0], 2, 2);
        applyPatch(calcul, getOpScreen(op), 2, listNb[0].width + 4);
        applyPatch(calcul, listNb[1], 2, listNb[0].width + getOpScreen(op).width + 8);
        drawBorder(calcul, ANSI_TEXT_DEFAULT_COLOR);
        removePatch(main, calcul, main.height/2 - calcul.height/2, main.width/2 - calcul.width/2);
        refresh();
    }


    boolean calculReussi(int nb1, int nb2, Operation op, int response) {
        boolean result = false;
        switch (op) {
            case ADDITION:
                result = nb1 + nb2 == response;
                break;
            case SOUSTRACTION:
                result = nb1 - nb2 == response;
                break;
            case MULTIPLICATION:
                result = nb1 * nb2 == response;
                break;
            case DIVISION:
                result = nb1 / nb2 == response;
                break;
            default:
                result = false;
                break;
        }
        return result;
    }

    void chooseBonus() {
        Screen saveScreen = copy(main);
        Screen text = genText("Choisi un bonus", ANSI_WHITE);
        Screen heal = loadASCII(HEART, ANSI_RED);
        Screen atk = loadASCII(SWORD, ANSI_WHITE);
        removePatch(main, main, 0, 0);
        applyPatch(main, text, 2, main.width/2-text.width/2);
        applyPatch(main, heal, main.height/2 - heal.height/2 + 2, main.width/2 - heal.width/2 - main.width/8);
        applyPatch(main, atk, main.height/2 - atk.height/2 + 2, main.width/2 + atk.width/2 );
        refresh();
        int choice = chooseNumber(1, 2);
        if (choice == 1) {
            player.hp = player.hp + 50;
        }
        else {
            player.atk = player.atk + 5;
        }
        applyPatch(main, saveScreen, 0, 0);
        refresh();

    }



    int damageToPlayer(int level, Mob mob) {
        playSound("./Son/hitHurt.wav");
        return (int) (random()*mob.atk*level) * 3;
    }

    int damageDoneToMob(Mob mob, Operation op) {
        if (op == mob.faiblesse) {
            print("Coup critique ! ");
            playSound("./Son/hitToMob.wav");
            return player.atk * 3;
        }
        else {
            playSound("./Son/hitToMob.wav");
            return player.atk;
        }
    }


    ///////////////////////
    ////  Score funtions //
    ///////////////////////
    

    boolean newPersonnalBestScore(int score, String pseudo) {
        boolean result = true;
        for (int i = 0; i < length(listScore); i++) {
            if (listScore[i].score > score && equals(listScore[i].pseudo, pseudo)) {
                result = false;
            }
        }
        return result;
    }

    boolean isNewPlayer(String pseudo) {
        boolean result = true;
        for (int i = 0; i < length(listScore); i++) {
            if (equals(listScore[i].pseudo, pseudo)) {
                result = false;
            }
        }
        return result;
    }

    void changePersonnalScore(int score, String pseudo) {
        for (int i = 0; i < length(listScore); i++) {
            if (equals(listScore[i].pseudo, pseudo)) {
                listScore[i].score = score;
                return;
            }
        }
    }

    void addScore(String pseudo, int score) {
        Score[] newListScore = new Score[length(listScore) + 1];
        for (int i = 0; i < length(listScore); i++) {
            newListScore[i] = listScore[i];
        }
        newListScore[length(listScore)] = newScore(pseudo, score);
        listScore = newListScore; 
    }

    

    void printScoreTab() {
        Screen scoreTab = newScreen(main.height, main.width/2 + main.width/4);
        Screen title = genText("Score", ANSI_WHITE);    
        Screen text;
        Screen score;
        for (int i = 0; i < length(listScore); i++) {
            text = genLittleText(listScore[i].pseudo, ANSI_BLUE);
            score = genLittleText(listScore[i].score + "", ANSI_RED);
            applyPatch(scoreTab, text, title.height + 3 + 2 * i, scoreTab.width/2 - 20);
            applyPatch(scoreTab, score, title.height + 3 + 2 * i , scoreTab.width/2 + 20);
            drawHorizontalLine(scoreTab, title.height + 3 + 2 * i + 1);
        }
        drawBorder(scoreTab, ANSI_WHITE);
        applyPatch(scoreTab, title, 2, scoreTab.width/2 - title.width/2);
        applyPatch(main, scoreTab, 2, main.width/2 - scoreTab.width/2);
        refresh();

    }

    void sortScore() {
        Score temp;
        for (int i = 0; i < length(listScore); i++) {
            for (int j = 0; j < length(listScore) - 1; j++) {
                if (listScore[j].score < listScore[j+1].score) {
                    temp = listScore[j];
                    listScore[j] = listScore[j+1];
                    listScore[j+1] = temp;
                }
            }
        }
    }

    String getletterPath(char c) {
        if (c == ' ') {
            return RESSOURCES_DIR + "/letters/" + "space" + ".txt";
        }
        return RESSOURCES_DIR + "/letters/" + c + ".txt"; 
    }

    // game events graphics

    void genTitleScreen(){
        Screen partA = loadASCII(LOGO_PART_A, ANSI_RED);
        Screen partB = loadASCII(LOGO_PART_B, ANSI_YELLOW);
        int posX_A = -150;
        int posy_A = 10;
        int posX_B = main.width - (posX_A +  135);
        int posy_B = 25;
        // sliding animation
        for (int i = posX_A; i < 40; i++) {
            posX_A = posX_A + 1;
            posX_B = posX_B - 1;
            moveRight(main, partA, posy_A, i);
            moveLeft(main, partB, posy_B, posX_B);
            refresh();
        } 
        print("             Press enter to start                ");
        readString();
        // falling animation
        for (int i = 0; i < 50; i++) {
            moveTop(main, partA, posy_A - i, posX_A) ;
            moveBottom(main, partB, posy_B + i, posX_B);
            refresh();
        }
    }

    int getPlayerBestScore(String pseudo) {
        extensions.CSVFile f = loadCSV(RESSOURCES_DIR + "/" + "scores.csv");
        int result = 0;
        int cpt = 1;
        while (cpt < rowCount(f) && result == 0) {
            if (equals(getCell(f, cpt, 0), pseudo)) {
                result = StringToInt(getCell(f, cpt, 1));
            }
            cpt = cpt + 1;
        }
        return result;

    }
    Player[] getPlayers() {
        extensions.CSVFile f = loadCSV(RESSOURCES_DIR + "/" + "scores.csv");
        Player[] table = new Player[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            table[i-1] = newPlayer(getCell(f, i, 0), null);
        }
        return table;

    }

    Screen chooseCharacter() {
        Screen choice = newScreen(main.height, main.width);
        Screen prompt = newScreen(main.height/4, main.width);
        int r;
        Screen[] list_perso = new Screen[]{loadASCII(PLAYER, ANSI_RED), loadASCII(PLAYER_2, ANSI_GREEN), loadASCII(PLAYER_3, ANSI_YELLOW), loadASCII(PLAYER_4, ANSI_BLUE)};
        Screen choose_ASCII = genText("Choisi ton personnage", "");
        applyPatch(choice, choose_ASCII, 3, choice.width/2 - choose_ASCII.width/2);
        prompt = genHorizontalList(list_perso, 20);
        applyPatch(main, choice, 0, 0);
        applyPatch(main, prompt, main.height/2 - prompt.height/2, 0, false);
        refresh();
        print("Choose your character : ");
        r = chooseNumber(1, 4);
        int hchoice = main.height/2 - prompt.height/2 + 2;
        int wchoice = sumWidth(list_perso, r-1) + 20*r;
        removeHorizontalList(main, list_perso, 20, main.height/2 - prompt.height/2, 0, true, new int[]{r-1});
        choice = newScreen((main.height/4) * 3, main.width);
        removePatch(main, prompt, choice.height, 0);
        removePatch(main, choice, 0, 0);
        moveTo(main, list_perso[r-1], hchoice, wchoice, (main.height/2 - list_perso[r-1].height/2) -5, 20, 4);
        refresh();
        return list_perso[r-1];

    }


    void refresh() {
        drawHorizontalLine(main, 0, ANSI_TEXT_DEFAULT_COLOR);
        drawHorizontalLine(main, main.height-1, ANSI_TEXT_DEFAULT_COLOR);
        drawVerticalLine(main, 0, ANSI_TEXT_DEFAULT_COLOR);
        drawVerticalLine(main, main.width-1, ANSI_TEXT_DEFAULT_COLOR);
        println(toString(main));
    }

   

    void algorithm() {
        loadMob();
        loadQuestion();
        loadBoss();
        loadScores();
        boolean gameOver = false;
        int level = 1;
        playSound("./Music3.wav");
        print("Entrez votre pseudo : ");
        player = newPlayer(readString(), null);
        genTitleScreen();
        player.character = chooseCharacter();
        printAttack();
        while (!gameOver) {
            gameOver = genLevel(level);
            if (!gameOver && level % 3 == 0) {
                gameOver = genBoss(level);
            }
            if (!gameOver && questionBonus()) {
                chooseBonus();
            }
            level = level + 1;
        }
        refresh();
        reset();
        Screen SR=loadASCII(GAMEOVER,ANSI_RED);
        if (gameOver){
            removePatch(main,main, 0,0);
            applyPatch(main,SR,main.height/2-SR.height/2,main.width/2-SR.width/2);
            refresh();
            reset();
        }
        if (isNewPlayer(player.pseudo)) {
            addScore(player.pseudo, level);
        }
        else if (newPersonnalBestScore(level, player.pseudo)) {
            changePersonnalScore(level, player.pseudo);
        }
        sortScore();
        printScoreTab();
        saveScores();
        reset();
    }

    void _algorithm() {
        // loadScores();
        // printScoreTab();
        // readString();
        // addScore("bonoujour", 55);
        // printScoreTab();
        // readString();
        // saveScores();
        Screen[] list = new Screen[]{loadASCII(PLAYER, ANSI_RED), loadASCII(PLAYER_2, ANSI_GREEN), loadASCII(PLAYER_3, ANSI_YELLOW), loadASCII(PLAYER_4, ANSI_BLUE)};
        applyPatch(main, genHorizontalList(list, 20), 0, 0);
        refresh();
        removeHorizontalList(main, list, 20, 0, 0, true, new int[]{1});
        refresh();
        // loadMob();
        // boolean gameOver = false;
        // int level = 1;
        // playSound("./Music3.wav");
        // print("Entrez votre pseudo : ");
        // player = newPlayer(readString(), null);
        // genTitleScreen();
        // player.character = chooseCharacter();
        // printAttack();
        // while (!gameOver) {
        //     gameOver = genLevel(level);
        //     level = level + 1;
        // }
        // refresh();
        // reset();
        // Screen SR=loadASCII(GAMEOVER,ANSI_RED);
        // if (gameOver){
        //     removePatch(main,main, 0,0);
        //     applyPatch(main,SR,main.height/2-SR.height/2,main.width/2-SR.width/2);
        //     refresh();
        // }
    }

    

}