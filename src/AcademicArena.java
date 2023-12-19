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

    final char[] LIST_EMPTY = new char[]{' ', ' ', ' ', ' ', ' ',' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '　', '⠀'};
    final Screen[] LIST_OPERATOR = new Screen[]{loadASCII(OPERATOR_DIR + "/" + "plus.txt", ANSI_RED), loadASCII(OPERATOR_DIR + "/" + "moins.txt", ANSI_GREEN), loadASCII(OPERATOR_DIR + "/" + "fois.txt", ANSI_YELLOW), loadASCII(OPERATOR_DIR + "/" + "division.txt", ANSI_BLUE)};
    Question[] listQuestion;
    Mob[] listMob ;
    Mob[] listBoss ;
    Screen main = newScreen(51,250);
    Player player;

    int indexNearSpace(String text, int index) {
        int result = index;
        while (charAt(text, result) != ' ' && result > 0) {
            result = result - 1;
        }
        while (charAt(text, result) != ' ' && result < length(text)) {
            result = result + 1;
        }
        return result;
    }
    void loadMob() {
        extensions.CSVFile f = loadCSV(RESSOURCES_DIR + "/" + "mob.csv");
        listMob = new Mob[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listMob[i-1] = newMob(StringToInt(getCell(f, i, 0)), StringToInt(getCell(f, i, 1)), getOperation(getCell(f, i, 2)), loadASCII(MOB_DIR + "/" + getCell(f, i, 3), getWeaknessColor(getCell(f, i, 2))), 0, 0);
        }

    }

    void loadBoss() {
        extensions.CSVFile f = loadCSV(RESSOURCES_DIR + "/" + "boss.csv");
        listBoss = new Mob[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listBoss[i-1] = newMob(StringToInt(getCell(f, i, 0)), StringToInt(getCell(f, i, 1)), getOperation(getCell(f, i, 2)), loadASCII(BOSS_DIR + "/" + getCell(f, i, 3), getWeaknessColor(getCell(f, i, 2))), 0, 0);
        }

    }

    void loadQuestion() {
        extensions.CSVFile f = loadCSV(RESSOURCES_DIR + "/" + "bonus.csv");
        listQuestion = new Question[rowCount(f)-1];
        for (int i = 1; i < rowCount(f); i++) {
            listQuestion[i-1] = newQuestion(getCell(f, i, 0), getCell(f, i, 1));
        }

    }

    Question newQuestion(String question, String reponse) {
        Question q = new Question();
        q.question = question;
        q.reponse = reponse;
        return q;

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

    int getPlayerBestScore(String speudo) {
        extensions.CSVFile f = loadCSV(RESSOURCES_DIR + "/" + "scores.csv");
        int result = 0;
        int cpt = 1;
        while (cpt < rowCount(f) && result == 0) {
            if (equals(getCell(f, cpt, 0), speudo)) {
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

    void refresh() {
        drawHorizontalLine(main, 0, ANSI_TEXT_DEFAULT_COLOR);
        drawHorizontalLine(main, main.height-1, ANSI_TEXT_DEFAULT_COLOR);
        drawVerticalLine(main, 0, ANSI_TEXT_DEFAULT_COLOR);
        drawVerticalLine(main, main.width-1, ANSI_TEXT_DEFAULT_COLOR);
        println(toString(main));
    }

    String getletterPath(char c) {
        if (c == ' ') {
            return RESSOURCES_DIR + "/letters/" + "space" + ".txt";
        }
        return RESSOURCES_DIR + "/letters/" + c + ".txt"; 
    }

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

    // boolean challenge(int min, int max) {
    //     int nb1;
    //     int nb2;
    //     if (min < 0) {
    //         nb1 = random(-min, max+(-min)) + min;
    //         nb2 = random(-min, max+(-min)) + min;
    //     }
    //     else {
    //         nb1 = random(min, max);
    //         nb2 = random(min, max);
    //     }

    //     int response ;
    //     print("Reponse : ");
    //     response = readInt();
    //     return response == nb1 + nb2;
    // }    

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

    boolean isEmpty(char c) {
        int cpt = 0;
        boolean result = false;
        while (cpt < length(LIST_EMPTY) && !result) {
            result = c == LIST_EMPTY[cpt];
            cpt = cpt + 1;
        }
        return result;
    }

    Pixel newPixel(char c, String color) {
        Pixel p = new Pixel();
        p.c = color + c;
        return p;
    }

    int StringToInt(String text) {
        int result = 0;
        int cpt = 0;
        while (cpt < length(text)) {
            result = result * 10 + charToInt(charAt(text, cpt));
            cpt = cpt + 1;
        }
        return result;
    }

    // int charToInt(char c) {
    //     return c - '0';
    // }

    Pixel newPixelEmpty() {
        Pixel p = new Pixel();
        p.c = ""+EMPTY;
        return p;
    }

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

    String toString(Pixel pixel) {
        return pixel.c;
    }

    String toString(Mob m) {
        return m.hp + " " + m.atk + " " + m.faiblesse + " " + m.visuel + " " + m.posx + " " + m.posy;
    }

    boolean equals(Pixel p1, Pixel p2) {
        return charAt(p1.c, length(p1.c) - 1) ==  charAt(p2.c, length(p2.c) - 1);
    }

    Pixel copy(Pixel p1) {
        Pixel p = newPixel('c', ANSI_TEXT_DEFAULT_COLOR);
        p.c = p1.c;
        return p;
    }

    void testEqualsPixel() {

    }

    String toString(Screen screen) {
        String result = "";
        for (int i = 0; i < length(screen.screen, 1); i++) {

            for (int j = 0; j < length(screen.screen, 2); j++) {
                result = result + toString(screen.screen[i][j]);
            }
            result = result + '\n' + ANSI_TEXT_DEFAULT_COLOR;
        }
        return result + ANSI_TEXT_DEFAULT_COLOR;
    }

    void testToStringScreen() {
        Screen sr = newScreen(2, 2);
        sr.screen[0][0] = newPixel('H', "");
        sr.screen[1][1] = newPixel('Y', "");
        assertEquals(toString(sr.screen[0][0]) + toString(sr.screen[0][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + toString(sr.screen[1][0]) + toString(sr.screen[1][1]) + "\n" + ANSI_TEXT_DEFAULT_COLOR + ANSI_TEXT_DEFAULT_COLOR  , toString(sr));
    }

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

    void testnewMob() {
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

    Player newPlayer(String speudo, Screen character) {
        Player p = new Player();
        p.hp = 100;
        p.atk = 5;
        p.speudo = speudo;
        p.character = character;
        return p;
    }

    Mob copyMob(Mob m) {
        Mob result = newMob(m.hp, m.atk, m.faiblesse, m.visuel, m.posx, m.posy);
        return result;
    }

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

    int count(String text, char car) {
        int result = 0;
        for (int i = 0; i < length(text); i++) {
            if (charAt(text, i) == car) {
                result = result + 1;
            }
        }
        return result;
    }

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
            screen[i][cptW-1].c = screen[i][cptW-1].c + ANSI_TEXT_DEFAULT_COLOR;
            cptW = 0;
            cpt = cpt + 1;
        }
        Screen result = newScreen(height, width);
        result.screen = screen;
        return result;
    }


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

    void removePatch(Screen mainScreen, Screen patch, int h, int w) {
        Screen n = newScreen(patch.height, patch.width);
        for (int i = 0; i < length(n.screen, 1); i++) {
            for (int j = 0; j < length(n.screen, 2); j++) {
                n.screen[i][j] = newPixel(EMPTY, ANSI_TEXT_DEFAULT_COLOR);
            }
        }
        applyPatch(mainScreen, n, h, w);
    }

    void removePatch(Screen mainScreen, Screen patch, int h, int w, boolean transistion) {
        Screen n = newScreen(1, patch.width);
        drawHorizontalLine(n, 0);
        for (int i = 0; i < patch.height+1; i++) {
            removePatch(patch, n, i-1, 0);
            applyPatch(patch, n, i, 0);
            applyPatch(mainScreen, patch, h, w);
            refresh();
        }
    }

    

    void moveRight(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH, curW+1);     
    }

    void moveLeft(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH, curW-1);     
    }

    void moveTop(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH - 1, curW);
    }

    void moveBottom(Screen mainScreen, Screen patch, int curH, int curW) {
        removePatch(mainScreen, patch, curH, curW);  
        applyPatch(mainScreen, patch, curH + 1, curW);     
    }


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

    

    void drawVerticalLine(Screen mainScreen, int w, String color) {
        Screen line = newScreen(mainScreen.height, 1);
        for (int i = 1; i < line.height; i++) {
            line.screen[i][0] = newPixel('▐', color);
        }
        applyPatch(mainScreen, line, 0, w);
    }

    void drawVerticalLine(Screen mainScreen, int w) {
        Screen line = newScreen(mainScreen.height, 1);
        for (int i = 1; i < line.height; i++) {
            line.screen[i][0] = newPixel('▐', "");
        }
        applyPatch(mainScreen, line, 0, w);
    }

    void drawHorizontalLine(Screen mainScreen, int h, String color) {
        Screen line = newScreen(1, mainScreen.width);
        for (int i = 0; i < line.width; i++) {
            line.screen[0][i] = newPixel('▁', color);
        }
        applyPatch(mainScreen, line, h, 0);
    }

    void drawHorizontalLine(Screen mainScreen, int h) {
        Screen line = newScreen(1, mainScreen.width);
        for (int i = 0; i < line.width; i++) {
            line.screen[0][i] = newPixel('▁', "");
        }
        applyPatch(mainScreen, line, h, 0);
    }

    void afficherLogo(){
        Screen partA = loadASCII(LOGO_PART_A, ANSI_RED);
        Screen partB = loadASCII(LOGO_PART_B, ANSI_YELLOW);
        int posX_A = -150;
        int posy_A = 10;
        int posX_B = main.width - (posX_A +  135);
        int posy_B = 25;

        for (int i = posX_A; i < 40; i++) {
            posX_A = posX_A + 1;
            posX_B = posX_B - 1;
            moveRight(main, partA, posy_A, i);
            moveLeft(main, partB, posy_B, posX_B);
            refresh();
        } 

        print("             Press enter to start                ");
        readString();

        for (int i = 0; i < 50; i++) {
            moveTop(main, partA, posy_A - i, posX_A) ;
            moveBottom(main, partB, posy_B + i, posX_B);
            refresh();
        }


    }

    // void mainMenue(Screen main) {
    //     Screen choice = newScreen(25, 120);
    //     Screen player = newScreen(25, 114);
    //     Screen player_ASCII = loadASCII(PLAYER, ANSI_BLUE);
    //     applyPatch(player, player_ASCII, 2, 20);
    //     applyPatch(main, player, 25, 120, false);
    //     drawVerticalLine(main, 120);
    //     refresh(main);

    // }



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


    

    Screen chooseCharacter() {
        Screen choice = newScreen(main.height, main.width);
        Screen prompt = newScreen(main.height/4, main.width);
        int curH = 3;
        int witdh_last = 0;
        int r;
        int hchoice = 0;
        int wchoice = 0;

        Screen[] list_perso = new Screen[4];
        list_perso[0] = loadASCII(PLAYER, ANSI_RED);
        list_perso[1] = loadASCII(PLAYER_2, ANSI_GREEN);
        list_perso[2] = loadASCII(PLAYER_3, ANSI_YELLOW);
        list_perso[3] = loadASCII(PLAYER_4, ANSI_BLUE);


        Screen choose_ASCII = genText("Choisi ton personnage", "");
        applyPatch(choice, choose_ASCII, curH, choice.width/2 - choose_ASCII.width/2);
        curH = curH + choose_ASCII.height + 3;
        curH = curH + 11;
        for (int i = 0; i < length(list_perso); i++) {
            applyPatch(choice, list_perso[i], curH, 20 + ((witdh_last) + 20*i));
            applyPatch(prompt, getNumber(i+1, ANSI_WHITE), 0, 20 + ((witdh_last) + 20*i));
            witdh_last = witdh_last + list_perso[i].width;
        }
        applyPatch(main, choice, 0, 0);
        applyPatch(main, prompt, main.height - choice.height +20, 0, false);
        
        do {
            refresh();

            print("Choose your character : ");

            r = readInt();

        } while(r < 1 || r > 4);

        witdh_last = 0;
        for (int i = 0; i < length(list_perso); i++) {
            if (i != r-1) {
                removePatch(main, list_perso[i], curH, 20 + ((witdh_last) + 20*i), true);
            }
            else {
                hchoice = curH;
                wchoice = 20 + ((witdh_last) + 20*i);
            }
            witdh_last = witdh_last + list_perso[i].width;
        }
        choice = newScreen((main.height/4) * 3, main.width);
        removePatch(main, prompt, choice.height, 0);
        removePatch(main, choice, 0, 0);
        moveTo(main, list_perso[r-1], hchoice, wchoice, (main.height/2 - list_perso[r-1].height/2) -5, 20, 4);
        refresh();
        return list_perso[r-1];

    }

    String randomChoice(String[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }

    Mob randomChoice(Mob[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }

    Question randomChoice(Question[] list) {
        int choice =(int) ( random() * length(list));
        return list[choice];
    }

    Mob[] genMob(int nombre) {
        loadMob();
        Mob[] lsMob = new Mob[nombre];
        Mob mob ;
        int posy_last = 0;
        int cpt = 0;
        while (cpt < nombre && posy_last < main.height) {
            mob = copyMob(randomChoice(listMob));
            mob.posx = main.width - mob.visuel.width - 30;
            print(posy_last + main.height/nombre - mob.visuel.height/2);
            mob.posy = posy_last + main.height/(nombre * 2) - mob.visuel.height/2;
            applyPatch(main, mob.visuel, mob.posy, main.height+mob.posx);
            moveTo(main, mob.visuel, mob.posy, main.height+mob.posx, mob.posy, mob.posx, 5);
            lsMob[cpt] = mob;
            cpt = cpt + 1;
            posy_last = posy_last + main.height/nombre;
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

    int chooseNumber(int min, int max) {
        int result = 0;
        do {
            result = readInt();
        } while (result < min || result > max);
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

    void updateMobBattle( Mob[] lsMob) {
        Mob mob;
        for (int i = 0; i < length(lsMob); i++) {
            mob = lsMob[i];
            if (mob.hp <= 0 && !mob.dead) {
                removePatch(main, mob.visuel, mob.posy, mob.posx, true);
                removePatch(main, newScreen(1, 20), mob.posy + mob.visuel.height, mob.posx);

                mob.dead = true;
                mob.hp = 0;
                
            }
            else {
                updateMobHpBar( mob);
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
        updateMobBattle( listToDefeat);
        printPlayerHp();
        refresh();
    }


    boolean genWawe( int waweNumber, int difficulty) {
        Mob[] listToDefeat = genMob( waweNumber);
        boolean gameOver = false;
        int cpt = 0;
        Operation op;
        while (!allDead(listToDefeat) && !gameOver ) {
            updateBattle(waweNumber, listToDefeat);
            print("quelle attaque voulez vous faire ? (+ - * /)");
            op = selectAttaque();
            println("Qui voulez vous attaquer ? ");
            int choice = chooseNumber(1, length(listToDefeat)) - 1;
            if (!listToDefeat[choice].dead) {
                attaquerMob(op, choice, listToDefeat[choice]);
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


    int sumWidth(Screen[] screens) {
        int result = 0;
        for (int i = 0; i < length(screens); i++) {
            if (screens[i] != null ) {
                result = result + screens[i].width;
            }
        }
        return result;
    }

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

     Screen fitText(Screen screen, String text) {
        if (length(text)*8 < screen.width) {
            return genText(text, ANSI_TEXT_DEFAULT_COLOR);
        }
        else {
            int cut_index = indexNearSpace(text, ((length(text)*8)%screen.width)%length(text));
            print(cut_index);
            Screen p1 = genText(substring(text, 0, cut_index), ANSI_WHITE);
            Screen p2 = genText(substring(text, cut_index, length(text)), ANSI_WHITE);
            Screen result = newScreen(p1.height + p2.height, screen.width);
            applyPatch(result, p1, 0, result.width/2-p1.width/2);
            applyPatch(result, p2, p1.height, result.width/2-p2.width/2);
            return result;
        }
        
    }

    Screen deepCopy(Screen ecran) {
        Screen result = newScreen(ecran.height, ecran.width);
        for (int i =0; i< result.height; i++) {

            for (int j = 0; j < result.width; j++) {

                result.screen[i][j] = copy(ecran.screen[i][j]);

            }
        }
        return result;
    }

   boolean questionBonus() {
        Question q = randomChoice(listQuestion);
        Screen saveScreen = deepCopy(main);
        removePatch(main, main, 0, 0);
        Screen text = fitText(main, q.question);
        Screen title = genText("Question Bonus", ANSI_WHITE);
        applyPatch(main, title, 2, main.width/2-title.width/2);
        applyPatch(main, text, main.height/2-text.height/2, main.width/2-text.width/2);
        refresh();
        String response = readString();
        applyPatch(main, saveScreen, 0, 0);
        return equals(response, q.reponse);
   }



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
        int cpt = 0;
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
        return true;
    }

    void drawBorder(Screen screen, String color) {
        drawHorizontalLine(screen, 0, color);
        drawHorizontalLine(screen, screen.height-1, color);
        drawVerticalLine(screen, 0, color);
        drawVerticalLine(screen, screen.width-1, color);
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
        Screen saveScreen = deepCopy(main);
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


    void algorithm() {
        loadMob();
        loadQuestion();
        loadBoss();
        boolean gameOver = false;
        int level = 1;
        playSound("./Music3.wav");
        print("Entrez votre pseudo : ");
        player = newPlayer(readString(), null);
        afficherLogo();
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
        reset();
    }

    void _algorithm() {

        // loadMob();
        // boolean gameOver = false;
        // int level = 1;
        // playSound("./Music3.wav");
        // print("Entrez votre pseudo : ");
        // player = newPlayer(readString(), null);
        // afficherLogo();
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