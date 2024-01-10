class AcademicArena extends Program {

    final int MAXINT = 2147483646;
    final int MININT = -2147483648;

    final String CONFIG_PATH = "AcademicArena.conf";
    final char CONFIG_SEPARATOR = '=';
    final char[] LIST_EMPTY = new char[]{' ', ' ', ' ', ' ', ' ',' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '　', '⠀'};
    final Pixel EMPTY_PIXEL = newPixelEmpty();

    // GAMEPLAY
    final boolean SHOW_RESPONSE = toBoolean(getFromConfigFile("SHOW_RESPONSE"));
    final int DIFFICULTY = StringToInt(getFromConfigFile("DIFFICULTY"));

    // ASCII ART & GRAPHICAL SETTINGS
    final String GAMEOVER = getFromConfigFile("GAMEOVER");
    final String LOGO_PART_A = getFromConfigFile("LOGO_PART_A");
    final String LOGO_PART_B = getFromConfigFile("LOGO_PART_B");
    final String PLAYER = getFromConfigFile("PLAYER");
    final String PLAYER_2 = getFromConfigFile("PLAYER_2");
    final String PLAYER_3 = getFromConfigFile("PLAYER_3");
    final String PLAYER_4 = getFromConfigFile("PLAYER_4");
    final String SWORD = getFromConfigFile("SWORD");
    final String HEART = getFromConfigFile("HEART");
    final char VERTICAL_LINE = charAt(getFromConfigFile("VERTICAL_LINE"), 0);
    final char HORIZONTAL_LINE = charAt(getFromConfigFile("HORIZONTAL_LINE"), 0);
    final char EMPTY = charAt(getFromConfigFile("EMPTY"), 0);

    // OPREATORS
    final String PLUS_ASCII = getFromConfigFile("PLUS_ASCII");
    final String MOINS_ASCII = getFromConfigFile("MOINS_ASCII");
    final String FOIS_ASCII = getFromConfigFile("FOIS_ASCII");
    final String DIVISION_ASCII = getFromConfigFile("DIVISION_ASCII");
    


    // DIRECTORIES
    final String RESSOURCES_DIR = getFromConfigFile("RESSOURCES_DIR");
    final String OPERATOR_DIR = getFromConfigFile("OPERATOR_DIR");
    final String NUMBERS_DIR = getFromConfigFile("NUMBERS_DIR");
    final String MOB_DIR = getFromConfigFile("MOB_DIR");
    final String BOSS_DIR = getFromConfigFile("BOSS_DIR");
    final String LETTER_DIR = getFromConfigFile("LETTER_DIR");
    
    // COLORS
    final String ADD_COLOR = getANSI_COLOR(getFromConfigFile("ADD_COLOR"));
    final String SUB_COLOR = getANSI_COLOR(getFromConfigFile("SUB_COLOR"));
    final String MUL_COLOR = getANSI_COLOR(getFromConfigFile("MUL_COLOR"));
    final String DIV_COLOR = getANSI_COLOR(getFromConfigFile("DIV_COLOR"));

    // SOUND
    final String HIT_SOUND = getFromConfigFile("HIT_SOUND");
    final String HIT_MOB_SOUND = getFromConfigFile("HIT_MOB_SOUND");
    final String MAIN_THEME = getFromConfigFile("MAIN_THEME");

    // CSV
    final String PLAYERS_FILE = getFromConfigFile("PLAYERS_FILE");
    final String SCORE_FILE = getFromConfigFile("SCORE_FILE");
    final String MOBS_FILE = getFromConfigFile("MOBS_FILE");
    final String BONUS_FILE = getFromConfigFile("BONUS_FILE");
    final String BOSS_FILE = getFromConfigFile("BOSS_FILE");

    final Screen[] LIST_OPERATOR = new Screen[]{
        loadASCII(PLUS_ASCII, getWeaknessColor(Operation.ADDITION)), 
        loadASCII(MOINS_ASCII, getWeaknessColor(Operation.SOUSTRACTION)), 
        loadASCII(FOIS_ASCII, getWeaknessColor(Operation.MULTIPLICATION)), 
        loadASCII(DIVISION_ASCII, getWeaknessColor(Operation.DIVISION))
    };

    // VAR
    Question[] listQuestion;
    Mob[] listMob ;
    Mob[] listBoss ;
    Score[] listScore;
    Screen main = newScreen(51,250);
    Player player;



    // fonction qui convertie une chaine de caractere en boolean
    boolean toBoolean(String s) {        
        return equals(s, "TRUE");
    }

    void testToBoolean() {
        assertTrue(toBoolean("TRUE"));
        assertFalse(toBoolean("FALSE"));
    }

    // function to load ressources

    /**
     * Retourne le resultat dans le fichier config concernant la cle donné en parametre.
     */
    String getFromConfigFile(String key) {
        String content = fileAsString(CONFIG_PATH);
        String result = getValue(content, key);
        return result;

    }

    /**
     * Retourne la valeur de la variable demande à partir d'un String representant le fichier config.
     */
    String getValue(String config, String key) {
        String[] lines = split(config, '\n');
        String result = "";
        for (int i = 0; i < length(lines); i++) {
            if (!(charAt(lines[i], 0) == '#')) {
                String start = split(lines[i], CONFIG_SEPARATOR)[0];
                String value = split(lines[i], CONFIG_SEPARATOR)[1];
                if (equals(start, key)) {
                    return value;
                }
            }
            
        }
        return result;
    }

    void testGetValue() {
        String config = "key1=value1\nkey2=value2\nkey3=value3\nkey4=value4\nkey5=value5\nkey6=value6\nkey7=value7\nkey8=value8\nkey9=value9\nkey10=value10\nkey11=value11\nkey12=value12\nkey13=value13";
        assertEquals("value1", getValue(config, "key1"));
        assertEquals("value2", getValue(config, "key2"));
        assertEquals("value3", getValue(config, "key3"));
        assertEquals("value4", getValue(config, "key4"));
        assertEquals("value5", getValue(config, "key5"));
        assertEquals("value6", getValue(config, "key6"));
        assertEquals("value7", getValue(config, "key7"));
        assertEquals("value8", getValue(config, "key8"));
        assertEquals("value9", getValue(config, "key9"));
        assertEquals("value10", getValue(config, "key10"));
        assertEquals("value11", getValue(config, "key11"));
        assertEquals("value12", getValue(config, "key12"));
        assertEquals("value13", getValue(config, "key13"));
        assertEquals("", getValue(config, "pasdansconfig"));
    }
    /**
     * Sépare un String par le separateur donne en parametre.
     */
    String[] split(String s, char separator) {
        int nbOccurences = nbOccurences(s, separator);
        String[] result = new String[nbOccurences + 1];
        int index = 0;
        int start = 0;
        int end = 0;
        for (int i = 0; i < length(s); i++) {
            if (charAt(s, i) == separator) {
                end = i;
                result[index] = substring(s, start, end);
                start = end + 1;
                index = index + 1;
            }
        }
        result[index] = substring(s, start, length(s));
        return result;
    }

    void testSplit() {
        String[] result = split("a,b,c,d", ',');
        assertEquals("a", result[0]);
        assertEquals("b", result[1]);
        assertEquals("c", result[2]);
        assertEquals("d", result[3]);
        result = split("a,b,c,d", 'b');
        assertEquals("a,", result[0]);
        assertEquals(",c,d", result[1]);
        result = split("a,b,c,d", 'd');
        assertEquals("a,b,c,", result[0]);
        assertEquals("", result[1]);
        result = split("a,b,c,d", 'a');
        assertEquals("", result[0]);
        assertEquals(",b,c,d", result[1]);
    }
    /**
     * Compte le nombre de fois qu'un char apparait dans un String.
     */
    int nbOccurences(String s, char separator) {
        int result = 0;
        for (int i = 0; i < length(s); i++) {
            if (charAt(s, i) == separator) {
                result= result + 1;
            }
        }
        return result;

    }

    void testNbOccurences() {
        assertEquals(3, nbOccurences("a,b,c,d", ','));
        assertEquals(0, nbOccurences("a,b,c,d", 'e'));
        assertEquals(1, nbOccurences("a,b,c,d", 'a'));
        assertEquals(1, nbOccurences("a,b,c,d", 'b'));
        assertEquals(1, nbOccurences("a,b,c,d", 'c'));
        assertEquals(1, nbOccurences("a,b,c,d", 'd'));
    }

    
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
        assertEquals(toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" +  ANSI_RESET + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_RESET + ANSI_RESET  , toString(sr));
        sr = newScreen(3, 3);
        assertEquals(3, sr.height);
        assertEquals(3, sr.width);
        assertEquals(toString(newPixelEmpty()) + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_RESET + toString(newPixelEmpty()) + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_RESET + toString(newPixelEmpty()) + toString(newPixelEmpty()) + toString(newPixelEmpty()) + "\n" + ANSI_RESET + ANSI_RESET  , toString(sr));
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
        assertEquals(toString(sr.screen[0][0]) + toString(sr.screen[0][1]) + "\n" + ANSI_RESET + toString(sr.screen[1][0]) + toString(sr.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(sr));
        sr.screen[0][0] = newPixel('H', ANSI_RED);
        sr.screen[1][1] = newPixel('Y', ANSI_GREEN);
        assertEquals(toString(sr.screen[0][0]) + toString(sr.screen[0][1]) + "\n" + ANSI_RESET + toString(sr.screen[1][0]) + toString(sr.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(sr));
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
    // le test est desactiver pour ne pas falsifier le fichier de score
    // void testSaveScores() {
    //     listScore = new Score[]{newScore("toto", 10), newScore("titi", 20), newScore("tata", 30)};
    //     Score[] temp = new Score[]{newScore("toto", 10), newScore("titi", 20), newScore("tata", 30)};
    //     saveScores();
    //     loadScores();
    //     assertTrue(equals(listScore, temp));
    // }

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

    boolean equals(Score[] p1, Score[] p2) {
        if (length(p1) != length(p2)) {
            return false;
        }
        boolean result = true;
        for (int i = 0; i < length(p1); i++) {
            result = result && equals(p1[i], p2[i]);
        }
        return result;
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


    // /**
    //  * Returns an int corresponding to the given string.
    //  * @param text containing only numbers
    //  * @return int corresponding to the given string
    //  */
    // int StringToInt(String text) {
    //     int result = 0;
    //     int cpt = 0;
    //     while (cpt < length(text)) {
    //         result = result * 10 + charToInt(charAt(text, cpt));
    //         cpt = cpt + 1;
    //     }
    //     return result;
    // }

    void testStringToInt() {
        assertEquals(0, stringToInt("0"));
        assertEquals(1, stringToInt("1"));
        assertEquals(12, stringToInt("12"));
        assertEquals(123, stringToInt("123"));
        assertEquals(1234, stringToInt("1234"));
        assertEquals(12345, stringToInt("12345"));
        assertEquals(123456, stringToInt("123456"));
        assertEquals(1234567, stringToInt("1234567"));
        assertEquals(12345678, stringToInt("12345678"));
        assertEquals(123456789, stringToInt("123456789"));
        assertEquals(-1, stringToInt("-1"));
        assertEquals(-12, stringToInt("-12"));
        assertEquals(-123, stringToInt("-123"));
        assertEquals(-1234, stringToInt("-1234"));
        assertEquals(-12345, stringToInt("-12345"));
        assertEquals(-123456, stringToInt("-123456"));
        assertEquals(-1234567, stringToInt("-1234567"));
        assertEquals(-12345678, stringToInt("-12345678"));
        assertEquals(-123456789, stringToInt("-123456789"));
    }

    /**
     * Converts a string to an integer, considering the possibility of a negative number.
     * @param text the string containing numbers, with an optional '-' sign at the beginning
     * @return the integer represented by the string
     */
    int StringToInt(String text) {
        int result = 0;
        int sign = 1;
        int index = 0;
        
        if (text.charAt(0) == '-') {
            sign = -1;
            index = 1;
        }
        
        while (index < length(text)) {
            char c = charAt(text, index);
            result = result * 10 + charToInt(c);
            index = index + 1;
        }
        
        return result * sign;
    }

    // void testStringToInt() {
    //     assertEquals(0, StringToInt("0"));
    //     assertEquals(1, StringToInt("1"));
    //     assertEquals(12, StringToInt("12"));
    //     assertEquals(123, StringToInt("123"));
    //     assertEquals(1234, StringToInt("1234"));
    //     assertEquals(12345, StringToInt("12345"));
    //     assertEquals(123456, StringToInt("123456"));
    //     assertEquals(1234567, StringToInt("1234567"));
    //     assertEquals(12345678, StringToInt("12345678"));
    //     assertEquals(123456789, StringToInt("123456789"));
    // }

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

    /**
     * Inverse la liste des mobs.
     */
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


    /**
     * Inverse le l'ordre dans le tableau de Screen.
     */
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
    /**
     * renvoie true si la chaine représente un nombre entier, positif ou non. Renvoie false sinon.
     */
    boolean isNumeric(String text) {
        boolean result = true;
        if (length(text) == 0) {
            return false;
        }
        if (charAt(text, 0) == '-') {
            text = substring(text, 1, length(text));
        }
        int cpt = 0;
        while (cpt < length(text) && result) {
            result = charAt(text, cpt) >= '0' && charAt(text, cpt) <= '9';
            cpt = cpt + 1;
        }
        return result;
    }

    void testIsNumeric() {
        assertTrue(isNumeric("0"));
        assertTrue(isNumeric("1"));
        assertTrue(isNumeric("2"));
        assertTrue(isNumeric("3"));
        assertTrue(isNumeric("4"));
        assertTrue(isNumeric("5"));
        assertTrue(isNumeric("6"));
        assertTrue(isNumeric("7"));
        assertTrue(isNumeric("8"));
        assertTrue(isNumeric("9"));
        assertTrue(isNumeric("-0"));
        assertTrue(isNumeric("-1"));
        assertTrue(isNumeric("-2"));
        assertTrue(isNumeric("-3"));
        assertTrue(isNumeric("-4"));
        assertTrue(isNumeric("-5"));
        assertTrue(isNumeric("-6"));
        assertTrue(isNumeric("-7"));
        assertTrue(isNumeric("-8"));
        assertTrue(isNumeric("-9"));
        assertFalse(isNumeric("a"));
        assertFalse(isNumeric("b"));
        assertFalse(isNumeric("c"));
        assertFalse(isNumeric("d"));
        assertFalse(isNumeric("e"));
        assertFalse(isNumeric("f"));
        assertFalse(isNumeric("g"));
        assertFalse(isNumeric("h"));
    }

    /**
     * Verifie la saisie du nombre entre un minimum et un maximum.
     */
    int chooseNumber(int min, int max) {
        int result = max+1;
        String temp = "";
        do {
            temp = readString();
            if (isNumeric(temp)) {
                result = StringToInt(temp);
            }
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

    void testApplyPatch() {
        Screen mainScreen = newScreen(2, 2);
        Screen patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', "");
        patch.screen[1][1] = newPixel('Y', "");
        applyPatch(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', ANSI_RED);
        patch.screen[1][1] = newPixel('Y', ANSI_GREEN);
        applyPatch(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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

    void testRemovePatch() {
        Screen mainScreen = newScreen(2, 2);
        Screen patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', "");
        patch.screen[1][1] = newPixel('Y', "");
        applyPatch(mainScreen, patch, 0, 0);
        removePatch(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', ANSI_RED);
        patch.screen[1][1] = newPixel('Y', ANSI_GREEN);
        applyPatch(mainScreen, patch, 0, 0);
        removePatch(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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

    void testMoveRight() {
        Screen mainScreen = newScreen(2, 2);
        Screen patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', "");
        patch.screen[1][1] = newPixel('Y', "");
        applyPatch(mainScreen, patch, 0, 0);
        moveRight(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(patch.screen[0][0]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(patch.screen[1][0]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', ANSI_RED);
        patch.screen[1][1] = newPixel('Y', ANSI_GREEN);
        applyPatch(mainScreen, patch, 0, 0);
        moveRight(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(patch.screen[0][0]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(patch.screen[1][0]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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

    void testMoveLeft() {
        Screen mainScreen = newScreen(2, 2);
        Screen patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', "");
        patch.screen[1][1] = newPixel('Y', "");
        applyPatch(mainScreen, patch, 0, 0);
        moveLeft(mainScreen, patch, 0, 0);
        assertEquals(toString(patch.screen[0][1]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(patch.screen[1][1]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', ANSI_RED);
        patch.screen[1][1] = newPixel('Y', ANSI_GREEN);
        applyPatch(mainScreen, patch, 0, 0);
        moveLeft(mainScreen, patch, 0, 0);
        assertEquals(toString(patch.screen[0][1]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(patch.screen[1][1]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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

    void testMoveTop() {
        Screen mainScreen = newScreen(2, 2);
        Screen patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', "");
        patch.screen[1][1] = newPixel('Y', "");
        applyPatch(mainScreen, patch, 0, 0);
        moveTop(mainScreen, patch, 0, 0);
        assertEquals(toString(patch.screen[1][0]) + toString(patch.screen[1][1]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', ANSI_RED);
        patch.screen[1][1] = newPixel('Y', ANSI_GREEN);
        applyPatch(mainScreen, patch, 0, 0);
        moveTop(mainScreen, patch, 0, 0);
        assertEquals(toString(patch.screen[1][0]) + toString(patch.screen[1][1]) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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


    void testMoveBottom() {
        Screen mainScreen = newScreen(2, 2);
        Screen patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', "");
        patch.screen[1][1] = newPixel('Y', "");
        applyPatch(mainScreen, patch, 0, 0);
        moveBottom(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(patch.screen[0][0]) + toString(patch.screen[0][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        patch = newScreen(2, 2);
        patch.screen[0][0] = newPixel('H', ANSI_RED);
        patch.screen[1][1] = newPixel('Y', ANSI_GREEN);
        applyPatch(mainScreen, patch, 0, 0);
        moveBottom(mainScreen, patch, 0, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(patch.screen[0][0]) + toString(patch.screen[0][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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
            line.screen[i][0] = newPixel(VERTICAL_LINE, color);
        }
        applyPatch(mainScreen, line, 0, w);
    }

    void testDrawVerticalLine() {
        Screen mainScreen = newScreen(2, 2);
        drawVerticalLine(mainScreen, 0, ANSI_RED);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(newPixel(VERTICAL_LINE, ANSI_RED)) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        drawVerticalLine(mainScreen, 0, ANSI_GREEN);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(newPixel(VERTICAL_LINE, ANSI_GREEN)) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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
            line.screen[i][0] = newPixel(VERTICAL_LINE, "");
        }
        applyPatch(mainScreen, line, 0, w);
    }

    void testDrawVerticalLine2() {
        Screen mainScreen = newScreen(2, 2);
        drawVerticalLine(mainScreen, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(newPixel(VERTICAL_LINE, "")) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        drawVerticalLine(mainScreen, 0);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(mainScreen.screen[0][1]) + "\n" + ANSI_RESET + toString(newPixel(VERTICAL_LINE, "")) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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
            line.screen[0][i] = newPixel(HORIZONTAL_LINE, color);
        }
        applyPatch(mainScreen, line, h, 0);
    }

    void testDrawHorizontalLine() {
        Screen mainScreen = newScreen(2, 2);
        drawHorizontalLine(mainScreen, 0, ANSI_RED);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(newPixel('▁', ANSI_RED)) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        drawHorizontalLine(mainScreen, 0, ANSI_GREEN);
        assertEquals(toString(mainScreen.screen[0][0]) + toString(newPixel('▁', ANSI_GREEN)) + "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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
            line.screen[0][i] = newPixel(HORIZONTAL_LINE, "");
        }
        applyPatch(mainScreen, line, h, 0);
    }

    void testDrawHorizontalLine2() {
        Screen mainScreen = newScreen(2, 2);
        drawHorizontalLine(mainScreen, 0);
        assertEquals(toString(newPixel(HORIZONTAL_LINE, "")) + toString(newPixel(HORIZONTAL_LINE, "")) +  "\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
        mainScreen = newScreen(2, 2);
        drawHorizontalLine(mainScreen, 0);
        assertEquals(toString(newPixel(HORIZONTAL_LINE, "")) + toString(newPixel(HORIZONTAL_LINE, "")) +"\n" + ANSI_RESET + toString(mainScreen.screen[1][0]) + toString(mainScreen.screen[1][1]) + "\n" + ANSI_RESET + ANSI_RESET  , toString(mainScreen));
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

    void testDrawBorder() {
        Screen screen = newScreen(3, 3);
        drawBorder(screen, ANSI_RED);
        assertEquals(toString(EMPTY_PIXEL) + toString(newPixel(HORIZONTAL_LINE, ANSI_RED)) + toString(EMPTY_PIXEL) + "\n" + ANSI_RESET + toString(newPixel(VERTICAL_LINE, ANSI_RED)) + toString(EMPTY_PIXEL) + toString(newPixel(VERTICAL_LINE, ANSI_RED)) + "\n" + ANSI_RESET + toString(newPixel(VERTICAL_LINE, ANSI_RED)) + toString(newPixel(HORIZONTAL_LINE, ANSI_RED)) + toString(newPixel(VERTICAL_LINE, ANSI_RED)) + "\n" + ANSI_RESET + ANSI_RESET  , toString(screen));
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

    void testSumWidth() {
        Screen[] screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10)};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null, null};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null, null, null};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null, null, null, null};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null, null, null, null, null};
        assertEquals(40, sumWidth(screens));
        screens = new Screen[]{null, newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null, null, null, null, null, null};
        assertEquals(40, sumWidth(screens));
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

    void testSumWidth2() {
        Screen[] screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10)};
        assertEquals(20, sumWidth(screens, 2));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null};
        assertEquals(20, sumWidth(screens, 2));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null};
        assertEquals(20, sumWidth(screens, 2));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null, null, null};
        assertEquals(20, sumWidth(screens, 2));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), null, null, null, null, null, null};
        assertEquals(20, sumWidth(screens, 2));
        screens = new Screen[]{newScreen(10, 10), null, null, null, null, null, null, null, null};
        assertEquals(10, sumWidth(screens, 1));
        screens = new Screen[]{null, null, null, null, null, null, null, null, null, null};
        assertEquals(0, sumWidth(screens, 0));
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

    void testSumHeight() {
        Screen[] screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10)};
        assertEquals(40, sumHeight(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null};
        assertEquals(40, sumHeight(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), null, null, null, null};
        assertEquals(20, sumHeight(screens));
        screens = new Screen[]{newScreen(10, 10), null, null, null, null, null, null};
        assertEquals(10, sumHeight(screens));
        screens = new Screen[]{null, null, null, null, null, null, null, null};
        assertEquals(0, sumHeight(screens));
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

    void testMaxHeight() {
        Screen[] screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(10, 10)};
        assertEquals(10, maxHeight(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), newScreen(20, 10)};
        assertEquals(20, maxHeight(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 10), null};
        assertEquals(10, maxHeight(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), null, null};
        assertEquals(10, maxHeight(screens));
        screens = new Screen[]{newScreen(10, 10), null, null, null};
        assertEquals(10, maxHeight(screens));
        screens = new Screen[]{null, null, null, null};
        assertEquals(0, maxHeight(screens));
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

    void testMaxWidth() {
        Screen[] screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 20), newScreen(10, 10)};
        assertEquals(20, maxWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 20), newScreen(10, 10), null};
        assertEquals(20, maxWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), newScreen(10, 20), null, null};
        assertEquals(20, maxWidth(screens));
        screens = new Screen[]{newScreen(10, 10), newScreen(10, 10), null, null, null};
        assertEquals(10, maxWidth(screens));
        screens = new Screen[]{newScreen(10, 10), null, null, null, null};
        assertEquals(10, maxWidth(screens));
        screens = new Screen[]{null, null, null, null, null};
        assertEquals(0, maxWidth(screens));
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


    /**
     * Permet de donner une couleur ou un fond a l'ecran en parametre
     */
    void setcolor(Screen screen, String color) {
        for (int i = 0; i < length(screen.screen, 1); i++) {
            for (int j = 0; j < length(screen.screen, 2); j++) {
                Pixel p = screen.screen[i][j];
                screen.screen[i][j].c = color + p.c;
            }
            screen.screen[i][length(screen.screen, 2)-1].c = screen.screen[i][length(screen.screen, 2)-1].c + ANSI_RESET;
        }
    }

    void testSetColor() {
        Screen screen = newScreen(2, 2);
        screen.screen[0][0] = newPixel('H', "");
        screen.screen[1][1] = newPixel('Y', "");
        Screen cp = copy(screen);
        setcolor(screen, ANSI_RED);
        assertEquals(toString(newPixel(charAt(cp.screen[0][0].c, length(cp.screen[0][0].c) - 1), ANSI_RED)) + toString(newPixel(charAt(cp.screen[0][1].c, length(cp.screen[0][1].c) - 1), ANSI_RED)) + ANSI_RESET + '\n' + ANSI_RESET + toString(newPixel(charAt(cp.screen[1][0].c, length(cp.screen[1][0].c) - 1), ANSI_RED)) + toString(newPixel(charAt(cp.screen[1][1].c, length(cp.screen[1][1].c) - 1), ANSI_RED)) + ANSI_RESET + '\n' + ANSI_RESET + ANSI_RESET  , toString(screen));
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
        boolean neg = false;
        if (number < 10000 && number > -10000) {
            if (number < 0) {
                number = number * -1;
                neg = true;
            }
            Screen[] list_nb = new Screen[4];
            for (int i = 0; i < length(list_nb); i++) {
                list_nb[i] = newScreen(0, 0);
            }
            if (number == 0) {
                list_nb[0] = loadASCII(NUMBERS_DIR + "/" + '0' + ".txt", color);
            }
            else {
                int cpt = 0;
                while (number > 0) {
                    int r = number % 10;
                    number = number / 10;
                    list_nb[cpt] = loadASCII(NUMBERS_DIR + "/" + r + ".txt", color);
                    cpt = cpt + 1;
                }
            }
            
            int withDec = 0;
            Screen number_ASCII;
            if (neg) {
                number_ASCII = newScreen(list_nb[0].height, LIST_OPERATOR[1].width+list_nb[0].width + list_nb[1].width + list_nb[2].width+ list_nb[3].width);
                applyPatch(number_ASCII, LIST_OPERATOR[1], 0, 0);
                withDec = LIST_OPERATOR[1].width;
            }
            else {
                number_ASCII = newScreen(list_nb[0].height, list_nb[0].width + list_nb[1].width + list_nb[2].width+ list_nb[3].width);
                
            }
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

    void testGetOpScreen() {
        assertEquals(toString(LIST_OPERATOR[0]), toString(getOpScreen(Operation.ADDITION)));
        assertEquals(toString(LIST_OPERATOR[1]), toString(getOpScreen(Operation.SOUSTRACTION)));
        assertEquals(toString(LIST_OPERATOR[2]), toString(getOpScreen(Operation.MULTIPLICATION)));
        assertEquals(toString(LIST_OPERATOR[3]), toString(getOpScreen(Operation.DIVISION)));
    }

    /**
     * Genre une liste horizontale.
     */
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

    /**
     * Supprime la liste horizontale sans transition.
     */
    void removeHorizontalList(Screen mainScreen, Screen[] list, int gap, int h, int w) {
        Screen result = newScreen(maxHeight(list)+2, sumWidth(list) + length(list)*gap);
        removePatch(mainScreen, result, h, w);
    }
        /**
     * Supprime la liste horizontale avec transition.
     */
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

    /**
     * Supprime la liste horizontale avec transition en excluant de la suppression les indexs precises dans la liste.
     */
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
    /**
     * Genre une liste verticale.
     */
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
    /**
     * Supprime une liste verticale sans transition.
     */
    void removeVerticalList(Screen mainScreen, Screen[] list, int gap, int h, int w) {
        Screen result = newScreen(sumHeight(list) + length(list)*gap, maxWidth(list)+2);
        removePatch(mainScreen, result, h, w);
    }
    /**
     * Supprime une liste verticale avec transition.
     */
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
    /**
     * Supprime la liste verticale avec transition en excluant de la suppression les indexs precises dans la liste.
     */
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
    /**
     * genere un ecran representant un texte avec les caracteres normaux (pas en ASCII ART)
     */
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
    /**
     * IDonne la couleur de la faiblesse 
     */
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
    /**
     * Donne la couleur de l'operation
     */
    String getWeaknessColor(Operation op) {
        String result = "";
        switch (op) {
            case ADDITION:
                result = ADD_COLOR;
                break;
            case MULTIPLICATION:
                result = MUL_COLOR;
                break;
            case DIVISION:
                result = DIV_COLOR;
                break;
            case SOUSTRACTION:
                result = SUB_COLOR;
                break;
            default:
                result = ANSI_TEXT_DEFAULT_COLOR;
                break;
        }
        return result;
    }

    void testGetWeaknessColor2() {
        assertEquals(getWeaknessColor(Operation.ADDITION), ADD_COLOR);
        assertEquals(getWeaknessColor(Operation.MULTIPLICATION), MUL_COLOR);
        assertEquals(getWeaknessColor(Operation.DIVISION), DIV_COLOR);
        assertEquals(getWeaknessColor(Operation.SOUSTRACTION), SUB_COLOR);
    }


    /////////////////////////////////////
    //////     game functions       /////
    /////////////////////////////////////

    /**
     * Funtion d'animation qui fait entrer les mob dans l'ecran.
     */
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

    /**
     * Fonction qui genere les mobs aléatoirement
     */
    Mob[] genMob(int nombre) {
        loadMob();
        Mob[] lsMob = new Mob[nombre];
        Mob mob ;
        int posy_last = 0;
        int cpt = 0;
        while (cpt < nombre && posy_last < main.height) {
            mob = copy(randomChoice(listMob));
            lsMob[cpt] = mob;
            cpt = cpt + 1;
        }
        return lsMob;
    }
    /**
     * Renvoie true si tout les mob de la liste en parametre sont mort sinon false
     */
    boolean allDead(Mob[] list) {
        boolean result = true;
        int cpt = 0;
        while (cpt < length(list) && result) {
            result = list[cpt].dead;
            cpt = cpt + 1;
        }
        return result;
    }

    void testAllDead() {
        Mob[] list = new Mob[]{new Mob(), new Mob(), new Mob(), new Mob()};
        list[0].dead = true;
        list[1].dead = true;
        list[2].dead = true;
        list[3].dead = true;
        assertTrue(allDead(list));
        list = new Mob[]{new Mob(), new Mob(), new Mob(), new Mob()};
        list[0].dead = true;
        list[1].dead = true;
        list[2].dead = true;
        assertFalse(allDead(list));
        list = new Mob[]{new Mob(), new Mob(), new Mob(), new Mob()};
        list[0].dead = true;
        list[1].dead = true;
        assertFalse(allDead(list));
        list = new Mob[]{new Mob(), new Mob(), new Mob(), new Mob()};
        list[0].dead = true;
        assertFalse(allDead(list));
        list = new Mob[]{new Mob(), new Mob(), new Mob(), new Mob()};
        assertFalse(allDead(list));
    }
    /**
     * Fonction qui met a jour les bar de vie des mobs
     */
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
    /**
     * Function qui retourne la liste d'ecran des mobs
     */
    Screen[] getMobScreens(Mob[] list) {
        Screen[] result = new Screen[length(list)];
        for (int i = 0; i < length(list); i++) {
            result[i] = list[i].visuel;
        }
        return result;
    }

    void testGetMobScreens() {
        Mob[] list = new Mob[]{new Mob(), new Mob(), new Mob(), new Mob()};
        list[0].visuel = newScreen(10, 10);
        list[1].visuel = newScreen(10, 10);
        list[2].visuel = newScreen(10, 10);
        list[3].visuel = newScreen(10, 10);
        Screen[] result = new Screen[]{list[0].visuel, list[1].visuel, list[2].visuel, list[3].visuel};
        assertArrayEquals(result, getMobScreens(list));
    }
    /**
     * Fonction qui enleve les mobs morts de l'ecran
     */
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

    /**
     * Fonction qui affiche les pv du joueur
     */
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

    /**
     * Fonction qui affiche les attque disponible pour le joueur (les operateur)
     */
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

    /**
     * Fonction qui permet de selectionner l'attaque a faire
     */
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

    /**
     * Fonction qui met a jour le combat (affichage du niveau et appelle des fonction updateMobBattle printPlayerHp )
     */
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

    /**
     * genere une vague d'ennemie et retourne true si le joueur a perdu sinon false
     */
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

    /**
     * genere l'affichage d'une bone réponse
     */
    void afficheGoodAnswer() {
        Screen text = genText("OK", ANSI_GREEN);
        applyPatch(main, text,  main.height/2 - text.height/2, main.width/2 - text.width/2);
        refresh();
        delay(1000);
        removePatch(main, text, main.height/2 - text.height/2, main.width/2 - text.width/2);
    }

    /**
     * genere l'affichage d'une mauvaise réponse
     */
    void afficheWrongAnswer(int response) {
        Screen text = genText("X", ANSI_RED);
        Screen textR = getNumber(response, ANSI_WHITE);
        applyPatch(main, text, main.height/2 - text.height/2, main.width/2 - text.width/2);
        if (SHOW_RESPONSE) {
            applyPatch(main, textR, main.height/2 - textR.height/2 + 5, main.width/2 - text.width/2 );
        }
        refresh();
        delay(1000);
        removePatch(main, text, main.height/2 - text.height/2, main.width/2 - text.width/2);
        if (SHOW_RESPONSE) {
            removePatch(main, textR, main.height/2 - textR.height/2 + 5, main.width/2 - text.width/2 );
        }
    }

    /**
     * Effectue l'attaque du joueur sur le mob si le joueur repond correctement
     */
    void attaquerMob(Operation op, int level, Mob mob) {
        int nb1 = (int) ((random() * 10) * (level+1));
        int nb2 = (int) ((random() * 10) * (level+1));
        if (op == Operation.DIVISION && nb2 == 0) {
            nb2 = 1;
        }
        int response;
        printCalcul(nb1, nb2, op);
        response = chooseNumber(MININT, MAXINT);
        removecalcul(nb1, nb2, op);
        if (calculReussi(nb1, nb2, op, response)) {
            afficheGoodAnswer();
            mob.hp = mob.hp - damageDoneToMob(mob, op);
        }
        else {
            afficheWrongAnswer(calculResult(nb1, nb2, op));
        }
        if (mob.hp <= 0) {
            mob.hp = 0;
        }
        
    }

    /**
     * Affiche la question bonus a l'ecran
     */
    void afficheQuestionBonus(String question) {
        Screen text = fitText(main, question);
        Screen title = genText("Question Bonus", ANSI_WHITE);
        applyPatch(main, title, 2, main.width/2-title.width/2);
        applyPatch(main, text, main.height/2-text.height/2, main.width/2-text.width/2);
        refresh();
    }
    /**
     * retire la question bonus de l'ecran
     */
    void removeQuestionBonus(String question) {
        Screen text = fitText(main, question);
        Screen title = genText("Question Bonus", ANSI_WHITE);
        removePatch(main, title, 2, main.width/2-title.width/2);
        removePatch(main, text, main.height/2-text.height/2, main.width/2-text.width/2);
        refresh();
    }
    /**
     * genere une question bonus et retourne true si le joueur repond correctement sinon false
     */
    boolean questionBonus() {
        Question q = randomChoice(listQuestion);
        Screen saveScreen = copy(main);
        removePatch(main, main, 0, 0);
        afficheQuestionBonus(q.question);
        String response = readString();
        removeQuestionBonus(response);
        if(equals(response,q.reponse)){ 
            afficheGoodAnswer();
            applyPatch(main,saveScreen,0,0);
            return true;   
        }else{
            removePatch(main, main, 0, 0);
            afficheWrongAnswer(StringToInt(q.reponse));
            applyPatch(main,saveScreen,0,0);
            return false;
        }
   }
   /**
     * Affiche dans l'ecran le niveau actuel
     */
    void printLevel(int level) {
        Screen levelScreen = newScreen(9, 55);
        Screen text = genText("Level ", ANSI_WHITE);
        applyPatch(levelScreen, text, 2, 2);
        applyPatch(levelScreen, getNumber(level, ANSI_WHITE), 2, text.width);
        applyPatch(main, levelScreen, 0, 0);
        refresh();
    }
    /**
     * genre un niveau et retourne true si le joueur a perdu sinon false
     */
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

    /**
     * demarre un combat de boss et retourne true si le joueur a perdu sinon false
     */
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

    
    /**
     * affcihe le calcul a l'ecran
     */
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
    /**
     * retire le calcul de l'ecran
     */
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

    /**
     * determine si le calcul est réussi ou non
     */
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

    void testCalculReussi() {
        assertTrue(calculReussi(1, 1, Operation.ADDITION, 2));
        assertTrue(calculReussi(1, 1, Operation.SOUSTRACTION, 0));
        assertTrue(calculReussi(1, 1, Operation.MULTIPLICATION, 1));
        assertTrue(calculReussi(1, 1, Operation.DIVISION, 1));
        assertFalse(calculReussi(1, 1, Operation.ADDITION, 3));
        assertFalse(calculReussi(1, 1, Operation.SOUSTRACTION, 1));
        assertFalse(calculReussi(1, 1, Operation.MULTIPLICATION, 2));
        assertFalse(calculReussi(1, 1, Operation.DIVISION, 2));
    }
    /**
     * Retourne le resultat du calcul passer en parametre
     */
    int calculResult(int nb1, int nb2, Operation op) {
        int result;
        switch (op) {
            case ADDITION:
                result = nb1 + nb2 ;
                break;
            case SOUSTRACTION:
                result = nb1 - nb2 ;
                break;
            case MULTIPLICATION:
                result = nb1 * nb2 ;
                break;
            case DIVISION:
                result = nb1 / nb2 ;
                break;
            default:
                result = 0;
                break;
        }
        return result;
    }
    
    /**
     * affiche un bonus
     */
    void afficheBonus() {
        Screen text = genText("Choisi un bonus", ANSI_WHITE);
        Screen heal = loadASCII(HEART, ANSI_RED);
        Screen atk = loadASCII(SWORD, ANSI_WHITE);
        removePatch(main, main, 0, 0);
        applyPatch(main, text, 2, main.width/2-text.width/2);
        applyPatch(main, heal, main.height/2 - heal.height/2 + 2, main.width/2 - heal.width/2 - main.width/8);
        applyPatch(main, atk, main.height/2 - atk.height/2 + 2, main.width/2 + atk.width/2 );
        refresh();
    }
    /**
     * Permet de choisir un bonus
     */
    void chooseBonus() {
        Screen saveScreen = copy(main);
        afficheBonus();
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


    /**
     * retourne un int representant le nombre de degat que le mob inflige au joueur
     */
    int damageToPlayer(int level, Mob mob) {
        playSound(HIT_SOUND);
        return (int) (random()*mob.atk*level) * DIFFICULTY;
    }
    /**
     * retourne un int representant le nombre de degat que le joueur inflige au mob
     */
    int damageDoneToMob(Mob mob, Operation op) {
        if (op == mob.faiblesse) {
            print("Coup critique ! ");
            playSound(HIT_MOB_SOUND);
            return player.atk * 3;
        }
        else {
            playSound(HIT_MOB_SOUND);
            return player.atk;
        }
    }


    ///////////////////////
    ////  Score funtions //
    ///////////////////////
    
    /**
     * determine si un score est un nouveau record personnel
     */
    boolean newPersonnalBestScore(int score, String pseudo) {
        boolean result = false;
        for (int i = 0; i < length(listScore); i++) {
            if (listScore[i].score < score && equals(listScore[i].pseudo, pseudo)) {
                result = true;
            }
        }
        return result;
    }

    void testNewPersonnalBestScore() {
        listScore = new Score[]{newScore("a", 1), newScore("b", 2), newScore("c", 3), newScore("d", 4)};
        assertFalse(newPersonnalBestScore(1, "a"));
        assertFalse(newPersonnalBestScore(2, "b"));
        assertFalse(newPersonnalBestScore(3, "c"));
        assertFalse(newPersonnalBestScore(4, "d"));
        assertTrue(newPersonnalBestScore(0, "a"));
        assertTrue(newPersonnalBestScore(1, "b"));
        assertTrue(newPersonnalBestScore(2, "c"));
        assertTrue(newPersonnalBestScore(3, "d"));
    }
    /**
     * determine si le joueur est un nouveau joueur
     */
    boolean isNewPlayer(String pseudo) {
        boolean result = true;
        for (int i = 0; i < length(listScore); i++) {
            if (equals(listScore[i].pseudo, pseudo)) {
                result = false;
            }
        }
        return result;
    }

    void testIsNewPlayer() {
        listScore = new Score[]{newScore("a", 1), newScore("b", 2), newScore("c", 3), newScore("d", 4)};
        assertFalse(isNewPlayer("a"));
        assertFalse(isNewPlayer("b"));
        assertFalse(isNewPlayer("c"));
        assertFalse(isNewPlayer("d"));
        assertTrue(isNewPlayer("e"));
        assertTrue(isNewPlayer("f"));
        assertTrue(isNewPlayer("g"));
        assertTrue(isNewPlayer("h"));
    }
    /**
     * change le score du joueur pour le score donner en parametre
     */
    void changePersonnalScore(int score, String pseudo) {
        for (int i = 0; i < length(listScore); i++) {
            if (equals(listScore[i].pseudo, pseudo)) {
                listScore[i].score = score;
                return;
            }
        }
    }

    void testChangePersonnalScore() {
        listScore = new Score[]{newScore("a", 1), newScore("b", 2), newScore("c", 3), newScore("d", 4)};
        changePersonnalScore(10, "a");
        changePersonnalScore(20, "b");
        changePersonnalScore(30, "c");
        changePersonnalScore(40, "d");
        assertEquals(listScore[0].score, 10);
        assertEquals(listScore[1].score, 20);
        assertEquals(listScore[2].score, 30);
        assertEquals(listScore[3].score, 40);
    }
    /**
     * ajoute un score a la liste des scores
     */
    void addScore(String pseudo, int score) {
        Score[] newListScore = new Score[length(listScore) + 1];
        for (int i = 0; i < length(listScore); i++) {
            newListScore[i] = listScore[i];
        }
        newListScore[length(listScore)] = newScore(pseudo, score);
        listScore = newListScore; 
    }

    void testAddScore() {
        listScore = new Score[]{newScore("a", 1), newScore("b", 2), newScore("c", 3), newScore("d", 4)};
        addScore("e", 5);
        addScore("f", 6);
        addScore("g", 7);
        addScore("h", 8);
        assertEquals(listScore[4].pseudo, "e");
        assertEquals(listScore[5].pseudo, "f");
        assertEquals(listScore[6].pseudo, "g");
        assertEquals(listScore[7].pseudo, "h");
        assertEquals(listScore[4].score, 5);
        assertEquals(listScore[5].score, 6);
        assertEquals(listScore[6].score, 7);
        assertEquals(listScore[7].score, 8);
    }

    
    /**
     * affiche le tableau des scores
     */
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
    /**
     * fonction qui effectue le tri du tableau des scores
     */
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

    void testSortScore() {
        listScore = new Score[]{newScore("a", 1), newScore("b", 2), newScore("c", 3), newScore("d", 4)};
        sortScore();
        assertEquals(listScore[0].score, 4);
        assertEquals(listScore[1].score, 3);
        assertEquals(listScore[2].score, 2);
        assertEquals(listScore[3].score, 1);
    }

    /**
     * fonction qui determine le chemin d'une lettre en fonction de son caractere
     */
    String getletterPath(char c) {
        if (c == ' ') {
            return LETTER_DIR + "/space.txt";
        }
        return LETTER_DIR + "/" + c + ".txt"; 
    }

    // game events graphics
    /**
     * réalise l'affichage de l'ecran titre
     */
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
    /**
     * retourne un int representant le meilleur score du joueur
     */
    int getPlayerBestScore(String pseudo) {
        extensions.CSVFile f = loadCSV(SCORE_FILE);
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
    /**
     * retourne un tableau de joueur representant les joueurs du fichier score
     */
    Player[] getPlayers() {
        extensions.CSVFile f = loadCSV(SCORE_FILE);
        Player[] table = new Player[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            table[i-1] = newPlayer(getCell(f, i, 0), null);
        }
        return table;
    }
    /**
     * genere l'ecran de selection du personnage
     */
    Screen chooseCharacter() {
        Screen choice = newScreen(main.height, main.width);
        Screen prompt = newScreen(main.height/4, main.width);
        int r;
        Screen[] list_perso = new Screen[]{loadASCII(PLAYER, ANSI_RED), loadASCII(PLAYER_2, ANSI_GREEN), loadASCII(PLAYER_3, ANSI_YELLOW), loadASCII(PLAYER_4, ANSI_BLUE)};
        Screen choose_ASCII = genText("Choisis ton personnage", "");
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

    /**
     * actualise l'ecran principale
     */
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
        playSound(MAIN_THEME);
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
            delay(1000);
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
        println(toString(getNumber(0, "")));
        // loadScores();
        // printScoreTab();
        // readString();
        // addScore("bonoujour", 55);
        // printScoreTab();
        // readString();
        // saveScores();
        // Screen[] list = new Screen[]{loadASCII(PLAYER, ANSI_RED), loadASCII(PLAYER_2, ANSI_GREEN), loadASCII(PLAYER_3, ANSI_YELLOW), loadASCII(PLAYER_4, ANSI_BLUE)};
        // applyPatch(main, genHorizontalList(list, 20), 0, 0);
        // refresh();
        // removeHorizontalList(main, list, 20, 0, 0, true, new int[]{1});
        // refresh();
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